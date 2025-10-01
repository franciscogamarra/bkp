package br.sicoob.src.app.shared.forms;

import gm.utils.lambda.F0;
import js.Js;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class InputInt extends FormInput<Integer> {
	
	private F0<Integer> minFunc;
	private F0<Integer> maxFunc;
	
	public InputInt(String name, boolean required) {
		super(name, required);
	}
	
	public InputInt set(Integer o) {

		if (isNull_(o)) {
			setPrivate(null);
			return this;
		}
		
		int min = getMin();
		
		if (o < min) {
			setPrivate(min);
			return this;
		}

		int max = getMax();
		
		if (o > max) {
			setPrivate(max);
			return this;
		}
		
		setPrivate(o);
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
		return getInt(minFunc, 0);
	}

	public int getMax() {
		return getInt(maxFunc, 999999);
	}

	@Override
	protected Integer parseT(String s) {
		return Js.parseInt(s);
	}
	
}