package br.sicoob.src.app.funcionalidades.proposta__credito__bndes.calculos;

import br.sicoob.SicoobTranspilar;
import br.sicoob.src.app.funcionalidades.licenca_ambiental.models.ItemInvestimento;
import br.sicoob.src.app.funcionalidades.licenca_ambiental.models.TipoInvestimento;
import br.sicoob.src.app.funcionalidades.proposta__credito__bndes.comps.AbaItensInvestimentoComponent;
import br.sicoob.src.app.funcionalidades.proposta__credito__bndes.comps.PropostaCreditoBndesComponent;
import br.sicoob.src.app.shared.forms.StateForm;
import br.sicoob.src.app.shared.forms.StateFormControl;
import gm.languages.ts.angular.forms.FormGroup;
import gm.utils.anotacoes.IgnorarDaquiPraBaixo;
import gm.utils.comum.PrintObj;
import gm.utils.lambda.F1;
import gm.utils.lambda.P0;
import js.Js;
import js.array.Array;
import js.outros.Date;
import js.support.JSON;
import js.support.console;
import src.commom.utils.object.Null;

/* TODO verificar o valor máximo de configuração que o programa permite um % maximo de financiamento */

public class PcbCalculo {
	
	public static final int TEMPO_REFRESH = 200;
	
	/* variaveis de controle do formulario */
	private FormGroup form;
	private StateForm stateForm;
	
	/* inputs */
	public StateFormControl itensInvestimento;
	public StateFormControl percentualRecursoProprio;
	public StateFormControl percentualFinanciado;
	
	/* outputs */
	private StateFormControl percTotalInvestimentoOut;
	public StateFormControl valorTotalInvestimentoOut;
	public StateFormControl valorFinanciadoOut;
	public StateFormControl valorRecursosPropriosOut;
	
//	private StateFormControl errosForm;
	
	/* wrappers */
	public ProgramaWrapper programa;
	private Array<TipoInvestimentoWrapper> tipos;
	public static P0 aoCalcular;
	
	/* variáveis de resultado */
	public final Array<Erro> erros = new Array<>();
	public Array<PcbCols> totais;
	public PcbCols total;
	public PcbCols titles = new PcbCols();
	public PcbFinanciamentoDistribuicao result;
	
	public Num percentualMinimoDeRecursosPropriosAceitavel;
	public boolean percentualMinimoDeRecursosPropriosAceitavelInconsistente;
	
	/* variavel de controle externo que diz se um recalculo deve ou não ocorrer após a ação de salvar */
	public static boolean calcularErrosAposSalvar = true;
	
	/* indica que obrigatoriamente o calculo será disparado na proxima leitura de dados */
	public static boolean calcularNaProxima = true;
	
	/* variáveis utilizadas para evitar recalculos desnecessários */
	private static Num ultimoPercentualDeFinanciamentoCalulado;
	private String tiposInvestimentoString;

	public static final PcbCalculo instance = new PcbCalculo();
	
	private boolean ligado = true;
	private long ultimaBusca = 0;

	public static PcbCalculo get() {
		
		long now = new Date().getTime();
		
		if (now - instance.ultimaBusca < 50) {
			return instance;
		}
		
		instance.ultimaBusca = now;
		
		/* indica que todas as novas instancias de num virão por padrão com arredondamento para baixo */
		Num.arredondarParaBaixoDefault();
		
		boolean calcularPorPadrao = false;
		
		if (instance.form != PropostaCreditoBndesComponent.instance.formCadastroProposta) {
			instance.form = PropostaCreditoBndesComponent.instance.formCadastroProposta;
			instance.tiposInvestimentoString = null;
			instance.clearErros();
			calcularPorPadrao = true;
		}
		
		if (!Null.is(instance.form)) {
			instance.calcula(calcularPorPadrao);
		}
		
		return instance;
		
	}
	
	private void clearErros() {
		while (erros.length ) {
			erros.pop();
		}
	}

