package br.sicoob.src.app.funcionalidades.proposta__credito__bndes.calculos.tests;

import br.sicoob.SicoobTranspilar;
import br.sicoob.src.app.funcionalidades.proposta__credito__bndes.calculos.tests.proxies.PropostaCreditoBndesForm;
import br.sicoob.src.app.funcionalidades.proposta__credito__bndes.models.LinhaBndes;
import gm.utils.anotacoes.IgnorarDaquiPraBaixo;

public class Acao002SelecionarLinhaBndes extends PcbAcao {

	private LinhaBndes linhaBndes;

	public Acao002SelecionarLinhaBndes(LinhaBndes linhaBndes) {
		super("Acao002SelecionarLinhaBndes " + linhaBndes.id);
		this.linhaBndes = linhaBndes;
	}

	@Override
	public void acao() {
		PropostaCreditoBndesForm.setLinhaBndes(linhaBndes);
	}
	
	@IgnorarDaquiPraBaixo

	public static void main(String[] args) {
		SicoobTranspilar.exec(Acao002SelecionarLinhaBndes.class);
	}

}
