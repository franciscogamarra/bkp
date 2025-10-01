package gm.utils.jpa.select;

public class SelectObject<TS extends SelectBase<?,?,?>> extends SelectTyped<TS, Object> {
	public SelectObject(TS x, String campo) {
		super(x, campo);
		new SelectTyped<SelectBase<?,?,?>, Object>(x, campo);
	}
}
