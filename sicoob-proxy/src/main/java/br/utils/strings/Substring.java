package br.utils.strings;

import br.utils.lambda.F0;
import br.utils.lambda.F1;

public class Substring {
	
	private static String get(String s, String sub, F0<Integer> getIndex, F1<Integer, String> func) {

		if (s == null || sub == null) {
			return "";
		}
		
		int i = getIndex.call();
		
		if (i == -1) {
			return "";
		}
		
		return func.call(i);
		
	}

	private static String first(String s, String sub, F1<Integer, String> func) {
		return get(s, sub, () -> s.indexOf(sub),  func);
	}
	
	private static String last(String s, String sub, F1<Integer, String> func) {
		return get(s, sub, () -> s.lastIndexOf(sub),  func);
	}
	
	public static String beforeFirst(String s, String sub) {
		return first(s, sub, i -> i.equals(0) ? "" : s.substring(0, i));
	}

	public static String beforeLast(String s, String sub) {
		return last(s, sub, i -> i.equals(0) ? "" : s.substring(0, i));
	}
	
	public static String afterFirst(String s, String sub) {
		return first(s, sub, i -> i.equals(0) ? "" : s.substring(i + sub.length()));
	}
	
	public static String afterLast(String s, String sub) {
		return last(s, sub, i -> i.equals(0) ? "" : s.substring(i + sub.length()));
	}	
	

	public static String somenteNumeros(String s) {
	    return s == null ? "" : s.replaceAll("[^0-9]", "");
	}	
	
}
