export default class IntegerBox {

	constructor(i) {
		this.set(i);
	}
	set(i) {
		this.value = i;
		return this.value;
	}
	get() {
		return this.value;
	}
	inc1() {
		return this.inc(1);
	}
	inc(i) {
		return this.set(this.value+i);
	}
	dec1() {
		return this.dec(1);
	}
	dec(i) {
		return this.set(this.value-i);
	}
	toString() {
		return ""+this.value;
	}

}
