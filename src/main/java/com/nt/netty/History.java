package com.nt.netty;

import static com.nt.netty.JSONUtil.jsonArrayToList;

import java.util.List;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

public class History {
	private long table;
	private List<Result> results;

	public History(JSONObject json) {
		this.table = (long) json.get("table");
		this.results = jsonArrayToList((JSONArray) json.get("list"), Result.class);
	}

	public long getTable() {
		return table;
	}

	public void setTable(long table) {
		this.table = table;
	}

	public List<Result> getResults() {
		return results;
	}

	public void setResults(List<Result> results) {
		this.results = results;
	}

	@Override
	public String toString() {
		return "History [table=" + table + ", results=" + results + "]";
	}

}
