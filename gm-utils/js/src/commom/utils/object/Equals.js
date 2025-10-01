import Null from './Null';

export default class Equals {
	static is(a, b) {
		return a === b || (Null.is(a) && Null.is(b));
	}
}
