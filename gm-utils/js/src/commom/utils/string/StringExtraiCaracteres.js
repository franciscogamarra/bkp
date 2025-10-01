import StringEmpty from './StringEmpty';

export default class StringExtraiCaracteres {

	static exec(s, list) {

		if (StringEmpty.is(s)) {
			return "";
		}

		let s2 = "";

		while (!StringEmpty.is(s)) {
			let x = s.substring(0, 1);
			s = s.substring(1);
			if (list.contains(x)) {
				s2 += x;
			}
		}

		return s2;

	}

}
