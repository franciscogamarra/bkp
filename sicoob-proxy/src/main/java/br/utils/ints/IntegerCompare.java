package br.utils.ints;

public class IntegerCompare {
	
	public static boolean eq(Integer a, Integer b) {
		
		if (a == b) {
			return true;
		}
		
		if (a == null || b == null) {
			return false;
		}
		
		return a.intValue() == b.intValue();
		
	}
	
	public static boolean ne(Integer a, Integer b) {
		return !eq(a, b);
	}
	
	public static int compare(Integer a, Integer b) {
		if (eq(a, b)) {
			return 0;
		}
		if (a == null) {
			return -1;
		} else if (b == null || a >= b) {
			return 1;
		} else {
			return -1;
		}
	}
	
	public static boolean isZero(Integer i) {
		return eq(i, 0);
	}
	
	public static boolean maior(Integer a, Integer b) {
		return eq(compare(a, b), 1);
	}
	
	public static Integer getMaior(Integer a, Integer b) {
		return compare(a, b) == -1 ? b : a;
	}
	
	public static Integer getMenor(Integer a, Integer b) {
		return compare(a, b) == -1 ? a : b;
	}
	
	public static boolean menor(Integer a, Integer b) {
		return eq(compare(a, b), -1);
	}
	
	public static boolean maiorOuIgual(Integer a, Integer b) {
		return maior(a, b) || eq(a, b);
	}
	
	public static boolean menorOuIgual(Integer a, Integer b) {
		return menor(a, b) || eq(a, b);
	}
	
}
