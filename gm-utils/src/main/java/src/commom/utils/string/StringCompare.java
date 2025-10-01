package src.commom.utils.string;

import js.Js;
import src.commom.utils.object.Equals;

public class StringCompare {

	private StringCompare() {}

	public static boolean eqq(String a, String b) {

		if (StringEmpty.is(a)) {
			return StringEmpty.is(b);
		}
		if (StringEmpty.is(b)) {
			return false;
		} else {
			return eq(a, b);
		}

	}

	public static boolean eq(String a, String b) {

		if (Equals.is(a, b)) {
			return true;
		}
		if (StringLength.get(a) != StringLength.get(b)) {
			return false;
		} else if (StringEmpty.is(a)) {
			return StringEmpty.is(b);
		} else if (StringEmpty.is(b)) {
			return false;
		}

		if (Js.inJava) {
			return a.contentEquals(b);
		}

		/* garante que a comparacao seja por conteudo e não por referencia */
		a += "";
		b += "";

		return Equals.is(a, b);

	}
	
	public static boolean ne(String a, String b) {
		return !eq(a,b);
	}

	public static boolean eqIgnoreCase(String a, String b) {

		if (StringEmpty.is(a)) {
			return StringEmpty.is(b);
		}
		if (StringEmpty.is(b)) {
			return false;
		} else {
			return eq(a.toLowerCase(), b.toLowerCase());
		}

	}

	public static int compare(String a, String b) {
		if (eq(a, b)) {
			return 0;
		}
		if (StringEmpty.is(a)) {
			return -1;
		} else if (StringEmpty.is(b) || a.toLowerCase().startsWith(b.toLowerCase())) {
			return 1;
		} else if (b.toLowerCase().startsWith(a.toLowerCase())) {
			return -1;
		} else {
			return a.compareTo(b);
		}
	}

	public static int compareNumeric(String a, String b) {

		if (eq(a, b)) {
			return 0;
		}
		if (StringEmpty.is(a)) {
			return -1;
		} else if (StringEmpty.is(b)) {
			return 1;
		} else if (StringLength.get(a) < StringLength.get(b)) {
			return -1;
		} else if (StringLength.get(b) < StringLength.get(a)) {
			return 1;
		} else {
			return a.compareTo(b);
		}

	}

}
