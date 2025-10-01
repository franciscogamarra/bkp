package br.utils.strings;

import java.util.Collection;

public class StringCompare {
	
	private StringCompare() {
	}
	
	public static boolean eqq(String a, String b) {
		
		if (StringEmpty.is(a)) {
			return StringEmpty.is(b);
		}
		if (StringEmpty.is(b)) {
			return false;
		} else {
			return eq(a, b);
		}
		
	}
	
	public static boolean eq(String a, String b) {
		if (a == b) {
			return true;
		} else if (a == null || b == null) {
			return false;
		} else {
			return a.contentEquals(b);
		}
	}
	
	public static boolean ne(String a, String b) {
		return !eq(a, b);
	}
	
	public static boolean eqIgnoreCase(String a, String b) {
		
		if (StringEmpty.is(a)) {
			return StringEmpty.is(b);
		}
		if (StringEmpty.is(b)) {
			return false;
		} else {
			return eq(a.toLowerCase(), b.toLowerCase());
		}
		
	}
	
	public static int compare(String a, String b) {
		if (eq(a, b)) {
			return 0;
		}
		if (StringEmpty.is(a)) {
			return -1;
		} else if (StringEmpty.is(b) || a.toLowerCase().startsWith(b.toLowerCase())) {
			return 1;
		} else if (b.toLowerCase().startsWith(a.toLowerCase())) {
			return -1;
		} else {
			return a.compareTo(b);
		}
	}
	
	public static int compareNumeric(String a, String b) {
		
		if (eq(a, b)) {
			return 0;
		}
		if (StringEmpty.is(a)) {
			return -1;
		} else if (StringEmpty.is(b)) {
			return 1;
		} else if (StringLength.get(a) < StringLength.get(b)) {
			return -1;
		} else if (StringLength.get(b) < StringLength.get(a)) {
			return 1;
		} else {
			return a.compareTo(b);
		}
		
	}
	
	public static boolean any(String s, Collection<String> itens) {
		return itens.stream().anyMatch(i -> StringCompare.eq(i, s));
	}	
	
}
