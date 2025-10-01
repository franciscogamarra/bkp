package gm.utils.jpa.select;

public class SelectEnum<TS extends SelectBase<?,?,?>, T extends Enum<T>> extends SelectTyped<TS, T> {
	public SelectEnum(TS x, String campo) {
		super(x, campo);
		new SelectTyped<SelectBase<?,?,?>, T>(x, campo);
	}
}
