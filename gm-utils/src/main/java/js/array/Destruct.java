package js.array;

import gm.utils.comum.Lst;

public class Destruct {
	
	private final Lst<?> lst;

	public Destruct(Array<?> array) {
		lst = array.list.copy();
	}
	
	@SuppressWarnings("unchecked")
	public <T> T get() {
		return (T) lst.remove(0);
	}
	
	public static Destruct exec(Array<?> array) {
		return new Destruct(array);
	}

	
}
