import StringCompare from './StringCompare';
import StringEmpty from './StringEmpty';
import StringTrim from './StringTrim';

export default class StringEmptyPlus {

	static is(s) {

		if (StringEmpty.is(s)) {
			return true;
		}

		s = StringTrim.plus(s).toLowerCase();

		if (StringCompare.eq(s, "null")) {
			return true;
		}
		if (StringCompare.eq(s, "undefined")) {
			return true;
		} else {
			return false;
		}

	}

}
