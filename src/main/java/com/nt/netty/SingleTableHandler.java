package com.nt.netty;

import static com.nt.netty.JSONUtil.parse;
import static com.nt.netty.JSONUtil.sendMsg;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import java.math.BigDecimal;

import org.json.simple.JSONObject;

public class SingleTableHandler extends SimpleChannelInboundHandler<String> {
	private Player player;
	private Long tableId;
	private Limit limit;

	public SingleTableHandler(Player player, Long tableId, Limit limit) {
		this.player = player;
		this.tableId = tableId;
		this.limit = limit;
		System.out.println("============================================");
		System.out.println("Entered table " + tableId);
		System.out.println("============================================");
	}

	@Override
	protected void channelRead0(ChannelHandlerContext ctx, String msg) throws Exception {
		JSONObject jsonObject = parse(msg);
		String kind = (String) jsonObject.get("kind");
		switch (kind) {
		case "balance":
			JSONObject balance = (JSONObject) jsonObject.get("content");
			player.setBalance(new BigDecimal(balance.get("value").toString()));
			System.out.println(player);
			break;
		default:
			System.err.println("unknown msg:" + msg);
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public void channelRegistered(ChannelHandlerContext ctx) throws Exception {
		super.channelRegistered(ctx);
		JSONObject limitObject = new JSONObject();
		limitObject.put("table", tableId);
		limitObject.put("limit", limit.getId());
		sendMsg(ctx.channel(), "enter", limitObject);
	}

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
		cause.printStackTrace();
		ctx.close();
	}
}
