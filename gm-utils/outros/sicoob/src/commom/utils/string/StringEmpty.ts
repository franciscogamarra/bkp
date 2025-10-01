import IntegerCompare from "../integer/IntegerCompare";
import Null from "../object/Null";

export default class StringEmpty {

	private constructor() {}

	public static is(s : string) : boolean {
		return Null.is(s) || IntegerCompare.eq(s.trim().length, 0);
	}

	public static notIs(s : string) : boolean {
		return !StringEmpty.is(s);
	}

}
