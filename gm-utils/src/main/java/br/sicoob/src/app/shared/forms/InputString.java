package br.sicoob.src.app.shared.forms;

import br.sicoob.src.app.shared.utils.Str;
import gm.utils.lambda.F0;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class InputString extends FormInput<String> {
	
	private F0<Integer> minLen;
	private F0<Integer> maxLen;
	
	public InputString(String name, boolean required) {
		super(name, required);
	}
	
	public InputString set(String o) {

		if (isNull_(o)) {
			setPrivate("");
			return this;
		}
		
		Str s = new Str().set(o);
		s.truncate(getMax());
		setPrivate(s.get());
		return this;
		
	}
	
	private int getInt(F0<Integer> func, int def) {

		if (isNull_(func)) {
			return def;
		}
		
		Integer i = func.call();
		
		if (isNull_(i)) {
			return def;
		}
		
		return i;
		
	}
	
	public int getMin() {
		return getInt(minLen, 0);
	}

	public int getMax() {
		return getInt(maxLen, 50);
	}

	@Override
	protected String parseT(String s) {
		return s;
	}
	
}