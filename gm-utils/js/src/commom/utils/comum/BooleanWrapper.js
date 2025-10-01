import Null from '../object/Null';

export default class BooleanWrapper {

	get() {
		return this.value;
	}

	set(avalue) {
		this.value = avalue;
		return this;
	}

	isTrue() {
		return UBoolean.isTrue(this.value);
	}

	isFalse() {
		return UBoolean.isFalse(this.value);
	}

	isNull() {
		return Null.is(this.value);
	}

}
