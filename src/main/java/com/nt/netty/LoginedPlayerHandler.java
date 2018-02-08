package com.nt.netty;

import static com.nt.netty.JSONUtil.jsonArrayToList;
import static com.nt.netty.JSONUtil.parse;
import static com.nt.netty.JSONUtil.sendMsg;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

public class LoginedPlayerHandler extends SimpleChannelInboundHandler<String> {
	private static final String TEST_NICKNAME = "{\"name\":\"11111111111\",\"avatar\":\"12\",\"kind\":\"createuserinfo\"}";
	private Player player;
	private List<Table> tables;
	private History history;
	private Map<Long, List<Limit>> limits = new HashMap<Long, List<Limit>>();

	public LoginedPlayerHandler(Player player) {
		this.player = player;
		System.out.println("============================================");
		System.out.println("Logined player");
		System.out.println("============================================");
	}

	@Override
	@SuppressWarnings("unchecked")
	protected void channelRead0(ChannelHandlerContext ctx, String msg) throws Exception {
		JSONObject jsonObject = parse(msg);
		String kind = (String) jsonObject.get("kind");
		switch (kind) {
		case "balance":
			JSONObject balance = (JSONObject) jsonObject.get("content");
			player.setBalance(new BigDecimal(balance.get("value").toString()));
			System.out.println(player);
			break;
		case "showlobby":
			tables = jsonArrayToList((JSONArray) jsonObject.get("content"), Table.class);
			System.out.println("Tables:" + tables);
			if (tables != null) {
				JSONObject limitObject = new JSONObject();
				JSONArray tableIds = new JSONArray();
				tableIds.add(tables.get(0).getId());
				limitObject.put("tables", tableIds);
				sendMsg(ctx.channel(), "limits", limitObject);
			}
			break;
		case "history":
			history = new History((JSONObject) jsonObject.get("content"));
			System.out.println(history);
			break;
		case "limits":
			JSONArray limitJson = (JSONArray) jsonObject.get("content");
			limitJson.forEach(obj -> {
				JSONObject tableLimit = (JSONObject) obj;
				List<Limit> limit = jsonArrayToList((JSONArray) tableLimit.get("limits"), Limit.class);
				limits.put((Long) tableLimit.get("table"), limit);
			});
			limits.entrySet().stream().findFirst().ifPresent(entry -> {
				ctx.pipeline().replace(this, "SingleTableHandler", new SingleTableHandler(player, entry.getKey(), entry.getValue().get(0)));
				ctx.fireChannelRegistered();
			});
			System.out.println(limits);
			break;
		default:
			System.err.println("unknown msg:" + msg);
		}
	}

	@Override
	public void channelRegistered(ChannelHandlerContext ctx) throws Exception {
		super.channelRegistered(ctx);
		sendMsg(ctx.channel(), "showlobby", null);
		sendMsg(ctx.channel(), "getbalance", null);
		if (player.getCustomNickname() == null) {
			sendMsg(ctx.channel(), TEST_NICKNAME);
		}
	}

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
		cause.printStackTrace();
		ctx.close();
	}
}
