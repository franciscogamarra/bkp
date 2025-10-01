import IntegerIs from './IntegerIs';
import Null from '../object/Null';

export default class IntegerParse {

	static toIntDef(o, def) {
		if (IntegerIs.is(o)) {
			return IntegerParse.toInt(o);
		}
		try {
			o = IntegerParse.toInt(o);
			if (IntegerIs.is(o)) {
				return o;
			}
			return def;
		} catch (e) {
			return def;
		}

	}

	static toInt(o) {
		if (Null.is(o)) {
			return null;
		}
		return parseInt(o);
	}

}
