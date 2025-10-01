import StringEmpty from './StringEmpty';
import StringLength from './StringLength';

export default class StringRight {

	static ignore1(s) {
		return StringRight.ignore(s, 1);
	}

	static ignore(s, count) {

		if (StringEmpty.is(s)) {
			return "";
		}

		let len = StringLength.get(s);

		if (len <= count) {
			return "";
		}
		return s.substring(0, len - count);

	}

	static get(s, count) {
		let len = StringLength.get(s);
		if (len < count) {
			return s;
		}
		return s.substring(len - count);
	}

}
