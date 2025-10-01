package src.commom.utils.comum;

import src.commom.utils.integer.IntegerParse;

public class CoordenadasMovimento {

	private int xa;
	private int xb;
	private int ya;
	private int yb;
	private int inc;

	public CoordenadasMovimento(int xa, int xb, int ya, int yb, int inc) {

		if (inc < 1) {
			throw new RuntimeException("inc < 1");
		}
		if (xa < 0) {
			throw new RuntimeException("xa < 0");
		}
		if (xb < 0) {
			throw new RuntimeException("xb < 0");
		}
		if (ya < 0) {
			throw new RuntimeException("ya < 0");
		}
		if (yb < 0) {
			throw new RuntimeException("yb < 0");
		}

		this.inc = inc;
		this.xa = xa;
		this.xb = xb;
		this.ya = ya;
		this.yb = yb;
	}

	public boolean next() {

		if (xa == xb) {
			return y();
		}
		if (ya == yb) {
			return x();
		}

		int xc = dif(xa, xb);
		int yc = dif(xa, xb);

		if (xc > yc) {
			int i = IntegerParse.toInt(xc / yc);
			while (i > 0) {
				x();
				i--;
			}
			y();
		} else {
			int i = IntegerParse.toInt(yc / xc);
			while (i > 0) {
				y();
				i--;
			}
			x();
		}

		return true;

	}

	private boolean x() {
		if (xa == xb) {
			return false;
		}
		if (xa > xb) {
			xaDec();
		} else {
			xaInc();
		}
		return true;
	}

	private boolean y() {
		if (ya == yb) {
			return false;
		}
		if (ya > yb) {
			yaDec();
		} else {
			yaInc();
		}
		return true;
	}

	private static int dif(int a, int b) {
		return a > b ? a-b : b-a;
	}

	private void yaDec() {
		ya -= inc;
		if (ya < yb) {
			ya = yb;
		}
	}

	private void yaInc() {
		ya += inc;
		if (ya > yb) {
			ya = yb;
		}
	}

	private void xaDec() {
		xa -= inc;
		if (xa < xb) {
			xa = xb;
		}
	}

	private void xaInc() {
		xa += inc;
		if (xa > xb) {
			xa = xb;
		}
	}

}
