import StringEmpty from './StringEmpty';

export default class StringPrimeiraMinuscula {

	static exec(s) {
		if (StringEmpty.is(s)) {
			return s;
		}
		return s.substring(0, 1).toLowerCase() + s.substring(1);
	}

}