	private PcbCalculo() {
		stateForm = new StateForm(() -> this.form);
		itensInvestimento = stateForm.build("itensInvestimento");
		percentualFinanciado = stateForm.build("percFinanciada");
		percentualRecursoProprio = stateForm.build("percRecursoProprio");
		valorFinanciadoOut = stateForm.build("valorFinanciado");
		valorRecursosPropriosOut = stateForm.build("valorRecursosProprios");
		percTotalInvestimentoOut = stateForm.build("percTotalInvestimento");
		valorTotalInvestimentoOut = stateForm.build("valorTotalInvestimento");
		programa = new ProgramaWrapper(stateForm.build("programa"));
//		errosForm = stateForm.build("erros");
	}
	
	public void desliga() {
		ligado = false;
		get();
	}

	public void liga() {
		ligado = true;
		Js.setTimeout(() -> get(), 1000);
	}

	public Num getValorTotalInvestimento() {
		if (this.programa.exigeInvestimentos()) {
			return sum(tipos, /*TipoInvestimentoWrapper*/ item -> item.getValorTotal());
		} else {
			return Num.fromState(2, valorTotalInvestimentoOut);
		}
	}
	
	public Num getSomaDosItensFinanciaveis() {
		return sum(tipos.filter(i -> i.isFinanciavel()), /*TipoInvestimentoWrapper*/ item -> item.getValorTotal());
	}

	public Num getSomaDosItensNaoFinanciaveis() {
		return sum(tipos.filter(i -> !i.isFinanciavel()), /*TipoInvestimentoWrapper*/ item -> item.getValorTotal());
	}
	
	/* chamado na aba-operacao.components */
	@SuppressWarnings("unchecked")
	public Array<ItemInvestimento> getItensInvestimento() {
		Array<ItemInvestimento> itens = (Array<ItemInvestimento>) itensInvestimento.get();
		if (Null.is(itens)) {
			itens = new Array<>();
			this.itensInvestimento.set(itens);// nao chamar set pois nao quero calcular
		}
		return itens;
	}
	
	public Num getValorFinanciadoForm() {
		return Num.fromState(2, valorFinanciadoOut);
	}

	public Num getPercFinanciada() {
		return Num.fromState(2, percentualFinanciado);
	}

	public Num getPercRecursoProprio() {
		return Num.fromState(2, percentualRecursoProprio);
	}

	private Array<TipoInvestimento> getTiposInvestimento() {
		
		if (Null.is(AbaItensInvestimentoComponent.instance)) {
			return new Array<>();
		} else if (Null.is(AbaItensInvestimentoComponent.instance.tipoInvestimentos)) {
			return new Array<>();
		} else {
			return AbaItensInvestimentoComponent.instance.tipoInvestimentos;
		}
		
	}

	public void setItensInvestimento(Array<ItemInvestimento> value) {
		
		if (Null.is(value)) {
			value = new Array<>();
		}
		
		this.itensInvestimento.set(value);
		
		if (getPercFinanciada().isZero() && getPercRecursoProprio().isZero()) {
			Js.setTimeout(() -> atualizaPercentuaisBaseadoNosTotais(), TEMPO_REFRESH);
		} else {
			calcula(false);
		}
		
	}

	public void setPercFinanciada(Num value) {
		calcula(setPercFinanciadaSemCalculo(value));
	}
	
	/* chamado do retorno da calculadora */
	public boolean setPercFinanciadaSemCalculo(Num value) {
		return this.percentualFinanciado.setMoney(value.toDouble());
	}

	public void setPercRecursoProprioForm(Num value) {
		this.percentualRecursoProprio.setMoney(value.toDouble());
		atualizaPercentuaisBaseadoNoNaoFinanciado();
	}

	private Erro addErro(String msg) {
		Erro o = new Erro(msg);
		erros.push(o);
		return o;
	}

	public void calcular() {
//		console.log("vai recalcular obrigatoriamente");
		calcula(true);
	}
	
	private boolean calcula(boolean recalcular) {
		boolean result = calculaPrivate(recalcular);
		if (result) {
			atualizaFormulario();
		}
//		errosForm.set(erros);
		return result;
	}
	
