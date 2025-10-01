package br.utils.strings;

public class StringRight {
	
	public static String ignore1(String s) {
		return ignore(s, 1);
	}
	
	public static String ignore(String s, int count) {
		
		if (StringEmpty.is(s)) {
			return "";
		}
		
		int len = StringLength.get(s);
		
		if (len <= count) {
			return "";
		}
		return s.substring(0, len - count);
		
	}
	
	public static String get(String s, int count) {
		
		if (StringEmpty.is(s)) {
			return "";
		}
		
		int len = StringLength.get(s);
		
		if (len < count) {
			return s;
		}
		
		return s.substring(len - count);
		
	}
	
	private StringRight() {
	}
	
}