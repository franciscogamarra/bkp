package src.commom.utils.outros;

import js.JsObject;
import js.outros.ObjJson;
import src.commom.utils.object.Null;
import src.commom.utils.string.StringAfterFirst;
import src.commom.utils.string.StringBeforeFirst;
import src.commom.utils.string.StringEmpty;
import src.commom.utils.string.StringSplit;

public class HttpUtils {

	public static String getQueryParams(ObjJson o) {
		
		if (Null.is(o)) {
			return "";
		}
		
		String s = JsObject.keys(o).map(key -> {
			
			Object value = o.array(key);
			
			if (Null.is(value)) {
				return "";
			}
			
			String ss = value + "";
			
			if (StringEmpty.is(ss)) {
				return "";
			}
			
			ss = ss.replace(" ", "%20");
			
			return "&" + key + "=" + ss;
			
		}).join("");
		
		if (StringEmpty.is(s)) {
			return "";
		} else {
			return "?" + s.substring(1);
		}
		
	}
	
	public static void setQueryParams(ObjJson o, String params) {
		
		if (StringEmpty.is(params)) {
			return;
		}
		
		StringSplit.exec(params, "&").forEach(s -> {
			String key = StringBeforeFirst.get(s, "=");
			String value = StringAfterFirst.get(s, "=").replace("%20", " ");
			o.arraySet(key, value);
		});
		
	}

}