	private void atualizaFormulario() {
		
		Num totalInvestimento = getValorTotalInvestimento();
		valorTotalInvestimentoOut.setNum(totalInvestimento);
		
		Num valorFinanciado = getValorFinanciadoConformePercentual();
		valorFinanciadoOut.setNum(valorFinanciado);
		
		valorRecursosPropriosOut.setNum(totalInvestimento.menos(valorFinanciado));
		
		PropostaCreditoBndesComponent.instance.refresh();
	
//		console.log("calculou e atualizou formulario " + getValorFinanciadoCalculado());

		if (!Null.is(aoCalcular)) {
			aoCalcular.call();
		}
		
	}
	
	public Num getValorFinanciadoConformePercentual() {
		return percentualFinanciado.getMoney().vezes(getValorTotalInvestimento()).dividido(Num.CEM).arredondarParaBaixo(2);
	}

	private boolean calculaPrivate(boolean recalcular) {
		
		if (!ligado) {
			return false;
		}
		
//		Print.ln("calcula " + recalcular);
		
		/* precisa chamar para atualizar */
		if (programa.mudouDesdeOUltimoCalculo()) {
			recalcular = true;
		}
		
		/* verifica se o percentual do financiamento mudou desde o ultimo calculo */
		if (!percentualFinanciado.getMoney().igual(ultimoPercentualDeFinanciamentoCalulado)) {
//			console.log("mudou percewntula finaciado de " + ultimoPercentualDeFinanciamentoCalulado + " para " + percentualFinanciado.getMoney());
			ultimoPercentualDeFinanciamentoCalulado = percentualFinanciado.getMoney();
			recalcular = true;
		}
		
		if (calcularNaProxima) {
			recalcular = true;
			calcularNaProxima = false;
		}
		
		if (programa.exigeInvestimentos()) {
			return calculaComInvestimentos(recalcular);
		} else {
			return calculaSemInvestimentos(recalcular);
		}
		
	}
	
	private boolean calculaSemInvestimentos(boolean recalcular) {
		
		tipos = null;
		TipoInvestimentoWrapper.clearInstances();
		
		if (!recalcular) {
			return false;
		}
		
//		console.log("PcbCalculo.calculaSemInvestimentos foi disparado");
	
		clearErros();
		
//		setValorFinanciadoSemCalculo(getValorFinanciado())
//		setValorFinanciado(getValorFinanciado());
//		valorFinanciado.setMoney(valorFinanciado);
		
		return true;
		
	}
	
