package src.commom.utils.shortt;

import src.commom.utils.object.Equals;
import src.commom.utils.object.Null;

public class ShortCompare {
	
	public static Short parseShort(Integer i) {
		return i == null ? null : i.shortValue();
	}

	public static boolean eq(Short a, Integer b) {
		return eq(a, parseShort(b));
	}
	
	public static boolean eq(Short a, Short b) {
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
	
	public static boolean ne(Short a, Short b) {
		return !eq(a,b);
	}

	public static int compare(Short a, Short b) {
		if (eq(a,b)) {
			return 0;
		} else if (Null.is(a)) {
			return -1;
		} else if (Null.is(b)) {
			return 1;
		} else if (a > b) {
			return 1;
		} else {
			return -1;
		}
	}
	
	public static int compareNullsEnd(Short a, Short b) {
		if (eq(a,b)) {
			return 0;
		} else if (Null.is(a)) {
			return 1;
		} else if (Null.is(b)) {
			return -1;
		} else if (a > b) {
			return 1;
		} else {
			return -1;
		}
	}

	public static boolean isZero(Short i) {
		return eq(i, (short) 0);
	}

}
