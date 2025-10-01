package src.commom.utils.integer;

import src.commom.utils.object.Equals;
import src.commom.utils.object.Null;

public class IntegerCompare {

	public static boolean eq(Integer a, Integer b) {
		if (Equals.is(a, b)) {
			return true;
		}
		if (Null.is(a) || Null.is(b)) {
			return false;
		} else if ((a - b == 0) || Equals.is(a+1, b+1)) {
			return true;
		} else {
			return false;
		}
	}

	public static boolean ne(Integer a, Integer b) {
		return !eq(a,b);
	}

	public static int compare(Integer a, Integer b) {
		if (eq(a,b)) {
			return 0;
		}
		if (Null.is(a)) {
			return -1;
		} else if (Null.is(b) || (a >= b)) {
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
