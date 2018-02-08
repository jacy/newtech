package com.nt.netty;

import java.util.List;

import org.json.simple.JSONObject;

public class Limit extends JsonToJavaObj {

	private long id;
	private long min;
	private long max;
	private List<Long> chips;

	@SuppressWarnings("unchecked")
	public Limit(JSONObject o) {
		super(o);
		this.id = (long) o.get("id");
		this.min = (long) o.get("min");
		this.max = (long) o.get("max");
		this.chips = (List<Long>) o.get("chips");
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public long getMin() {
		return min;
	}

	public void setMin(long min) {
		this.min = min;
	}

	public long getMax() {
		return max;
	}

	public void setMax(long max) {
		this.max = max;
	}

	public List<Long> getChips() {
		return chips;
	}

	public void setChips(List<Long> chips) {
		this.chips = chips;
	}

	@Override
	public String toString() {
		return "Limit [id=" + id + ", min=" + min + ", max=" + max + ", chips=" + chips + "]";
	}
}
