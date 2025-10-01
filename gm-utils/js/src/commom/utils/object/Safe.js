import Null from './Null';

export default class Safe {

	static get(o,getter, other) {
		if (Null.is(o)) {
			return other();
		}
		return getter(o);
	}

	static safeNullPointer(o,getter, other) {
		if (Null.is(o)) {
			return other();
		}
		try {
			return getter(o);
		} catch (e) {
			return other();
		}
	}

}
