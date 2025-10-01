import JsMap from './JsMap';
import Null from '../object/Null';
import StringAfterFirst from '../string/StringAfterFirst';
import StringBeforeFirst from '../string/StringBeforeFirst';
import StringEmpty from '../string/StringEmpty';
import StringReplace from '../string/StringReplace';
import StringSplit from '../string/StringSplit';

export default class Browser {

	static queryParams;

	static getQueryParams() {
		if (Null.is(Browser.queryParams)) {
			Browser.queryParams = new JsMap();
			let s = window.location.search;
			Browser.setQueryParams(s, Browser.queryParams);
		}
		return Browser.queryParams;
	}

	static setQueryParams(s,map) {

		if (StringEmpty.is(s)) {
			return;
		}

		if (s.startsWith("?")) {
			s = s.substring(1);
		}

		let itens = StringSplit.exec(s, "&");

		itens.forEach(ss => {

			let key = StringBeforeFirst.get(ss, "=");
			key = StringReplace.exec(key, "%", " ");

			let value = StringAfterFirst.get(ss, "=");
			value = StringReplace.exec(value, "%", " ");

			map.set(key, value);
		});

	}

}
