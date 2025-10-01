package gm.utils.jpa;

import gm.utils.abstrato.ExtraidId;
import gm.utils.comum.UType;
import gm.utils.date.Data;
import gm.utils.exception.UException;
import src.commom.utils.string.StringParse;

public class SqlNativeValue {
	public static String get(Object o) {
		if (o == null) {
			return "null";
		}
		if (UType.isData(o)) {
			return Data.to(o).format_sql(true);
		}
		if (o instanceof String) {
			String s = o.toString();
			if (s.startsWith("=")) {
				s = s.substring(1);
			} else {
				s = "'" + s + "'";
			}
			return s;
		}
		if (UType.isPrimitiva(o)) {
			return StringParse.get(o);
		}
		
		Object id = ExtraidId.exec(o);
		if (id == null) {
			throw UException.runtime("???: " + o);
		}
		return id.toString();
	}
}
