package src.commom.utils.comum;

import gm.utils.comum.UBoolean;
import src.commom.utils.object.Null;

public class BooleanWrapper {

	public Boolean value;
	
	public Boolean get() {
		return value;
	}
	
	public BooleanWrapper set(Boolean avalue) {
		this.value = avalue;
		return this;
	}
	
	public boolean isTrue() {
		return UBoolean.isTrue(value);
	}
	
	public boolean isFalse() {
		return UBoolean.isFalse(value);
	}
	
	public boolean isNull() {
		return Null.is(value);
	}
	
}
