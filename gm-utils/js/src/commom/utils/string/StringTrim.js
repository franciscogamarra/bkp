import Null from '../object/Null';
import StringEmpty from './StringEmpty';
import StringReplace from './StringReplace';
import StringRight from './StringRight';

export default class StringTrim {

	static trim(s) {
		if (Null.is(s)) {
			return null;
		}
		return s.trim();
	}

	static plus(s) {

		if (Null.is(s)) {
			return null;
		}

		s = StringTrim.trim(s);

		s = StringReplace.exec(s, "\t", " ");
		s = StringReplace.exec(s, "\n", " ");
		s = StringReplace.exec(s, "  ", " ");

		return s.trim();

	}

	static plusNull(s) {
		s = StringTrim.plus(s);
		return StringEmpty.is(s) ? null : s;
	}

	static left(s) {
		if (StringEmpty.is(s)) {
			return "";
		}
		while (s.startsWith(" ") || s.startsWith("\t") || s.startsWith("\n")) {
			s = s.substring(1);
		}
		return s;
	}

	static right(s) {
		if (StringEmpty.is(s)) {
			return "";
		}
		while (s.endsWith(" ") || s.endsWith("\t") || s.endsWith("\n")) {
			s = StringRight.ignore1(s);
		}
		return s;
	}

}
