package gm.utils.jpa.select;

import gm.utils.abstrato.IdObject;
import gm.utils.comum.IWrapper;
import gm.utils.comum.IWrapperInteger;
import src.commom.utils.integer.IntegerParse;

public class SelectInteger<TS extends SelectBase<?,?,?>> extends SelectTypedLogical<TS, Integer> {

	public SelectInteger(TS x, String campo) {
		super(x, campo);
	}

	public TS isNullOrZero() {
		isNull();
		c().or();
		eq(0);
		return ts;
	}

	public Integer sum() {
		return IntegerParse.toInt( c().sum(getCampo()) );
	}

	public Integer max() {
		return IntegerParse.toInt( c().max(getCampo()) );
	}

	public Integer min() {
		return IntegerParse.toInt( c().min(getCampo()) );
	}

	public TS maior(IWrapperInteger value) {
		return maior(value.unwrapperInteger());
	}

	public TS menor(IWrapperInteger value) {
		return menor(value.unwrapperInteger());
	}

	public TS maiorOuIgual(IWrapperInteger value) {
		return maiorOuIgual(value.unwrapperInteger());
	}

	public TS menorOuIgual(IWrapperInteger value) {
		return menorOuIgual(value.unwrapperInteger());
	}

	public TS entre(IWrapperInteger a, Integer b) {
		return entre(a.unwrapperInteger(), b);
	}

	public TS entre(IWrapperInteger a, IWrapper<Integer> b) {
		return entre(a.unwrapperInteger(), b);
	}

	public TS entre(Integer a, IWrapperInteger b) {
		return entre(a, b.unwrapperInteger());
	}

	public TS entre(IWrapper<Integer> a, IWrapperInteger b) {
		return entre(a, b.unwrapperInteger());
	}

	public TS entre(IWrapperInteger a, IWrapperInteger b) {
		return entre(a.unwrapperInteger(), b.unwrapperInteger());
	}

	public TS eq(IWrapperInteger value) {
		return eq(value.unwrapperInteger());
	}

	public TS ne(IWrapperInteger value) {
		return ne(value.unwrapperInteger());
	}

	public TS eq(IdObject value) {
		if (value == null) {
			return isNull();
		}
		return eq(value.getId());
	}

	public TS ne(IdObject value) {
		if (value == null) {
			return isNotNull();
		}
		return ne(value.getId());
	}
	
}
