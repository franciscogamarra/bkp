package br.sicoob.src.app.funcionalidades.proposta__credito__bndes.calculos.tests;

import gm.utils.lambda.F0;
import src.commom.utils.object.Equals;

public class Acao900AssertEquals extends PcbAcao {

	private Object expected;
	private F0<Object> get;

	public Acao900AssertEquals(Object expected, F0<Object> get) {
		super("Acao900AssertEquals");
		this.expected = expected;
		this.get = get;
	}

	@Override
	public void acao() {
		Object value = get.call();
		if (!Equals.is(expected, value)) {
			throw new Error("expected: " + expected + " / value " + value);
		}
	}
	
}
