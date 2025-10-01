package src.commom.utils.booleans;

import src.commom.utils.object.Null;

public class BooleanCompare {

	public static boolean eq(Boolean a, Boolean b) {
		return compare(a, b) == 0;
	}

	public static int compare(Boolean a, Boolean b) {
		if (Null.is(a)) {
			if (Null.is(b)) {
				return 0;
			} else {
				return -1;
			}
		}
		if (Null.is(b)) {
			return 1;
		} else if (a) {
			if (b) {
				return 0;
			} else {
				return 1;
			}
		} else if (b) {
			return -1;
		} else {
			return 0;
		}
	}

}
