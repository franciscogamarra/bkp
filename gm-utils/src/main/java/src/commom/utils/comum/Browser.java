package src.commom.utils.comum;

import js.Js;
import src.commom.utils.array.Itens;
import src.commom.utils.object.Null;
import src.commom.utils.string.StringAfterFirst;
import src.commom.utils.string.StringBeforeFirst;
import src.commom.utils.string.StringEmpty;
import src.commom.utils.string.StringReplace;
import src.commom.utils.string.StringSplit;

public class Browser {

	private static JsMap<String, String> queryParams;

	public static JsMap<String, String> getQueryParams() {
		if (Null.is(queryParams)) {
			queryParams = new JsMap<>();
			String s = Js.window.location.search;
			Browser.setQueryParams(s, Browser.queryParams);
		}
		return queryParams;
	}

	public static void setQueryParams(String s, JsMap<String, String> map) {

		if (StringEmpty.is(s)) {
			return;
		}

		if (s.startsWith("?")) {
			s = s.substring(1);
		}

		Itens<String> itens = StringSplit.exec(s, "&");

		itens.forEach(ss -> {

			String key = StringBeforeFirst.get(ss, "=");
			key = StringReplace.exec(key, "%", " ");

			String value = StringAfterFirst.get(ss, "=");
			value = StringReplace.exec(value, "%", " ");

			map.set(key, value);
		});

	}

}
