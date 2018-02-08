package com.nt.netty;

import static com.nt.netty.JSONUtil.parse;
import static com.nt.netty.JSONUtil.sendMsg;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import org.json.simple.JSONObject;

public class AnoymousPlayerHandler extends SimpleChannelInboundHandler<String> {

	private static final String TEST_TOKEN = "{\"sys\":\"CASH\",\"token\":\"jacytest1\",\"platform\":\"web\",\"operator\":\"W88\",\"client\":\"Safari 11.0.2\",\"kind\":\"auth\"}";

	public AnoymousPlayerHandler() {
		System.out.println("============================================");
		System.out.println("Anoymous player");
		System.out.println("============================================");
	}

	@Override
	protected void channelRead0(ChannelHandlerContext ctx, String msg) throws Exception {
		JSONObject jsonObject = parse(msg);
		String kind = (String) jsonObject.get("kind");
		switch (kind) {
		case "auth":
			sendMsg(ctx.channel(), TEST_TOKEN);
			break;
		case "authplayersucceed":
			Player player = new Player((JSONObject) jsonObject.get("content"));
			ctx.pipeline().replace(this, "loginedplayerHandler", new LoginedPlayerHandler(player));
			ctx.fireChannelRegistered();
			break;
		case "authplayerfail":
			ctx.close();
			break;
		default:
			System.err.println("unknown msg:" + msg);
		}
	}

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
		cause.printStackTrace();
		ctx.close();
	}
}
