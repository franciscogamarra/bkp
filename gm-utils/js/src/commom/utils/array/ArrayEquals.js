import ArrayEmpty from './ArrayEmpty';
import ArrayLength from './ArrayLength';
import ArrayLst from './ArrayLst';
import Equals from '../object/Equals';
import IntegerCompare from '../integer/IntegerCompare';
import Null from '../object/Null';

export default class ArrayEquals {

	static size(a, b) {
		return IntegerCompare.eq(ArrayLength.get(a), ArrayLength.get(b));
	}

	static size0(a, b) {
		return IntegerCompare.eq(ArrayLength.get0(a), ArrayLength.get0(b));
	}

	static is0(a, b) {
		return ArrayEquals.is(a, b, (x,y) => Equals.is(x, y));
	}

	static is(a, b, comparator) {
		if (!ArrayEquals.size(a, b)) {
			return false;
		}
		if (ArrayEmpty.is(a)) {
			return ArrayEmpty.is(b);
		} else if (ArrayEmpty.is(b)) {
			return false;
		} else {
			for (let i = 0; i < a.size(); i++) {
				if (!comparator(a.get(i), b.get(i))) {
					return false;
				}
			}
			return true;
		}
	}

	static isT(a, b, comparator) {
		if (!ArrayEquals.size(a, b)) {
			return false;
		}
		if (ArrayEmpty.is(a)) {
			return ArrayEmpty.is(b);
		} else if (ArrayEmpty.is(b)) {
			return false;
		} else {
			for (let i = 0; i < a.size(); i++) {
				if (!comparator(a.get(i), b.get(i))) {
					return false;
				}
			}
			return true;
		}
	}

	static isT0(a, b, comparator) {
		if (!ArrayEquals.size0(a, b)) {
			return false;
		}
		if (ArrayEmpty.is0(a)) {
			return ArrayEmpty.is0(b);
		} else if (ArrayEmpty.is0(b)) {
			return false;
		} else {
			for (let i = 0; i < a.length; i++) {
				if (!comparator(a.get(i), b.get(i))) {
					return false;
				}
			}
			return true;
		}
	}

	static equalsNotification(a, b) {

		if (Null.is(a)) {
			return Null.is(b);
		}
		if (Null.is(b) || (a.size() !== b.size())) {
			return false;
		}

		return ArrayEquals.is(a, b, (x,y) => {
			if (x instanceof ArrayLst) {
				let xx = x;
				let yy = y;
				return ArrayEquals.equalsNotification(xx, yy);
			}
			return a === b;
		});

	}

}
