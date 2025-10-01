package src.commom.utils.casewhen;

import gm.utils.comum.Lst;
import gm.utils.lambda.F0;

/*
 * para melhor desempenho utilizar
 * arrowFunctions quando o result
 * só for preparado para este fim
 * */
public class Case<T> {

	private Lst<When<T>> whens = new Lst<>();
	private F0<T> other_;

	public Case<T> when0(F0<Boolean> expression, T result) {
		return when(expression, () -> result);
	}

	public Case<T> when1(boolean expression, T result) {
		return when(() -> expression, () -> result);
	}

	public Case<T> when2(boolean expression, F0<T> result) {
		return when(() -> expression, result);
	}

	public Case<T> when(F0<Boolean> expression, F0<T> result) {
		whens.add(new When<>(expression, result));
		return this;
	}

	public Case<T> other(F0<T> result) {
		this.other_ = result;
		return this;
	}

	public Case<T> other0(T result) {
		return other(() -> result);
	}

	public T end() {
		for (When<T> when : whens) {
			if (when.e.call()) {
				return when.r.call();
			}
		}
		if (other_ == null) {
			return null;
		}
		return other_.call();
	}

}
