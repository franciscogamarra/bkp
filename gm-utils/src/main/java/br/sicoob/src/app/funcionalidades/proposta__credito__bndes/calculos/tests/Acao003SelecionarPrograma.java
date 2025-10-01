package br.sicoob.src.app.funcionalidades.proposta__credito__bndes.calculos.tests;

import br.sicoob.SicoobTranspilar;
import br.sicoob.src.app.funcionalidades.proposta__credito__bndes.calculos.tests.proxies.PropostaCreditoBndesForm;
import br.sicoob.src.app.funcionalidades.proposta__credito__bndes.models.Programa;
import gm.utils.anotacoes.IgnorarDaquiPraBaixo;

public class Acao003SelecionarPrograma extends PcbAcao {

	private Programa programa;

	public Acao003SelecionarPrograma(Programa programa) {
		super("Acao003SelecionarPrograma " + programa.id);
		this.programa = programa;
	}

	@Override
	public void acao() {
		PropostaCreditoBndesForm.setPrograma(programa);
	}
	
	@IgnorarDaquiPraBaixo

	public static void main(String[] args) {
		SicoobTranspilar.exec(Acao003SelecionarPrograma.class);
	}

}
