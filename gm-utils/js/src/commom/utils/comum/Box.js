import Null from '../object/Null';

export default class Box {
	constructor(valueP) {
		this.set(valueP);
	}
	get() {
		return this.value;
	}
	set(valueP) {
		this.value = valueP;
	}
	isNotNull() {
		return !this.isNull();
	}
	isNull() {
		return Null.is(this.value);
	}
}
