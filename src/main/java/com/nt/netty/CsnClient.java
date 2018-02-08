package com.nt.netty;

import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import io.netty.handler.codec.MessageToByteEncoder;
import io.netty.handler.codec.MessageToMessageDecoder;
import io.netty.handler.codec.bytes.ByteArrayDecoder;
import io.netty.util.CharsetUtil;

import java.io.ByteArrayInputStream;
import java.util.List;
import java.util.zip.InflaterInputStream;

import org.apache.commons.io.IOUtils;

public class CsnClient {
	private static final String HOST = "localhost";
	private static final int PORT = 7080;

	public static void main(String[] args) throws InterruptedException {
		EventLoopGroup group = new NioEventLoopGroup();
		try {
			Bootstrap b = new Bootstrap();
			b.group(group).channel(NioSocketChannel.class).option(ChannelOption.SO_KEEPALIVE, true).option(ChannelOption.TCP_NODELAY, true).handler(new ChannelInitializer<SocketChannel>() {
				@Override
				public void initChannel(SocketChannel ch) throws Exception {
					ChannelPipeline p = ch.pipeline();

					p.addLast("inbound1-readContentBaseOnFirst2BytesAsMessageSize", new LengthFieldBasedFrameDecoder(1024 * 1024, 0, 2, 0, 2));
					p.addLast("inbound2-byteBufferToBytes", new ByteArrayDecoder());
					p.addLast("inbound3-decompressMessage", new JdkDeflateDecoder());
					p.addLast("inbound4-authHandler", new AnoymousPlayerHandler());

					p.addLast("outbound1-append0AsMessageDelimiter", new XmlSocketEncoder());
				}
			});

			b.connect(HOST, PORT).sync().channel().closeFuture().sync();
		} finally {
			group.shutdownGracefully();
		}
	}

	static class JdkDeflateDecoder extends MessageToMessageDecoder<byte[]> {

		@Override
		protected void decode(ChannelHandlerContext ctx, byte[] bytes, List<Object> out) throws Exception {
			InflaterInputStream deflate = new InflaterInputStream(new ByteArrayInputStream(bytes));
			String msg = IOUtils.toString(deflate, CharsetUtil.UTF_8);
			IOUtils.closeQuietly(deflate);
			out.add(msg);
		}

		@Override
		public boolean isSharable() {
			return true;
		}
	}

	static class XmlSocketEncoder extends MessageToByteEncoder<String> {
		@Override
		protected void encode(ChannelHandlerContext ctx, String msg, ByteBuf out) throws Exception {
			byte[] bytes = msg.getBytes(CharsetUtil.UTF_8);
			out.writeBytes(bytes);
			out.writeByte(0);

		}

		@Override
		public boolean isSharable() {
			return true;
		}
	}
}
