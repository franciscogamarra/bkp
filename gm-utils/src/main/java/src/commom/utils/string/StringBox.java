package src.commom.utils.string;

import src.commom.utils.object.Null;
import src.commom.utils.object.ObjJs;
import src.commom.utils.object.UJson;

public class StringBox extends ObjJs {

	private String value = "";

	public StringBox(String s) {
		set(s);
	}
	
	public String get() {
		return value;
	}

	public String set(String s) {
		if (Null.is(s)) {
			value = "";
		} else {
			value = s;
		}
		return value;
	}

	public StringBox add(String s) {
		set(get()+s);
		return this;
	}

	public boolean isEmpty() {
		return StringEmpty.is(value);
	}

	public String removeLeft(int count) {
		String x = value.substring(0, count);
		value = value.substring(count);
		return x;
	}

	public String removeRight(int count) {
		String x = StringRight.get(value, count);
		value = StringRight.ignore(value, count);
		return x;
	}

	public StringBox replace(String a, String b) {
		set(StringReplace.exec(value, a, b));
		return this;
	}

	public StringBox replaceWhile(String a, String b) {
		set(StringReplace.whilee(value, a, b));
		return this;
	}
	
	public void clear() {
		set("");
	}

	@Override
	public String toString() {
		return value;
	}

	public boolean eq(String s) {
		return StringCompare.eq(get(), s);
	}
	@Override
	protected String toJsonImpl() {
		return "{" + UJson.itemString("value", value) + "}";
	}
}
