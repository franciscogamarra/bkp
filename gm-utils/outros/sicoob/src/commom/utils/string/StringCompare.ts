import Equals from "../object/Equals";
import StringLength from "./StringLength";
import StringEmpty from "./StringEmpty";

export default class StringCompare {

	private constructor() {}

	public static eqq(a : string, b : string) : boolean {

		if (StringEmpty.is(a)) {
			return StringEmpty.is(b);
		}
		if (StringEmpty.is(b)) {
			return false;
		} else {
			return StringCompare.eq(a, b);
		}

	}

	public static eq(a : string, b : string) : boolean {

		if (Equals.is(a, b)) {
			return true;
		}
		if (StringLength.get(a) !== StringLength.get(b)) {
			return false;
		} else if (StringEmpty.is(a)) {
			return StringEmpty.is(b);
		} else if (StringEmpty.is(b)) {
			return false;
		}

		/* garante que a comparacao seja por conteudo e não por referencia */
		a += "";
		b += "";

		return Equals.is(a, b);

	}

	public static eqIgnoreCase(a : string, b : string) : boolean {

		if (StringEmpty.is(a)) {
			return StringEmpty.is(b);
		}
		if (StringEmpty.is(b)) {
			return false;
		} else {
			return StringCompare.eq(a.toLowerCase(), b.toLowerCase());
		}

	}

	public static compare(a : string, b : string) : number {
		if (StringCompare.eq(a, b)) {
			return 0;
		}
		if (StringEmpty.is(a)) {
			return -1;
		} else if (StringEmpty.is(b) || a.toLowerCase().startsWith(b.toLowerCase())) {
			return 1;
		} else if (b.toLowerCase().startsWith(a.toLowerCase())) {
			return -1;
		} else {
			return a.localeCompare(b);
		}
	}

	public static compareNumeric(a : string, b : string) : number {

		if (StringCompare.eq(a, b)) {
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
			return a.localeCompare(b);
		}

	}

}
