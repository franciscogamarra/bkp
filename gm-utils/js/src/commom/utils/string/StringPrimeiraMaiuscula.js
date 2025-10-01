import StringEmpty from './StringEmpty';
import StringTrim from './StringTrim';

export default class StringPrimeiraMaiuscula {

	static exec(s) {
		s = StringTrim.plus(s);
		if (StringEmpty.is(s)) {
			return s;
		}
		return s.substring(0, 1).toUpperCase() + s.substring(1);
	}

}
