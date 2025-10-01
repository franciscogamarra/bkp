import Null from '../object/Null';

export default class StringContains {

	static is(a, b) {
		if (Null.is(a) || Null.is(b)) {
			return false;
		}
		if (b.length === 0) {
			return true;
		}
		if (a.length === 0) {
			return false;
		}
		return a.indexOf(b) > -1;
	}

}
