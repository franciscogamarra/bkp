package gm.utils.jpa.select;

import gm.utils.number.Numeric18;

public class SelectNumeric18<TS extends SelectBase<?,?,?>> extends SelectBigDecimal<TS, Numeric18> {
	public SelectNumeric18(TS x, String campo) {
		super(x, campo);
	}
}