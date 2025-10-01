import Equals from '../object/Equals';
import Null from '../object/Null';

export default class LongCompare {

	static eq(a, b) {
		if (Equals.is(a, b)) {
			return true;
		}
		if (Null.is(a) || Null.is(b)) {
			return false;
		} else if ((a - b === 0L) || Equals.is(a+1, b+1)) {
			return true;
		} else {
			return false;
		}
	}

	static compare(a, b) {
		if (LongCompare.eq(a,b)) {
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

	static isZero(i) {
		return LongCompare.eq(i, 0L);
	}

}
