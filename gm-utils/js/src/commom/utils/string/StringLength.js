import IntegerCompare from '../integer/IntegerCompare';
import Null from '../object/Null';

export default class StringLength {

	static get(s) {
		if (Null.is(s)) {
			return 0;
		}
		return s.length;
	}

	static is(s, size) {
		return IntegerCompare.eq(StringLength.get(s), size);
	}

	static max(s, max) {
		if (StringLength.get(s) > max) {
			return s.substring(0, max);
		}
		return s;
	}

}
