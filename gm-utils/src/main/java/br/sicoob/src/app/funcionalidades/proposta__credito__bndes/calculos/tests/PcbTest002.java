package br.sicoob.src.app.funcionalidades.proposta__credito__bndes.calculos.tests;


import br.sicoob.SicoobTranspilar;
import br.sicoob.src.app.funcionalidades.proposta__credito__bndes.calculos.tests.mocks.LinhasBndes;
import br.sicoob.src.app.funcionalidades.proposta__credito__bndes.calculos.tests.mocks.Produtos;
import br.sicoob.src.app.funcionalidades.proposta__credito__bndes.calculos.tests.mocks.Programas;
import br.sicoob.src.app.funcionalidades.proposta__credito__bndes.calculos.tests.mocks.ProgramasTipoInvestimento;
import br.sicoob.src.app.funcionalidades.proposta__credito__bndes.calculos.tests.mocks.TiposInvestimento;
import gm.utils.anotacoes.IgnorarDaquiPraBaixo;

public class PcbTest002 extends PcbTestBase {

	@Override
	protected void run() {

		ProgramasTipoInvestimento.start();
		new Acao001SelecionarProduto(Produtos.creditoRural);
		new Acao002SelecionarLinhaBndes(LinhasBndes.finame);
		new Acao003SelecionarPrograma(Programas.p2424);
		
		new Acao005ExpandirAbaItensDeInvestimento();

		addTipo(TiposInvestimento.item1);
		addItem(1, 35800);
		new Acao900AssertEquals(1, () -> TiposInvestimento.item1.itensInvestimento.lengthArray());
		addItem(1, 1500);
		new Acao900AssertEquals(2, () -> TiposInvestimento.item1.itensInvestimento.lengthArray());
		
		addTipo(TiposInvestimento.item8);
		addItem(1, 1589.50);
		new Acao900AssertEquals(1, () -> TiposInvestimento.item8.itensInvestimento.lengthArray());
		
		new Acao018ExpandirAbaOperacoes();
		
		new Acao020AbrirCalculadora();
		new Acao021CalculadoraSetValorCredito(11569.87);
		new Acao023CalculadoraSubmit();
		
		PcbAcao.run();
		
	}
	
	public static void exec() {
		new PcbTest002().run();
	}

	@IgnorarDaquiPraBaixo

	public static void main(String[] args) {
		SicoobTranspilar.exec(PcbTest002.class);
	}

}
