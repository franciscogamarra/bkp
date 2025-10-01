import ArrayLst from '../array/ArrayLst';
import Null from '../object/Null';

export default class StringSplit {

	static exec(s, separator) {

		if (Null.is(s)) {
			return new ArrayLst();
		}

		if (s.indexOf(separator) === -1) {
			return ArrayLst.build(s);
		}

		if (separator.length === 0) {

			let lst = new ArrayLst();

			while (s.length > 0) {
				lst.add(s.substring(0,1));
				s = s.substring(1);
			}

			return lst;

		}

		let sp = separator;

		let array = s.split(sp);

		let list = new ArrayLst();
		list.addArray(array);
		return list;

	}

}
