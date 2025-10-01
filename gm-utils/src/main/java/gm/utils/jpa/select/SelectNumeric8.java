package gm.utils.jpa.select;

import gm.utils.number.Numeric8;

public class SelectNumeric8<TS extends SelectBase<?,?,?>> extends SelectBigDecimal<TS, Numeric8> {
	public SelectNumeric8(TS x, String campo) {
		super(x, campo);
	}
}