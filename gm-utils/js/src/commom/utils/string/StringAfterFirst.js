import IntegerCompare from '../integer/IntegerCompare';
import StringObrig from './StringObrig';

export default class StringAfterFirst {

	static get(s, substring) {
		let i = s.indexOf(substring);
		if (IntegerCompare.eq(i, -1)) {
			return null;
		}
		i += substring.length;
		return s.substring(i);
	}

	static obrig(s, substring) {
		return StringObrig.get(StringAfterFirst.get(s, substring));
	}

}
