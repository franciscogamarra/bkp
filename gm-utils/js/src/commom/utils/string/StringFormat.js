import StringLength from './StringLength';

export default class StringFormat {

	static leftPad(s, substring, len) {

		while (StringLength.get(s) < len) {
			s = substring + s;
		}

		return s;

	}

	static rightPad(s, substring, len) {

		while (StringLength.get(s) < len) {
			s += substring;
		}

		return s;

	}

}
