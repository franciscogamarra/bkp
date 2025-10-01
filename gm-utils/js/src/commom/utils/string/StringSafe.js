import StringEmpty from './StringEmpty';

export default class StringSafe {

	static get(s, def) {
		if (StringEmpty.is(s)) {
			return def;
		}
		return s;
	}

}
