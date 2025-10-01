import StringEmpty from '../string/StringEmpty';
import StringExtraiNumeros from '../string/StringExtraiNumeros';
import StringSplit from '../string/StringSplit';

export default class SeparaMilhares {

	static exec(s) {
		s = StringExtraiNumeros.exec(s);
		if (StringEmpty.is(s)) {
			return "";
		}
		let split = StringSplit.exec(s, "");
		s = "";
		while (!split.isEmpty()) {
			s = split.pop() + s;
			if (!split.isEmpty()) {
				s = split.pop() + s;
				if (!split.isEmpty()) {
					s = split.pop() + s;
					if (!split.isEmpty()) {
						s = "." + s;
					}
				}
			}
		}
		return s;
	}

}
