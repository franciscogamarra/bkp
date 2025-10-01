export default class Null {

	public static is(o : any) : boolean {
		return o === null || o === undefined;
	}

	public static isEmpty(o : any) : boolean {
		return Null.is(o) || o === "";
	}

}
