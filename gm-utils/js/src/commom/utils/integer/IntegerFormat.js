import Null from '../object/Null';

export default class IntegerFormat {

	static zerosEsquerda(value, casas) {
		if (Null.is(value)) {
			return "";
		}
		let s = ""+value;
		while (s.length < casas) {
			s = "0" + s;
		}
		return s;
	}

	static xx(value) {
		return IntegerFormat.zerosEsquerda(value, 2);
	}

	static xxx(value) {
		return IntegerFormat.zerosEsquerda(value, 3);
	}

	static xxxx(value) {
		return IntegerFormat.zerosEsquerda(value, 4);
	}

}
