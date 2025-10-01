package br.sicoob.src.app.funcionalidades.proposta__credito__bndes.calculos.tests;

import js.Js;

public class Acao005ExpandirAbaItensDeInvestimento extends PcbAcao {

	public Acao005ExpandirAbaItensDeInvestimento() {
		super("Acao005ExpandirAbaItensDeInvestimento");
	}

	@Override
	public void acao() {
		Js.document.getElementById("accordion-group-aba-itens-investimento").click();
	}
	
	@Override
	public int tempoDeExecucao() {
		return 1500;
	}

}
