package br.sicoob.src.app.funcionalidades.proposta__credito__bndes.calculos;

import br.sicoob.SicoobTranspilar;
import gm.utils.anotacoes.IgnorarDaquiPraBaixo;
import js.Js;

public class Temp {

//	private Array<TipoInvestimento> tipoInvestimentos = new Array<>();
	
//	private boolean isTipoInvestimentoValid(TipoInvestimento tipo) {
//		return TipoInvestimentoWrapper.get(tipo).isValid();
//	}
//	
//	private boolean isTipoInvestimentoFinanciavel(TipoInvestimento tipo) {
//		return TipoInvestimentoWrapper.get(tipo).isFinanciavel();
//	}
	
//	private boolean possuiItensDeInvestimento(TipoInvestimento tipo) {
//		return TipoInvestimentoWrapper.get(tipo).possuiItens();
//	}
	
//	private Array<ItemInvestimento> getItensInvestimento(boolean filtrar, boolean financiaveis) {
//		
//		Array<ItemInvestimento> itens = new Array<>();
//		
//		if (Utils.isNull(tipoInvestimentos)) {
//			return itens;
//		}
//		
//		Array<TipoInvestimentoWrapper> tipos = tipoInvestimentos
//				.map(/*TipoInvestimento*/tipo -> TipoInvestimentoWrapper.get(tipo))
//				.filter(/*TipoInvestimentoWrapper*/tipo -> tipo.possuiItens());
//		
//		if (filtrar) {
//			tipos = tipos.filter(/*TipoInvestimentoWrapper*/tipo -> tipo.isFinanciavel() == financiaveis);
//		}
//		
//		tipos.forEach(/*TipoInvestimentoWrapper*/tipo -> tipo.itens.forEach(/*ItemInvestimentoWrapper*/item -> itens.push(item.value)));
//		
//		return itens;
//		
//	}
	
//	private Array<ItemInvestimento> getItensInvestimentoFinanciaveis() {
//		return getItensInvestimento(true, true);
//	}
//
//	private Array<ItemInvestimento> getItensInvestimentoNaoFinanciaveis() {
//		return getItensInvestimento(true, false);
//	}
	
//	private Double getValorCreditoFinanciavel() {
//		return getItensInvestimentoFinanciaveis().reduce((/*Double*/total, /*ItemInvestimento*/item) -> total + item.valorTotalItem, 0.0);
//	}
//	
//	private boolean getTemItemNaoFinanciavel() {
//		return getItensInvestimentoNaoFinanciaveis().lengthArray() > 0;
//	}
//	
//	private Double somaAtributo(TipoInvestimento tipo, F1<ItemInvestimento, Number> get) {
//		
//		if (possuiItensDeInvestimento(tipo)) {
//			return tipo.itensInvestimento.reduce((/*Double*/total, /*ItemInvestimento*/item) -> {
//				Double value = Js.Number(get.call(item));
//				if (Js.isNaN(value)) {
//					return total;
//				} else {
//					return total + value;
//				}
//			}, 0.0);
//		} else {
//			return 0.0;
//		}
//		
//	}
	
	public int getTotalizadorQuantidade(int index) {
//		return TipoInvestimentoWrapper.get(tipoInvestimentos.array(index)).getSumQuantidade();
		return 0;
	}

	public static Double toDouble(Number o) {
		Double value = Js.Number(o);
		if (Js.isNaN(value)) {
			return 0.0;
		} else {
			return value;
		}
	}	
	@IgnorarDaquiPraBaixo
	
	public static void main(String[] args) {
		SicoobTranspilar.exec(Temp.class);
	}
	
}