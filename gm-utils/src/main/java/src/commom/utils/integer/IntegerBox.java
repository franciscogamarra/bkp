package src.commom.utils.integer;

public class IntegerBox {
	private Integer value;

	public IntegerBox(Integer i) {
		set(i);
	}
	public Integer set(Integer i) {
		value = i;
		return value;
	}
	public Integer get() {
		return value;
	}
	public Integer inc1() {
		return inc(1);
	}
	public Integer inc(int i) {
		return set(value+i);
	}
	public Integer dec1() {
		return dec(1);
	}
	public Integer dec(int i) {
		return set(value-i);
	}
	@Override
	public String toString() {
		return ""+value;
	}

}
