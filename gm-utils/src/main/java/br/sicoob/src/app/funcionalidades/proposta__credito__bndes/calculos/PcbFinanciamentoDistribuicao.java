package br.sicoob.src.app.funcionalidades.proposta__credito__bndes.calculos;

import br.sicoob.SicoobTranspilar;
import br.sicoob.src.app.funcionalidades.licenca_ambiental.models.ItemInvestimento;
import br.sicoob.src.app.shared.utils.ChaveValor;
import gm.utils.anotacoes.IgnorarDaquiPraBaixo;
import gm.utils.comum.PrintObj;
import js.Error;
import js.array.Array;
import js.support.console;

/* Responsavel por calcular os valores dos  */
public class PcbFinanciamentoDistribuicao {
	
	public static Num percentualFinanciado = Num.ZERO;
	public Num percentualFinanciadoAjustado = Num.ZERO;
	public Num totalFinanciadoEsperado = Num.ZERO;
	public Num totalFinanciadoNaoAjustado = Num.ZERO;
	public Num totalFinanciadoAjustado = Num.ZERO;
	public Num resto = Num.ZERO;
	public Num somaTotalDosItens = Num.ZERO;
	public Num somaTotalDasQtds = Num.ZERO;
	public final Array<PcbFinanciamentoDistribuicaoItem> itens = new Array<>();
	public final Array<ChaveValor> resumo = new Array<>();;
	public boolean sucesso;
	
	public void aplica() {
		
		itens.forEach(/*PcbFinanciamentoDistribuicaoItem*/i -> i.aplica());
		
		while (resumo.lengthArray() > 0) {
			resumo.pop();
		}
		
		resumo.push(ChaveValor.build("Valor Total Financiado Esperado", totalFinanciadoEsperado.toString()));
		resumo.push(ChaveValor.build("Soma dos itens financiáveis", somaTotalDosItens.toString()));
		resumo.push(ChaveValor.build("% Financiado", percentualFinanciado.toString()));
		resumo.push(ChaveValor.build("% Financiado Ajustado", percentualFinanciadoAjustado.toString()));
		resumo.push(ChaveValor.build("Fórmula % Financiado", "Crédito Financiado / Soma dos itens financiáveis"));
		resumo.push(ChaveValor.build("Resto", resto.toString()));
		resumo.push(ChaveValor.build("Soma financiado não ajustado", totalFinanciadoNaoAjustado.toString()));
		resumo.push(ChaveValor.build("Soma financiado ajustado", totalFinanciadoAjustado.toString()));
		resumo.push(ChaveValor.build("Quantidade total de itens", "" + somaTotalDasQtds.toString()));
		
//		if (Js.inJava) {
//			resumo.print();
//		}
		
	}
	
	private static Num getValorPercentual(Num valor) {
		
		if (percentualFinanciado.igual(Num.CEM)) {
			return valor;
		}
		/* separado em partes para debug */
		Num primeiro = valor.vezes(percentualFinanciado);
//		primeiro.print();
		Num segundo = primeiro.dividido(Num.CEM);
//		segundo.print();
		Num terceiro = segundo.arredondarParaBaixo(2);
//		terceiro.print();
		return terceiro;
		
	}
	
