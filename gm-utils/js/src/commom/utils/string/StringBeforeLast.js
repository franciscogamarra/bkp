import IntegerCompare from '../integer/IntegerCompare';
import StringObrig from './StringObrig';

export default class StringBeforeLast {

	static get(s, substring) {
		let i = s.lastIndexOf(substring);
		if (IntegerCompare.eq(i, -1)) {
			return null;
		}
		return s.substring(0, i);
	}

	static obrig(s, substring) {
		return StringObrig.get(StringBeforeLast.get(s, substring));
	}

}
