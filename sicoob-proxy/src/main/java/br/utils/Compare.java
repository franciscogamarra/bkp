package br.utils;

import java.sql.Timestamp;
import java.time.LocalDateTime;

import br.utils.strings.StringCompare;

public class Compare {

	public static int compare(Timestamp a, Timestamp b) {
		
		if (a == b) {
			return 0;
		}
		
		if (a == null) {
			return -1;
		}
		
		if (b == null) {
			return 1;
		}
		
		return a.compareTo(b);
		
	}
	
	public static int compare(LocalDateTime a, LocalDateTime b) {
		
		if (a == b) {
			return 0;
		}
		
		if (a == null) {
			return -1;
		}
		
		if (b == null) {
			return 1;
		}
		
		return a.compareTo(b);
		
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static boolean eq(Object a, Object b) {
		
		if (a == b) {
			return true;
		}
		
		if (a == null || b == null) {
			return true;
		}
		
		if (a.equals(b)) {
			return true;
		}
		
		if (a.getClass() != b.getClass()) {
			return false;
		}
		
		if (a instanceof String) {
			return StringCompare.eq((String) a, (String) b);
		}
		
		if (a instanceof Comparable) {
			Comparable ia = (Comparable) a;
			Comparable ib = (Comparable) b;
			return ia.compareTo(ib) == 0;
		}
		
		return false;
		
	}
	
}