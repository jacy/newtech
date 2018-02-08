package com.nt.netty;

import java.lang.reflect.Constructor;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import io.netty.channel.Channel;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class JSONUtil {
	private static final JSONParser parser = new JSONParser();

	@SuppressWarnings("unchecked")
	public static void sendMsg(Channel channel, String kind, JSONObject jsonObject) {
		if (jsonObject == null) {
			jsonObject = new JSONObject();
		}
		jsonObject.put("kind", kind);
		String reqJson = jsonObject.toJSONString();
		sendMsg(channel, reqJson);
	}

	public static void sendMsg(Channel channel, String reqJson) {
		channel.writeAndFlush(reqJson);
		System.out.println("sent:" + reqJson);
	}

	public static JSONObject parse(String msg) throws ParseException {
		Object jsonObj = parser.parse(msg);
		return (JSONObject) jsonObj;
	}
	
	public static <T extends JsonToJavaObj> List<T> jsonArrayToList(JSONArray gameArray, Class<T> type) {
		@SuppressWarnings("unchecked")
		Stream<JSONObject> parallelStream = gameArray.parallelStream();
		try {
			Constructor<T> constructor = type.getConstructor(JSONObject.class);
			return parallelStream.map(g -> {
				try {
					return constructor.newInstance(g);
				} catch (Exception e) {
					e.printStackTrace();
				}
				return null;
			}).collect(Collectors.toList());
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

}
