package br.sicoob.src.app.funcionalidades.proposta__credito__bndes.calculos.tests;

import br.sicoob.src.app.funcionalidades.proposta__credito__bndes.calculos.tests.proxies.AdicionaItensForm;

public class Acao013MenuItemInserirInformacaoAdicional extends PcbAcao {

	private String s;

	public Acao013MenuItemInserirInformacaoAdicional(String s) {
		super("Acao013MenuItemInserirInformacaoAdicional: " + s);
		this.s = s;
	}

	@Override
	public void acao() {
		AdicionaItensForm.setDescricaoInformacaoAdicional(s);
	}
	
}
