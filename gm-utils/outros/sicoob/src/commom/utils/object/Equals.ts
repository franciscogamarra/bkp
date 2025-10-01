import Null from "./Null";

export default class Equals {

	public static is(a : any, b : any) : boolean {
		return a === b || (Null.is(a) && Null.is(b));
	}
}
