import IntegerCompare from '../integer/IntegerCompare';
import Null from '../object/Null';

export default class IdTextCompare {

	static isNull(o) {
		return Null.is(o) || Null.is(o.id);
	}

	static eq(a, b) {

		if (IdTextCompare.isNull(a)) {
			return IdTextCompare.isNull(b);
		}
		if (IdTextCompare.isNull(b)) {
			return false;
		} else {
			return IntegerCompare.eq(a.id, b.id);
		}

	}

	static compare(a, b) {

		if (IdTextCompare.isNull(a)) {
			if (IdTextCompare.isNull(b)) {
				return 0;
			} else {
				return -1;
			}
		}
		if (IdTextCompare.isNull(b)) {
			return 1;
		} else {
			return IntegerCompare.compare(a.id, b.id);
		}

	}

}
