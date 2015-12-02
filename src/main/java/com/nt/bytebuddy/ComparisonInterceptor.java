package com.nt.bytebuddy;

public class ComparisonInterceptor {
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public int compare(Object first, Object second) {
		return ((Comparable) first).compareTo(second);
	}
}