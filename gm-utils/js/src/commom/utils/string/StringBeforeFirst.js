import IntegerCompare from '../integer/IntegerCompare';
import Null from '../object/Null';
import StringObrig from './StringObrig';

export default class StringBeforeFirst {

	static get(s, substring) {

		let i = s.indexOf(substring);

		if (IntegerCompare.eq(i, -1)) {
			return null;
		}

		if (i === 0) {
			return "";
		}

		return s.substring(0, i);

	}

	static safe(s, substring) {
		s = StringBeforeFirst.get(s, substring);
		if (Null.is(s)) {
			return "";
		}
		return s;
	}

	static obrig(s, substring) {
		return StringObrig.get(StringBeforeFirst.get(s, substring));
	}

}
