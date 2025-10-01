import StringCompare from './StringCompare';
import StringRemoveAcentos from './StringRemoveAcentos';
import StringTrim from './StringTrim';

export default class StringEquivalente {

	static is(a, b) {
		return (
			StringCompare.eqIgnoreCase(
				StringTrim.plus(StringRemoveAcentos.exec(a)),
				StringTrim.plus(StringRemoveAcentos.exec(b))
			)
		);
	}

}
