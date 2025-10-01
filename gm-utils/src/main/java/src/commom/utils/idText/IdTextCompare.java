package src.commom.utils.idText;

import src.commom.utils.integer.IntegerCompare;
import src.commom.utils.object.Null;

public class IdTextCompare {

	public static boolean isNull(IdText o) {
		return Null.is(o) || Null.is(o.getId());
	}

	public static boolean eq(IdText a, IdText b) {

		if (isNull(a)) {
			return isNull(b);
		}
		if (isNull(b)) {
			return false;
		} else {
			return IntegerCompare.eq(a.getId(), b.getId());
		}

	}

	public static int compare(IdText a, IdText b) {

		if (isNull(a)) {
			if (isNull(b)) {
				return 0;
			} else {
				return -1;
			}
		}
		if (isNull(b)) {
			return 1;
		} else {
			return IntegerCompare.compare(a.getId(), b.getId());
		}

	}

}
