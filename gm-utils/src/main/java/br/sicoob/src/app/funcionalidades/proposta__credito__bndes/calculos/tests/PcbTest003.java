package br.sicoob.src.app.funcionalidades.proposta__credito__bndes.calculos.tests;


import br.sicoob.SicoobTranspilar;
import br.sicoob.src.app.funcionalidades.proposta__credito__bndes.calculos.tests.mocks.LinhasBndes;
import br.sicoob.src.app.funcionalidades.proposta__credito__bndes.calculos.tests.mocks.Produtos;
import br.sicoob.src.app.funcionalidades.proposta__credito__bndes.calculos.tests.mocks.Programas;
import br.sicoob.src.app.funcionalidades.proposta__credito__bndes.calculos.tests.mocks.ProgramasTipoInvestimento;
import br.sicoob.src.app.funcionalidades.proposta__credito__bndes.calculos.tests.mocks.TiposInvestimento;
import gm.utils.anotacoes.IgnorarDaquiPraBaixo;

public class PcbTest003 extends PcbTestBase {

	@Override
	protected void run() {

		ProgramasTipoInvestimento.start();
		new Acao001SelecionarProduto(Produtos.creditoRural);
		new Acao002SelecionarLinhaBndes(LinhasBndes.finame);
		new Acao003SelecionarPrograma(Programas.p2424);
		
		new Acao005ExpandirAbaItensDeInvestimento();

		addTipo(TiposInvestimento.item1);
		addItem(1, 1200);

		addTipo(TiposInvestimento.item17);
		addItem(1, 2000);//3200
		addItem(1, 1200);//4400
		
		addTipo(TiposInvestimento.item18);
		addItem(1, 5600);//10000

		addTipo(TiposInvestimento.item14);
		addItem(1, 1000);//11000

		addTipo(TiposInvestimento.item34);
		addItem(1, 20000);//31000
		
		new Acao018ExpandirAbaOperacoes();
		
		new Acao019AbaOperacaoSetPercentualFinanciado(35.48);
//		new Acao019AbaOperacaoSetPercentualFinanciado(64.52);
		
		/**/
		
		PcbAcao.run();
		
	}
	
	public static void exec() {
		new PcbTest003().run();
	}

	@IgnorarDaquiPraBaixo

	public static void main(String[] args) {
		SicoobTranspilar.exec(PcbTest003.class);
	}

}
