import IntegerCompare from '../integer/IntegerCompare';
import Null from '../object/Null';

export default class StringEmpty {

	static is(s) {
		return Null.is(s) || IntegerCompare.eq(s.trim().length, 0);
	}

	static notIs(s) {
		return !StringEmpty.is(s);
	}

}
