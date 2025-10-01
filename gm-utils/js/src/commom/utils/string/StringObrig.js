import StringEmpty from './StringEmpty';

export default class StringObrig {

	static get(s) {
		return StringObrig.get2(s, "s is empty");
	}

	static get2(s, message) {
		if (StringEmpty.is(s)) {
			throw new Error(message);
		}
		return s;
	}

}
