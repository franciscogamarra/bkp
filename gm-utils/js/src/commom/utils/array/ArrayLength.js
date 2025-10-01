import Null from '../object/Null';

export default class ArrayLength {

	static get(a) {
		if (Null.is(a)) {
			return 0;
		}
		return a.size();
	}

	static get0(a) {
		if (Null.is(a)) {
			return 0;
		}
		return a.length;
	}

}
