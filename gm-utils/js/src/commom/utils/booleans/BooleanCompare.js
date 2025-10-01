import Null from '../object/Null';

export default class BooleanCompare {

	static eq(a, b) {
		return BooleanCompare.compare(a, b) === 0;
	}

	static compare(a, b) {
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
