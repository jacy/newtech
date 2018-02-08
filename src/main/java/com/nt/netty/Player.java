package com.nt.netty;

import java.math.BigDecimal;

import org.json.simple.JSONObject;

public class Player {
	private long id;
	private String currency;
	private boolean testUser;
	private String name;
	private String nickname;
	private String customNickname;
	private String avatarId;
	private String riskId;
	private BigDecimal balance;

	public Player(JSONObject content) {
		this.id = (long) content.get("id");
		this.currency = (String) content.get("currency");
		this.nickname = (String) content.get("nickname");
	}

	public Player() {
		super();
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getCurrency() {
		return currency;
	}

	public void setCurrency(String currency) {
		this.currency = currency;
	}

	public boolean isTestUser() {
		return testUser;
	}

	public void setTestUser(boolean testUser) {
		this.testUser = testUser;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	public String getCustomNickname() {
		return customNickname;
	}

	public void setCustomNickname(String customNickname) {
		this.customNickname = customNickname;
	}

	public String getAvatarId() {
		return avatarId;
	}

	public void setAvatarId(String avatarId) {
		this.avatarId = avatarId;
	}

	public String getRiskId() {
		return riskId;
	}

	public void setRiskId(String riskId) {
		this.riskId = riskId;
	}

	public BigDecimal getBalance() {
		return balance;
	}

	public void setBalance(BigDecimal balance) {
		this.balance = balance;
	}

	@Override
	public String toString() {
		return "Player [id=" + id + ", currency=" + currency + ", balance=" + balance.toPlainString() + "]";
	}

}
