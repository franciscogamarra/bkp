import StringCompare from '../string/StringCompare';

export default class ClassSimpleName {

	static exec(o) {

		let s = typeof(o);

		if (StringCompare.eqIgnoreCase(s, "string")) {
			return "string";
		}
		if (StringCompare.eq(s, "number")) {
			return "number";
		}

		let js = o;
		return js.__proto__.constructor.name;

	}

	static is(o, s) {
		return StringCompare.eq(ClassSimpleName.exec(o), s);
	}

	static isObject(o) {
		return StringCompare.eq(ClassSimpleName.exec(o), "Object");
	}

	static isString(o) {
		return StringCompare.eq(ClassSimpleName.exec(o), "string");
	}

	static isNumber(o) {
		return StringCompare.eq(ClassSimpleName.exec(o), "number");
	}

/* 	if (UNative.inJava) return !UType.isPrimitiva(o);
	return StringCompare.eq(Js.typeof(o), "object"); */

}
