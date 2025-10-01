export default class Null {

	static is(o) {
		return o === null || o === undefined;
	}

	static isEmpty(o) {
		return Null.is(o) || o === "";
	}

}
