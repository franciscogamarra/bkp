package br.sicoob.src.app.funcionalidades.proposta__credito__bndes.calculos.tests;

import br.sicoob.SicoobTranspilar;
import gm.utils.anotacoes.IgnorarDaquiPraBaixo;
import js.Js;

public class Acao004AdicionarTipoDeInvestimento extends PcbAcao {

	public Acao004AdicionarTipoDeInvestimento() {
		super("Acao004AdicionarItemDeInvestimento");
	}

	@Override
	public void acao() {
		Js.document.getElementById("button-adicionarTipoDeItem").click();
	}
	
	@IgnorarDaquiPraBaixo

	public static void main(String[] args) {
		SicoobTranspilar.exec(Acao004AdicionarTipoDeInvestimento.class);
	}

}
