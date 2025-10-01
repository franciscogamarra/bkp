package br.sicoob.src.app.funcionalidades.proposta__credito__bndes.calculos.tests;

import br.sicoob.SicoobTranspilar;
import br.sicoob.src.app.funcionalidades.licenca_ambiental.models.TipoInvestimento;
import br.sicoob.src.app.funcionalidades.proposta__credito__bndes.comps.AbaItensInvestimentoComponent;
import gm.utils.anotacoes.IgnorarDaquiPraBaixo;
import js.Js;
import js.array.Array;
import js.html.Element;

public class Acao006SelecionarTipoDeInvestimentoNoUltimoItemAdicionado extends PcbAcao {

	private TipoInvestimento tipo;

	public Acao006SelecionarTipoDeInvestimentoNoUltimoItemAdicionado(TipoInvestimento tipo) {
		super("Acao006SelecionarTipoDeInvestimentoNoUltimoItemAdicionado: " + tipo.id);
		this.tipo = tipo;
	}

	@Override
	public void acao() {
		int count = AbaItensInvestimentoComponent.instance.tipoInvestimentos.lengthArray();
		AbaItensInvestimentoComponent.instance.tipoInvestimentos.arraySet(count-1, tipo);
		Array<Element> selects = Array.from(Js.document.getElementsByClassName("select-tipoInvestimentos"));
		Element element = selects.array(selects.lengthArray()-1);
		element.click();
	}
	
	@IgnorarDaquiPraBaixo

	public static void main(String[] args) {
		SicoobTranspilar.exec(Acao006SelecionarTipoDeInvestimentoNoUltimoItemAdicionado.class);
	}

}
