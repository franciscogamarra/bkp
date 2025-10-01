package br.sicoob.src.app.funcionalidades.proposta__credito__bndes.calculos.tests;


import br.sicoob.SicoobTranspilar;
import br.sicoob.src.app.funcionalidades.licenca_ambiental.models.TipoInvestimento;
import br.sicoob.src.app.funcionalidades.proposta__credito__bndes.calculos.TipoInvestimentoWrapper;
import br.sicoob.src.app.funcionalidades.proposta__credito__bndes.calculos.tests.mocks.EquipamentosLegados;
import br.sicoob.src.app.funcionalidades.proposta__credito__bndes.calculos.tests.mocks.FabricantesLegados;
import gm.utils.anotacoes.IgnorarDaquiPraBaixo;

public abstract class PcbTestBase {

	protected static TipoInvestimento tipoCorrente;
	
	protected static void addTipo(TipoInvestimento tipo) {
		new Acao004AdicionarTipoDeInvestimento();
		new Acao006SelecionarTipoDeInvestimentoNoUltimoItemAdicionado(tipo);
		tipoCorrente = tipo;
	}
	
	protected abstract void run();

	protected void addItem(int qtd, double valor) {

		new Acao007MenuItemExpandir(tipoCorrente);
		
		TipoInvestimentoWrapper tw = TipoInvestimentoWrapper.get(tipoCorrente);
		if (tw.isValores()) {
			new Acao012MenuItemSelecionarValorTotal(valor);
		} else if (tw.isEquipamento()) {
			new Acao008MenuItemBuscarFabricantes("maquina");
			new Acao009MenuItemPreencherFabricante(FabricantesLegados.CSM);
			new Acao010MenuItemPreencherEquipamento(EquipamentosLegados.item1);
			new Acao011MenuItemSelecionarQuantidade(qtd);
			new Acao017MenuItemSelecionarValorUnitario(valor);
			new Acao013MenuItemInserirInformacaoAdicional("A");
		} else if (tw.isQuantidades()) {
			new Acao011MenuItemSelecionarQuantidade(qtd);
			new Acao017MenuItemSelecionarValorUnitario(valor);
		} else {
			throw new Error("NaoImplementadoException");
		}

		new Acao014MenuItemClicarEmAdicionar(tipoCorrente);
		
	}
	
	@IgnorarDaquiPraBaixo

	public static void main(String[] args) {
		SicoobTranspilar.exec(PcbTestBase.class);
	}

}
