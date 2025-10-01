import Equals from '../object/Equals';
import Null from '../object/Null';

export default class IntegerCompare {

	static eq(a, b) {
		if (Equals.is(a, b)) {
			return true;
		}
		if (Null.is(a) || Null.is(b)) {
			return false;
		} else if ((a - b === 0) || Equals.is(a+1, b+1)) {
			return true;
		} else {
			return false;
		}
	}

	static ne(a, b) {
		return !IntegerCompare.eq(a,b);
	}

	static compare(a, b) {
		if (IntegerCompare.eq(a,b)) {
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
		return IntegerCompare.eq(i, 0);
	}

	static maior(a, b) {
		return IntegerCompare.eq(IntegerCompare.compare(a, b), 1);
	}

	static menor(a, b) {
		return IntegerCompare.eq(IntegerCompare.compare(a, b), -1);
	}

	static maiorOuIgual(a, b) {
		return IntegerCompare.maior(a, b) || IntegerCompare.eq(a, b);
	}

	static menorOuIgual(a, b) {
		return IntegerCompare.menor(a, b) || IntegerCompare.eq(a, b);
	}

}
