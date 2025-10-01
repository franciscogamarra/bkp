import IntegerCompare from '../integer/IntegerCompare';
import StringObrig from './StringObrig';

export default class StringAfterLast {

	static get(s, substring) {
		let i = s.lastIndexOf(substring);
		if (IntegerCompare.eq(i, -1)) {
			return null;
		}
		return s.substring(i + substring.length);
	}

	static obrig(s, substring) {
		return StringObrig.get(StringAfterLast.get(s, substring));
	}

}
