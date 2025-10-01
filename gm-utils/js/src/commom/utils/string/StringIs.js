import Null from '../object/Null';
import StringCompare from './StringCompare';

export default class StringIs {

	static is(o) {
		if (Null.is(o)) {
			return false;
		}
		return StringCompare.eq(typeof(o), "string");
	}

}
