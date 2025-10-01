import Null from '../object/Null';
import StringCompare from './StringCompare';
import StringContains from './StringContains';
import StringRight from './StringRight';
import StringSplit from './StringSplit';

export default class StringReplace {

	static execPrivate(s, a, b) {

		if (Null.is(s) || Null.is(a) || s.length === 0 || !StringContains.is(s, a)) {
			return s;
		}

		if (Null.is(b)) {
			b = "";
		}

		let split = StringSplit.exec(s, a);
		s = split.join(b);

		if (StringContains.is(s, a)) {
			return StringReplace.execPrivate(s, a, b);
		}
		return s;

	}

	static exec(s, a, b) {

		if (StringCompare.eq(s, a)) {
			return b;
		}

		if (!StringContains.is(s, a)) {
			return s;
		}

		let before = "";

		while (s.startsWith(a)) {
			before += b;
			s = s.substring(a.length);
		}

		if (s.length === 0) {
			return before;
		}

		let after = "";

		while (s.endsWith(a)) {
			after = b + after;
			s = StringRight.ignore(s, a.length);
		}

		let aux = StringReplace.OCORRENCIA_IMPROVAVEL;
		if (StringContains.is(aux, a)) {
			aux = StringReplace.execPrivate(aux, a, "");
		}

		if (StringContains.is(b, a)) {
			s = StringReplace.execPrivate(s, a, aux);
			s = StringReplace.execPrivate(s, aux, b);
		} else {
			s = StringReplace.execPrivate(s, a, b);
		}

		return before + s + after;

	}

	static ifContains(s, a, b) {
		if (StringContains.is(s, a)) {
			return StringReplace.exec(s, a, b());
		}
		return s;
	}

}
StringReplace.OCORRENCIA_IMPROVAVEL = "@[%85!2#$%]@))(6¬¢";
