import Null from './Null';

export default class Obrig {

	static check(o) {
		return Obrig.checkWithMessage(o, "Obrig.check: o is null");
	}

	static checkWithMessage(o, message) {
		if (Null.is(o)) {
			throw new Error(message);
		}
		return o;
	}

	static checkWithDynamicMessage(o, getMessage) {
		if (Null.is(o)) {
			throw new Error(getMessage());
		}
		return o;
	}

}
