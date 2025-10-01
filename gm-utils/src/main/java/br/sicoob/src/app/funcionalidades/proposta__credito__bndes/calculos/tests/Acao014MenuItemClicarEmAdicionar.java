package br.sicoob.src.app.funcionalidades.proposta__credito__bndes.calculos.tests;

import br.sicoob.SicoobTranspilar;
import br.sicoob.src.app.funcionalidades.licenca_ambiental.models.TipoInvestimento;
import br.sicoob.src.app.funcionalidades.proposta__credito__bndes.calculos.TipoInvestimentoWrapper;
import gm.utils.anotacoes.IgnorarDaquiPraBaixo;
import js.Js;

public class Acao014MenuItemClicarEmAdicionar extends PcbAcao {

	private final TipoInvestimento tipo;

	public Acao014MenuItemClicarEmAdicionar(TipoInvestimento tipo) {
		super("Acao014MenuItemClicarEmAdicionar");
		this.tipo = tipo;
	}
	
	private String getIdBotao() {

		TipoInvestimentoWrapper tw = TipoInvestimentoWrapper.get(tipo);
		if (tw.isValores()) {
			return "adiciona-itens-investimento-adicionar-itens-investimento";
		} else if (tw.isEquipamento()) {
			return "adiciona-equipamentos-botao-adicionar-equipamento";
		} else if (tw.isQuantidades()) {
			return "adiciona-itens-inverstimento-com-quantidade-adicionar-item-investimento";
		} else {
			throw new Error("NaoImplementadoException");
		}
		
	}

	@Override
	public void acao() {
		Js.document.getElementById(getIdBotao()).click();
	}
	
	@IgnorarDaquiPraBaixo

	public static void main(String[] args) {
		SicoobTranspilar.exec(Acao014MenuItemClicarEmAdicionar.class);
	}
	
}