	private boolean atualizarTipos() {

		Array<TipoInvestimento> tiposInvestimento = getTiposInvestimento();
		
		String s = JSON.stringify(tiposInvestimento.map(i -> i.id + ":" + JSON.stringify(i.itensInvestimento)));

		if (s == tiposInvestimentoString) {
			return false;
		}
		
//		console.log(s);
//		console.log(tiposInvestimentoString);
		
		tiposInvestimentoString = s;
		
		tipos = new Array<>();
		TipoInvestimentoWrapper.clearInstances();
		
		tiposInvestimento.forEach(/* TipoInvestimento */ tipoInvestimento -> {
			if (Null.is(tipoInvestimento)) {
				return;
			}
			TipoInvestimentoWrapper o = TipoInvestimentoWrapper.get(tipoInvestimento);
			o.setIndex(tipos.lengthArray());
			tipos.push(o);
		});
		
		return true;
		
	}
	
	
	private boolean calculaComInvestimentos(boolean recalcular) {
		
		recalcular = atualizarTipos() || recalcular;
		
		if (!recalcular) {
			return false;
		}

		clearErros();
		
		if (!tipos.length) {
			return true;
		}
		
		Num totalInvestimento = getValorTotalInvestimento();
		
		if (totalInvestimento.isZero()) {
			zeraTudo();
			return false;
		}

		console.log("PcbCalculo.calculaComInvestimentos foi disparado");

		result = PcbFinanciamentoDistribuicao.exec(this);
		
		titles.col1 = "Resumo";
		titles.col2 = "Tipos";
		titles.col3 = "Quantidade";
		titles.col4 = "Valor Total";
		titles.col5 = "Valor Total Financiado";
		titles.col9 = "% Orç";
		
		totais = new Array<>();
		
		Array<TipoInvestimentoWrapper> tiposFinanciaveis = getTiposFinanciaveis();
		PcbCols colsFinanciaveis = newCols("Itens Financiáveis:", tiposFinanciaveis);
		totais.push(colsFinanciaveis);

		Array<TipoInvestimentoWrapper> tiposNaoFinanciaveis = getTiposNaoFinanciaveis();		
		PcbCols colsNaoFinanciaveis = newCols("Itens não Financiáveis:", tiposNaoFinanciaveis);
		totais.push(colsNaoFinanciaveis);

		Num somaDoValorTotalDeItensFinanciados = getSumValorTotalFinanciado(tiposFinanciaveis);
		Num somaDoValorTotalDeItensNaoFinanciados = getSumValorTotal(tiposNaoFinanciaveis);
		
		percentualMinimoDeRecursosPropriosAceitavel = Num.novo(2);
		
		if (somaDoValorTotalDeItensFinanciados.maiorQueZero()) {
			colsFinanciaveis.col9 = somaDoValorTotalDeItensFinanciados.vezes(Num.CEM).dividido(totalInvestimento).arredondarParaBaixo(2).formatPer();
			if (somaDoValorTotalDeItensNaoFinanciados.maiorQueZero()) {
				percentualMinimoDeRecursosPropriosAceitavel = somaDoValorTotalDeItensNaoFinanciados.vezes(Num.CEM).dividido(totalInvestimento).arredondarParaCima(2);
			}
		} else if (somaDoValorTotalDeItensNaoFinanciados.maiorQueZero()) {
			percentualMinimoDeRecursosPropriosAceitavel = Num.CEM;
		}
		
		Num recursosProprios = getResursosProprios();
		
//		Num recursosProprios = getValorRecursosPropriosForm();
		
		if (recursosProprios.menor(somaDoValorTotalDeItensNaoFinanciados)) {
			addErro("O valor de recursos próprios não pode ser inferior à soma de itens não financiáveis!");
		}
		
		if (percentualMinimoDeRecursosPropriosAceitavel.maiorQueZero()) {
			String per = percentualMinimoDeRecursosPropriosAceitavel.formatPer();
			colsNaoFinanciaveis.col9 = per;
			Num percentualDeRecursosPropriosInformadosNoCampo = getPercRecursoProprio();
			if (percentualDeRecursosPropriosInformadosNoCampo.menor(percentualMinimoDeRecursosPropriosAceitavel)) {
				percentualMinimoDeRecursosPropriosAceitavelInconsistente = true;
				addErro("O Percentual de Recursos Próprios deve comportar no mínimo o valor de Itens não financiáveis informados nos Itens de Investimento.")
				.add("- A soma do valor total de itens de investimento é " + getSumValorTotal(tipos).formatRs() + ";")
				.add("- A soma do valor total de itens de investimento que possuem a característica de Não Financiáveis é " + somaDoValorTotalDeItensNaoFinanciados.formatRs() + ";")
				.add("- Isso corresponde a " + per + ";")
				.add("- O percentual informado foi " + percentualDeRecursosPropriosInformadosNoCampo.formatPer() + ";")
				.add("Para solucionar o problema, configure o percentual de recursos próprios para "+per+" ou diminua o valor de Itens não Financiáveis na aba Itens de Investimento;")
				;
			} else {
				percentualMinimoDeRecursosPropriosAceitavelInconsistente = false;
			}
		} else {
			percentualMinimoDeRecursosPropriosAceitavelInconsistente = false;
		}
		
		Num totalNaoInformado = totalInvestimento.menos(somaDoValorTotalDeItensFinanciados).menos(somaDoValorTotalDeItensNaoFinanciados);
		
		titles.col6 = "Não classificado";
		colsNaoFinanciaveis.col6 = totalNaoInformado.formatRs();

		total = newCols("Totais:", getTipos());
		
//		if (result.somaTotalDasQtds.maiorQueZero()) {
//			
//			if (getValorFinanciadoForm().maior(totalInvestimento)) {
//				addErro("O valor total financiado não deve ser maior que o valor total do investimento!");
//			}
//			
//			if (getValorRecursosPropriosForm().menorQueZero()) {
//				addErro("O valor de recursos próprios não deve ser negativo!");
//			}
//			
//		}
		
		tipos.forEach(i -> {
			if (!i.isDescricaoPreenchida()) {
				addErro("Na aba Itens de Investimento o tipo " + i.getLabel() + " não possui uma descrição prenchida!");
				return;
			}
			if (!i.possuiItens()) {
				addErro("Na aba Itens de Investimento o tipo " + i.getLabel() + " foi informado porém não possui itens!");
				return;
			}
		});
		
//		valorRecursosProprios.desbloqueia();
//		valorRecursosProprios.setNum(recursosProprios);
//		valorRecursosProprios.bloqueia();
		
		return true;

	}

