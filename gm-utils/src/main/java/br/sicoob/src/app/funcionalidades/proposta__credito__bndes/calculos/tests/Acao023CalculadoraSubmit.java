package br.sicoob.src.app.funcionalidades.proposta__credito__bndes.calculos.tests;

import br.sicoob.SicoobTranspilar;
import br.sicoob.src.app.funcionalidades.proposta__credito__bndes.comps.CalculadoraOperacoesComponent;
import gm.utils.anotacoes.IgnorarDaquiPraBaixo;

public class Acao023CalculadoraSubmit extends PcbAcao {

	public Acao023CalculadoraSubmit() {
		super("Acao023CalculadoraSubmit");
	}
	
	@Override
	public void acao() {
		CalculadoraOperacoesComponent.instance.onSubmit();
	}
	
	@IgnorarDaquiPraBaixo

	public static void main(String[] args) {
		SicoobTranspilar.exec(Acao023CalculadoraSubmit.class);
	}
	
}