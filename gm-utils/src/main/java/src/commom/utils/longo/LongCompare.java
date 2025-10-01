package src.commom.utils.longo;

import src.commom.utils.object.Equals;
import src.commom.utils.object.Null;

public class LongCompare {

	public static boolean eq(Long a, Long b) {
		if (Equals.is(a, b)) {
			return true;
		}
		if (Null.is(a) || Null.is(b)) {
			return false;
		} else if ((a - b == 0L) || Equals.is(a+1, b+1)) {
			return true;
		} else {
			return false;
		}
	}
	
	public static boolean ne(Long a, Long b) {
		return !eq(a,b);
	}

	public static int compare(Long a, Long b) {
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

	public static boolean isZero(Long i) {
		return eq(i, 0L);
	}

}
