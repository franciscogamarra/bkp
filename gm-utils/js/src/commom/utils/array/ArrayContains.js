import ArrayEmpty from './ArrayEmpty';
import ClassSimpleName from '../classe/ClassSimpleName';
import Equals from '../object/Equals';
import Null from '../object/Null';
import StringCompare from '../string/StringCompare';

export default class ArrayContains {

	static val(value, array) {

		if (array.indexOf(value) > -1) {
			return true;
		}

		if (Null.is(value)) {
			return ArrayContains.exists(array, o => Null.is(o));
		}

		if (ClassSimpleName.is(value, "String")) {
			let v = value;
			ArrayContains.exists(array, o => StringCompare.eq(v, o));
		}

		return ArrayContains.exists(array, o => Equals.is(o, value));

	}

	static exists(array,func) {
		if (ArrayEmpty.is0(array)) {
			return false;
		}
		return !ArrayEmpty.is0(array.filter(o => func(o)));
	}

}
