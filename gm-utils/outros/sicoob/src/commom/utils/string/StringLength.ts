import IntegerCompare from "../integer/IntegerCompare";
import Null from "../object/Null";

export default class StringLength {

	private constructor() {}

	public static get(s : string) : number {
		if (Null.is(s)) {
			return 0;
		}
		return s.length;
	}

	public static is(s : string, size : number) : boolean {
		return IntegerCompare.eq(StringLength.get(s), size);
	}

	public static max(s : string, max : number) : string {
		if (StringLength.get(s) > max) {
			return s.substring(0, max);
		}
		return s;
	}

}
