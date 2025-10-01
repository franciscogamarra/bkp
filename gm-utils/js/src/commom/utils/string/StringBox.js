import Null from '../object/Null';
import ObjJs from '../object/ObjJs';
import StringCompare from './StringCompare';
import StringEmpty from './StringEmpty';
import StringReplace from './StringReplace';
import StringRight from './StringRight';
import UJson from '../object/UJson';

export default class StringBox extends ObjJs {

	constructor(s) {
		super();
		this.set(s);
	}

	get() {
		return this.value;
	}

	set(s) {
		if (Null.is(s)) {
			this.value = "";
		} else {
			this.value = s;
		}
		return this.value;
	}

	add(s) {
		this.set(this.get()+s);
		return this;
	}

	isEmpty() {
		return StringEmpty.is(this.value);
	}

	removeLeft(count) {
		let x = this.value.substring(0, count);
		this.value = this.value.substring(count);
		return x;
	}

	removeRight(count) {
		let x = StringRight.get(this.value, count);
		this.value = StringRight.ignore(this.value, count);
		return x;
	}

	replace(a, b) {
		this.set(StringReplace.exec(this.value, a, b));
		return this;
	}

	clear() {
		this.set("");
	}

	toString() {
		return this.value;
	}

	eq(s) {
		return StringCompare.eq(this.get(), s);
	}
	toJsonImpl() {
		return "{" + UJson.itemString("value", this.value) + "}";
	}
}
