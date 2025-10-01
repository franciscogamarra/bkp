package src.commom.utils.comum;

import src.commom.utils.object.Null;

public class Box<T> {
	
	private T value;
	
	public Box() {}
	
	public T get() {
		return this.value;
	}
	
	public void set(T valueP) {
		this.value = valueP;
	}
	
	public boolean isNotNull() {
		return !isNull();
	}
	
	public boolean isNull() {
		return Null.is(value);
	}
	
}