package br.sicoob.src.app.funcionalidades.proposta__credito__bndes.calculos.tests;

import br.sicoob.src.app.funcionalidades.proposta__credito__bndes.comps.BuscarEquipamentosBndesComponent;

public class Acao008MenuItemBuscarFabricantes extends PcbAcao {

	private String filtro;

	public Acao008MenuItemBuscarFabricantes(String filtro) {
		super("Acao008MenuItemBuscarFabricantes: " + filtro);
		this.filtro = filtro;
	}

	@Override
	public void acao() {
		BuscarEquipamentosBndesComponent.instance.onChangeCodigoFabricante(filtro);
	}

}
