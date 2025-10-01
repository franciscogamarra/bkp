package br.sicoob.src.app.funcionalidades.proposta__credito__bndes.calculos.tests;

import br.sicoob.SicoobTranspilar;
import br.sicoob.src.app.funcionalidades.proposta__credito__bndes.comps.AbaOperacaoComponent;
import gm.utils.anotacoes.IgnorarDaquiPraBaixo;

public class Acao020AbrirCalculadora extends PcbAcao {

	public Acao020AbrirCalculadora() {
		super("Acao020AbrirCalculadora");
	}
	
	@Override
	public void acao() {
		AbaOperacaoComponent.instance.abreActionBarCalculadoraOperacoes();
	}
	
	@IgnorarDaquiPraBaixo

	public static void main(String[] args) {
		SicoobTranspilar.exec(Acao020AbrirCalculadora.class);
	}
	
}