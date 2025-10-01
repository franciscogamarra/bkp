import DoubleIs from './DoubleIs';

export default class DoubleParse {

	static toDouble(o) {
		if (DoubleIs.is(o)) {
			return parseFloat(o);
		}
		throw new Error("NaN");
	}

	static toDoubleDef(o, def) {
		if (DoubleIs.is(o)) {
			return parseFloat(o);
		}
		return def;
	}

}
