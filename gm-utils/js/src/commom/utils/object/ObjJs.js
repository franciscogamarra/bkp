import StringContains from '../string/StringContains';
import StringEmpty from '../string/StringEmpty';
import UJson from './UJson';

export default class ObjJs {

	toString() {
		return this.toJSON();
	}

	toJSON() {

		let s = this.toJsonImpl();

		if (StringEmpty.is(s)) {
			return null;
		}

		s = ObjJs.replace(s, " ,", ",");
		s = ObjJs.replace(s, ",,", ",");
		s = ObjJs.replace(s, " }", "}");
		s = ObjJs.replace(s, ",}", "}");
		s = ObjJs.replace(s, " ]", "]");
		return ObjJs.replace(s, ",]", "]");

	}

	/* nao utilizar StringReplace pois dá referencia circular */
	static replace(s, a, b) {
		while (StringContains.is(s, a)) {
			s = s.replace(a, b);
		}
		return s;
	}

	static itemString(key, valueP) {
		return UJson.itemString(key, valueP);
	}

	static itemInteger(key, valueP) {
		return UJson.itemInteger(key, valueP);
	}

	static itemBoolean(key, valueP) {
		return UJson.itemBoolean(key, valueP);
	}

	static itemObj(key, valueP) {
		return UJson.itemObj(key, valueP);
	}

}
