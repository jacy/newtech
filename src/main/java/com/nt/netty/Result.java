package com.nt.netty;

import org.json.simple.JSONObject;

public class Result extends JsonToJavaObj {
	private long roundid;
	private long shoe;
	private long round;
	private String result;
	private String cards;

	public Result(JSONObject json) {
		super(json);
		this.roundid = (long) json.get("roundid");
		this.shoe = (long) json.get("shoe");
		this.round = (long) json.get("round");
		this.result = (String) json.get("result");
		this.cards = (String) json.get("cards");
	}

	public long getRoundid() {
		return roundid;
	}

	public void setRoundid(long roundid) {
		this.roundid = roundid;
	}

	public long getShoe() {
		return shoe;
	}

	public void setShoe(long shoe) {
		this.shoe = shoe;
	}

	public long getRound() {
		return round;
	}

	public void setRound(long round) {
		this.round = round;
	}

	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}

	public String getCards() {
		return cards;
	}

	public void setCards(String cards) {
		this.cards = cards;
	}

	@Override
	public String toString() {
		return "Result [roundid=" + roundid + ", result=" + result + "]";
	}

}
