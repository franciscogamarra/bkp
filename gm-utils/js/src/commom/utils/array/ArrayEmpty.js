import IntegerCompare from '../integer/IntegerCompare';
import Null from '../object/Null';

export default class ArrayEmpty {

	static is0(o) {
		return Null.is(o) || IntegerCompare.eq(o.length, 0);
	}

	static is(o) {
		return Null.is(o) || IntegerCompare.eq(o.size(), 0);
	}

}
