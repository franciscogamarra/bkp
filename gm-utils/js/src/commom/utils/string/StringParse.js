import ArrayClear from '../array/ArrayClear';
import ClassSimpleName from '../classe/ClassSimpleName';
import Equals from '../object/Equals';
import Null from '../object/Null';
import Object from '../../../../js/Object';
import StringReplace from './StringReplace';

export default class StringParse {

	static get(o) {

		let start = Date.now();

		try {

			if (Null.is(o)) {
				return null;
			}

			if (o === "") {
				return "";
			}

			if (ClassSimpleName.isString(o)) {
				return o;
			}

			if (ClassSimpleName.isNumber(o)) {
				return "" + o;
			}

			if (ClassSimpleName.is(o, "Map")) {
				let map = o;
				return StringParse.get(Object.fromEntries(map));
			}

			let cache = [];
			let s = JSON.stringify(o, (key, value) => {
				if (Equals.is(value, null)) {
					return null;
				}
				if (ClassSimpleName.isObject(value)) {
					/* nao usar contains ou qualquer outro */
					if (cache.indexOf(value) > -1) {
						return null;
					} else {
						cache.push(value);
					}
				}
				return value;
			});

			ArrayClear.exec(cache);

			s = StringReplace.exec(s, "\"{", "{");
			s = StringReplace.exec(s, "}\"", "}");
			s = StringReplace.exec(s, "\"[", "[");
			s = StringReplace.exec(s, "]\"", "]");

			/* s = StringReplace.exec(s, "\\\"", "\""); */
			return StringReplace.exec(s, "\\\"", "\"");

		} finally {
			let end = Date.now();
			let dif = end - start;
			if (dif > 100) {
				console.log("ToString " + dif, o);
			}
		}
	}
}
