package br.sicoob.src.app.funcionalidades.proposta__credito__bndes.calculos.tests;

import js.Js;

public class Acao018ExpandirAbaOperacoes extends PcbAcao {

	public Acao018ExpandirAbaOperacoes() {
		super("Acao005ExpandirAbaItensDeInvestimento");
	}

	@Override
	public void acao() {
		Js.document.getElementById("accordion-group-aba-operacoes").click();
	}
	
	@Override
	public int tempoDeExecucao() {
		return 1500;
	}

}
