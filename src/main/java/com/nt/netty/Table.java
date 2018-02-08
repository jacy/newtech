package com.nt.netty;

import org.json.simple.JSONObject;

public class Table extends JsonToJavaObj {
	private long id;
	private String dealerGameName;
	private String dealerName;
	private String dealerPhoto;
	private String gameName;
	private long status;
	private long ticks;
	private long dealerTableId;
	private long displayStatus;

	public Table(JSONObject content) {
		super(content);
		this.id = (long) content.get("id");
		this.dealerTableId = (long) content.get("dealerTableId");
		this.dealerName = (String) content.get("dealerName");
		this.dealerPhoto = (String) content.get("dealerPhoto");
		this.dealerGameName = (String) content.get("dealerGameName");
	}

	@Override
	public String toString() {
		return "Table [id=" + id + ", dealerGameName=" + dealerGameName + ", dealerName=" + dealerName + ", dealerPhoto=" + dealerPhoto + "]";
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getDealerGameName() {
		return dealerGameName;
	}

	public void setDealerGameName(String dealerGameName) {
		this.dealerGameName = dealerGameName;
	}

	public String getDealerName() {
		return dealerName;
	}

	public void setDealerName(String dealerName) {
		this.dealerName = dealerName;
	}

	public String getDealerPhoto() {
		return dealerPhoto;
	}

	public void setDealerPhoto(String dealerPhoto) {
		this.dealerPhoto = dealerPhoto;
	}

	public String getGameName() {
		return gameName;
	}

	public void setGameName(String gameName) {
		this.gameName = gameName;
	}

	public long getStatus() {
		return status;
	}

	public void setStatus(long status) {
		this.status = status;
	}

	public long getTicks() {
		return ticks;
	}

	public void setTicks(long ticks) {
		this.ticks = ticks;
	}

	public long getDealerTableId() {
		return dealerTableId;
	}

	public void setDealerTableId(long dealerTableId) {
		this.dealerTableId = dealerTableId;
	}

	public long getDisplayStatus() {
		return displayStatus;
	}

	public void setDisplayStatus(long displayStatus) {
		this.displayStatus = displayStatus;
	}

}
