import IntegerParse from '../integer/IntegerParse';

export default class CoordenadasMovimento {

	xa = 0;
	xb = 0;
	ya = 0;
	yb = 0;
	inc = 0;

	constructor(xa, xb, ya, yb, inc) {

		if (inc < 1) {
			throw new Error("inc < 1");
		}
		if (xa < 0) {
			throw new Error("xa < 0");
		}
		if (xb < 0) {
			throw new Error("xb < 0");
		}
		if (ya < 0) {
			throw new Error("ya < 0");
		}
		if (yb < 0) {
			throw new Error("yb < 0");
		}

		this.inc = inc;
		this.xa = xa;
		this.xb = xb;
		this.ya = ya;
		this.yb = yb;
	}

	next() {

		if (this.xa === this.xb) {
			return this.y();
		}
		if (this.ya === this.yb) {
			return this.x();
		}

		let xc = CoordenadasMovimento.dif(this.xa, this.xb);
		let yc = CoordenadasMovimento.dif(this.xa, this.xb);

		if (xc > yc) {
			let i = IntegerParse.toInt(xc / yc);
			while (i > 0) {
				this.x();
				i--;
			}
			this.y();
		} else {
			let i = IntegerParse.toInt(yc / xc);
			while (i > 0) {
				this.y();
				i--;
			}
			this.x();
		}

		return true;

	}

	x() {
		if (this.xa === this.xb) {
			return false;
		}
		if (this.xa > this.xb) {
			this.xaDec();
		} else {
			this.xaInc();
		}
		return true;
	}

	y() {
		if (this.ya === this.yb) {
			return false;
		}
		if (this.ya > this.yb) {
			this.yaDec();
		} else {
			this.yaInc();
		}
		return true;
	}

	static dif(a, b) {
		return a > b ? a-b : b-a;
	}

	yaDec() {
		this.ya -= this.inc;
		if (this.ya < this.yb) {
			this.ya = this.yb;
		}
	}

	yaInc() {
		this.ya += this.inc;
		if (this.ya > this.yb) {
			this.ya = this.yb;
		}
	}

	xaDec() {
		this.xa -= this.inc;
		if (this.xa < this.xb) {
			this.xa = this.xb;
		}
	}

	xaInc() {
		this.xa += this.inc;
		if (this.xa > this.xb) {
			this.xa = this.xb;
		}
	}

}
