package gm.utils.jpa.select;

public class SelectShort<TS extends SelectBase<?,?,?>> extends SelectTypedLogical<TS, Short> {

	private static final short ZERO = 0;
	
	public SelectShort(TS x, String campo) {
		super(x, campo);
	}

	public TS isNullOrZero() {
		isNull();
		c().or();
		eq(ZERO);
		return ts;
	}

	public TS isZero() {
		eq(ZERO);
		return ts;
	}
	
}