	private PcbFinanciamentoDistribuicao(PcbCalculo calculo) {
		
		totalFinanciadoEsperado = calculo.getValorFinanciadoConformePercentual();
		Num somaDosItensFinanciaveis = calculo.getSomaDosItensFinanciaveis();
		
		if (totalFinanciadoEsperado.maior(somaDosItensFinanciaveis)) {
			totalFinanciadoEsperado = somaDosItensFinanciaveis;
			percentualFinanciado = Num.CEM;
		} else {
			
			if (calculo.getSomaDosItensNaoFinanciaveis().isZero()) {
				percentualFinanciado = calculo.getPercFinanciada();
			} else {
				percentualFinanciado = totalFinanciadoEsperado.dividido(somaDosItensFinanciaveis).vezes(Num.CEM);
			}
			
			if (percentualFinanciado.maior(Num.CEM)) {
				percentualFinanciado = Num.CEM;
			} else if (percentualFinanciado.menor(Num.ZERO)) {
				percentualFinanciado = Num.ZERO;
			}
			
		}
		
		calculo.getTipos()
			.filter(/*TipoInvestimentoWrapper*/i -> i.isFinanciavel())
			.forEach(/*TipoInvestimentoWrapper*/tipo -> 
				tipo.itens.forEach(/*ItemInvestimentoWrapper*/item -> itens.push(new PcbFinanciamentoDistribuicaoItem(item)))
			);
		
		if (itens.lengthArray() == 0) {
			return;
		}
		
		itens.forEach(/*PcbFinanciamentoDistribuicaoItem*/i -> {
			Num quantidade = i.getQuantidade();
			i.setValorTotal(i.valorUnitario.vezes(quantidade));
			i.setValorUnitarioFinanciadoNaoAjustado(getValorPercentual(i.valorUnitario));
			i.setValorTotalFinaciadoNaoAjustado(i.valorUnitarioFinanciadoNaoAjustado.vezes(quantidade));
			i.setValorUnitarioFinanciadoAjustado(i.valorUnitarioFinanciadoNaoAjustado);
			i.setValorTotalFinaciadoAjustado(i.valorTotalFinaciadoNaoAjustado);
			i.arrendondariaParaCimaNaUnidade = i.valorUnitarioFinanciadoNaoAjustado.vezes(Num.CEM).menor(i.valorUnitario.vezes(percentualFinanciado));
			i.arrendondariaParaCimaNoTotal = i.valorTotalFinaciadoNaoAjustado.vezes(Num.CEM).menor(i.valorTotal.vezes(percentualFinanciado));
			
			if (i.arrendondariaParaCimaNaUnidade) {
				i.quantoArredondouNaUnidade = i.valorUnitario.vezes(percentualFinanciado).menos(i.valorTotalFinaciadoNaoAjustado);
			}
			
			if (i.arrendondariaParaCimaNoTotal) {
				i.quantoArredondouNoTotal = i.valorTotal.vezes(percentualFinanciado).menos(i.valorTotalFinaciadoNaoAjustado);
			}
			
		});
		
		somaTotalDosItens = Num.sum(itens, /*PcbFinanciamentoDistribuicaoItem*/i -> i.valorTotal);
		
		if (somaTotalDosItens.menorQueZero()) {
			throw new Error("somaTotalDosItens < 0: " + somaTotalDosItens);
		}
		
		if (totalFinanciadoEsperado.menorQueZero()) {
			console.log("valorTotalFinanciadoEsperado", totalFinanciadoEsperado);
			console.log("somaTotalDosItens", somaTotalDosItens);
			console.log("percentualFinanciadoAjustado", percentualFinanciado);
			throw new Error("valorTotalFinanciadoEsperado < 0");
		}
		
		Num valorTotalFinanciadoAtual = Num.sum(itens, /*PcbFinanciamentoDistribuicaoItem*/i -> i.valorTotalFinaciadoAjustado);
		
		resto = totalFinanciadoEsperado.menos(valorTotalFinanciadoAtual);
		
		if (resto.menorQueZero()) {
			console.log("valorTotalFinanciadoAtual", valorTotalFinanciadoAtual);
			console.log("valorTotalFinanciadoEsperado", totalFinanciadoEsperado);
			console.log("resto", resto);
			/* nao faz sentido ter resto negativo pois todos os valores sao arredondados para baixo */
			throw new Error("resto < 0");
		}
		
		somaTotalDasQtds = Num.sum(itens, /*PcbFinanciamentoDistribuicaoItem*/i -> i.getQuantidade());
		
		if (somaTotalDasQtds.isZero()) {
			Num.sum(itens, /*PcbFinanciamentoDistribuicaoItem*/i -> i.getQuantidade());
			throw new Error("somaTotalDasQtds.isZero()");
		}
		
		if (resto.maiorQueZero()) {

//			if (resto.vezes(Num.CEM).maior(somaTotalDasQtds)) {
//				console.log("resto", resto.toString());
//				console.log("somaTotalDasQtds", somaTotalDasQtds.toString());
//				throw new Error("resto > somaTotalDasQtds");
//			}

			/* dar preferencia por distribuir primeiro para os itens que arredondariam para cima */
			Array<PcbFinanciamentoDistribuicaoItem> arrendondariaParaCimaNaUnidade = itens.filter(/*PcbFinanciamentoDistribuicaoItem*/i -> i.arrendondariaParaCimaNaUnidade);
			arrendondariaParaCimaNaUnidade.sort((a,b) -> b.quantoArredondouNaUnidade.compare(a.quantoArredondouNaUnidade));
			distribui(arrendondariaParaCimaNaUnidade);

			Array<PcbFinanciamentoDistribuicaoItem> arrendondariaParaCimaNoTotal = itens.filter(/*PcbFinanciamentoDistribuicaoItem*/i -> i.arrendondariaParaCimaNoTotal);
			arrendondariaParaCimaNoTotal.sort((a,b) -> b.quantoArredondouNoTotal.compare(a.quantoArredondouNoTotal));
			distribui(arrendondariaParaCimaNoTotal);

			while (resto.maiorQueZero()) {

				/* filtra os que sao passiveis de distribuicao */
				/* pois se a qtd de itens for maior que o resto então não é passível */
				Array<PcbFinanciamentoDistribuicaoItem> itensPassiveis = itens.filter(/*PcbFinanciamentoDistribuicaoItem*/i -> i.getQuantidade().menorOuIgual(resto.vezes(Num.CEM)));

				if (!itensPassiveis.length) {
					/* se não for possível distribuir então joga no que tem menor qtd */
					/* obs: isso fará com que a multimplicação da qtd * unitario não bata */
					/* mas é preciso para fechar o valor */
					PcbFinanciamentoDistribuicaoItem itemComMenorQuantidade = itens.reduce((/*PcbFinanciamentoDistribuicaoItem*/item, /*PcbFinanciamentoDistribuicaoItem*/atual) -> atual.getQuantidade().menor(item.getQuantidade()) ? atual : item, itens.array(0));

					itemComMenorQuantidade.valorTotalFinaciadoAjustado.maisIgual(resto);
					break;
				}
				
				distribui(itensPassiveis);

			}

		}
		
		itens.forEach(/*PcbFinanciamentoDistribuicaoItem*/i -> i.valida());
		
		totalFinanciadoAjustado = Num.sum(itens, /*PcbFinanciamentoDistribuicaoItem*/i -> i.valorTotalFinaciadoAjustado);
		totalFinanciadoNaoAjustado = Num.sum(itens, /*PcbFinanciamentoDistribuicaoItem*/i -> i.valorTotalFinaciadoNaoAjustado);

		percentualFinanciadoAjustado = percentualFinanciado;
		sucesso = totalFinanciadoAjustado.igual(totalFinanciadoEsperado);
		aplica();
		
	}
	
