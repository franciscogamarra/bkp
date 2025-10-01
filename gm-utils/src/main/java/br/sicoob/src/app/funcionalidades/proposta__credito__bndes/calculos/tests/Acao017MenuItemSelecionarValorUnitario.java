package br.sicoob.src.app.funcionalidades.proposta__credito__bndes.calculos.tests;

import br.sicoob.src.app.funcionalidades.proposta__credito__bndes.calculos.tests.proxies.AdicionaItensForm;

public class Acao017MenuItemSelecionarValorUnitario extends PcbAcao {

	private double valor;

	public Acao017MenuItemSelecionarValorUnitario(double valor) {
		super("Acao012MenuItemSelecionarValorUnitario: " + valor);
		this.valor = valor;
	}

	@Override
	public void acao() {
		AdicionaItensForm.setValorUnitarioItem(valor);
	}
	
}