	public Array<TipoInvestimentoWrapper> getTipos() {
		return tipos;
	}

	public Array<TipoInvestimentoWrapper> getTiposFinanciaveis() {
		return tipos.filter(i -> i.isFinanciavel());
	}

	public Array<TipoInvestimentoWrapper> getTiposNaoFinanciaveis() {
		return tipos.filter(i -> !i.isFinanciavel());
	}
	
	private Num getResursosProprios() {
		return sum(getTipos(), /*TipoInvestimentoWrapper*/ item -> item.getValorRecursosProprios());
	}
	
	public Num getValorFinanciadoCalculado() {
		return sum(getTipos(), item -> item.getValorTotalFinanciado());
	}

	public Num getPercentualFinanciamento() {
		
		Num percRecursoProprio = getPercRecursoProprio();
		
		if (percRecursoProprio.isZero()) {
			return Num.CEM;
		}
		
		if (percRecursoProprio.igual(Num.CEM)) {
			return Num.ZERO;
		}
		
		return PcbFinanciamentoDistribuicao.percentualFinanciado;

	}
	
	private Num sum(Array<TipoInvestimentoWrapper> list, F1<TipoInvestimentoWrapper, Num> get) {
		if (Null.is(list)) {
			return Num.ZERO;
		}
		return list.reduce((/*Num*/ soma, /*TipoInvestimentoWrapper*/ item) -> soma.mais(get.call(item)), Num.ZERO);
	}
	
	private Num getSumQuantidades(Array<TipoInvestimentoWrapper> list) {
		return sum(list, /*TipoInvestimentoWrapper*/ item -> item.getQuantidade());
	}

//	private Double getSumValorUnitario(Array<TipoInvestimentoWrapper> list) {
//		return sum(list, /*TipoInvestimentoWrapper*/ item -> item.getSumValorUnitario());
//	}

	private Num getSumValorTotal(Array<TipoInvestimentoWrapper> list) {
		return sum(list, /*TipoInvestimentoWrapper*/ item -> item.getValorTotal());
	}

	private Num getSumValorTotalFinanciado(Array<TipoInvestimentoWrapper> list) {
		return sum(list, /*TipoInvestimentoWrapper*/ item -> item.getValorTotalFinanciado());
	}

	private PcbCols newCols(String title, Array<TipoInvestimentoWrapper> list) {
		PcbCols cols = new PcbCols();
		cols.col1 = title;
		cols.col2 = list.lengthArray() + "";
		cols.col3 = getSumQuantidades(list) + "";
		cols.col4 = getSumValorTotal(list).formatRs();
		cols.col5 = getSumValorTotalFinanciado(list).formatRs();
		return cols;
	}	
	
	public boolean sucesso() {
		return !Null.is(result) && result.sucesso && !Null.is(erros) && erros.lengthArray() == 0;
	}