	public static PcbFinanciamentoDistribuicao exec(PcbCalculo calculo) {
		return new PcbFinanciamentoDistribuicao(calculo);
	}
	
	private void distribui(Array<PcbFinanciamentoDistribuicaoItem> itens) {
		
		/* ordena pela quantidade para começar distribuindo pelos maiores */
		itens.sort((/*PcbFinanciamentoDistribuicaoItem*/a, /*PcbFinanciamentoDistribuicaoItem*/b) -> b.getQuantidade().compare(a.getQuantidade()));

		while (itens.length && resto.maiorQueZero()) {
			PcbFinanciamentoDistribuicaoItem ultimo = itens.pop();
			Num x = ultimo.getQuantidade().vezes(Num.UM_CENTAVO);
			if (x.menorOuIgual(resto)) {
				if (ultimo.valorTotalFinaciadoAjustado.mais(x).maior(ultimo.valorTotal)) {
					continue;
				}
				ultimo.setValorTotalFinaciadoAjustado(ultimo.valorTotalFinaciadoAjustado.mais(x));
				ultimo.setValorUnitarioFinanciadoAjustado(ultimo.valorUnitarioFinanciadoAjustado.mais(Num.UM_CENTAVO));
				resto.menosIgual(x);
			}
		}
		
	}

	@IgnorarDaquiPraBaixo

	public static void main(String[] args) {
		SicoobTranspilar.exec(PcbFinanciamentoDistribuicao.class);
		ItemInvestimento.class.getName();
		PcbFinanciamentoDistribuicaoItem.class.getName();
	}
	
	public void print() {
		PrintObj.exec(this).print();
	}	

}