	private void controlaInputPercentuais(StateFormControl perInput, StateFormControl perOposto) {

		perOposto.desbloqueia();
		perInput.desbloqueia();
		
		Num valorTotal = getValorTotalInvestimento();
		
		if (valorTotal.isZero()) {
			perOposto.setNum(Num.ZERO);
			perInput.setNum(Num.ZERO);
			percTotalInvestimentoOut.setNum(Num.ZERO);
		} else {
			
			Num per = perInput.getMoney();
			
			if (per.maiorQueZero()) {

				if (per.maior(Num.CEM)) {

					while (per.maior(Num.CEM)) {
						per = per.dividido(Num.DEZ);
					}
		
					perInput.desbloqueia();
					perInput.setNum(per);
					perInput.bloqueia();
					
				}
				
				if (per.menor(Num.CEM)) {
					perOposto.setNum(Num.CEM.menos(per));
				} else {
					perOposto.set(0);
				}
				
			} else {
				perOposto.set(100);
			}
			
			percTotalInvestimentoOut.setNum(Num.CEM);
			
		}

		perInput.bloqueia();
		perOposto.bloqueia();
		
		calcular();
		
//		valorTotalInvestimento.setNum(valorTotal);
//		atualizaPercentualTotal();

		perInput.desbloqueia();
		perOposto.desbloqueia();

	}
	
//	public void atualizaPercentualTotal() {
//		setPercTotalInvestimento(getPercFinanciada().mais(getPercRecursoProprioForm()));
//	}

	/* disparado qdo o campo "Valor do Crédito - Percentual [percFinanciada]" é alterado */
	public void atualizaPercentuaisBaseadoNoFinanciado() {
		controlaInputPercentuais(percentualFinanciado, percentualRecursoProprio);
	}

	/* disparado qdo o campo "Valor de Recursos Próprios - Percentual [percRecursoProprio]" é alterado */
	public void atualizaPercentuaisBaseadoNoNaoFinanciado() {
		controlaInputPercentuais(percentualRecursoProprio, percentualFinanciado);
	}

	public void atualizaPercentuaisBaseadoNosTotais() {
		
		Num total = getValorTotalInvestimento();
		
		if (total.isZero()) {
			zeraTudo();
			return;
		}
		
//		valorTotalInvestimento.setNum(total);
		
		Num financiaveis = getSomaDosItensFinanciaveis();
		
		if (financiaveis.igual(total)) {
			percentualFinanciado.desbloqueia();
			percentualFinanciado.setNum(Num.CEM);
			atualizaPercentuaisBaseadoNoFinanciado();
		} else if (financiaveis.igual(Num.ZERO)) {
			percentualRecursoProprio.desbloqueia();
			percentualRecursoProprio.setNum(Num.CEM);
			atualizaPercentuaisBaseadoNoNaoFinanciado();
		} else {
			calcula(false);
		}
		
	}
	
	private boolean zerando = false;
	
	public void zeraTudo() {
		
		if (zerando) {
			return;
		}
		
		if (Null.is(form)) {
			return;
		}

		zerando = true;

		percentualFinanciado.desbloqueia();
		percentualFinanciado.setNum(Num.ZERO);

		percentualRecursoProprio.desbloqueia();
		percentualRecursoProprio.setNum(Num.ZERO);
		
		percTotalInvestimentoOut.setNum(Num.ZERO);
		valorTotalInvestimentoOut.setNum(Num.ZERO);
		
		valorFinanciadoOut.setNum(Num.ZERO);
		valorRecursosPropriosOut.setNum(Num.ZERO);
		
//		console.log("PcbCalculo.zeraTudo");
		
//		valorTotalInvestimento.setNum(Num.ZERO);
		atualizaPercentuaisBaseadoNoNaoFinanciado();
//        const calc = this.calculo();
//        calc.setPercFinanciada(Num.ZERO);
//        calc.setPercRecursoProprioForm(Num.ZERO);
//        calc.setValorFinanciado(Num.ZERO);
//        calc.setValorRecursosPropriosForm(Num.ZERO);
		
		Js.setTimeout(() -> {
			zerando = false;
		}, TEMPO_REFRESH);
		
	}
	
	@IgnorarDaquiPraBaixo

	public static void main(String[] args) {
		SicoobTranspilar.exec(PcbCalculo.class);
	}
	
	public static void print() {
		PrintObj.exec(instance).print();
		PcbCalculo.instance.erros.forEach(i -> System.err.println(i));
	}
	
	public boolean atualizarTiposJava() {
		return atualizarTipos();
	}
	

}