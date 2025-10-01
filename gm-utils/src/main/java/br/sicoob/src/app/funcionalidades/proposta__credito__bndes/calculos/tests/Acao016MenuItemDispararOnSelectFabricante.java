package br.sicoob.src.app.funcionalidades.proposta__credito__bndes.calculos.tests;

import br.sicoob.src.app.funcionalidades.proposta__credito__bndes.comps.BuscarEquipamentosBndesComponent;
import br.sicoob.src.app.funcionalidades.proposta__credito__bndes.models.FabricanteLegado;

public class Acao016MenuItemDispararOnSelectFabricante extends PcbAcao {

	private final FabricanteLegado fabricante;

	public Acao016MenuItemDispararOnSelectFabricante(FabricanteLegado fabricante) {
		super("Acao009bMenuItemDispararOnSelectFabricante: " + fabricante.codigoFabricante);
		this.fabricante = fabricante;
	}

	@Override
	public void acao() {
		BuscarEquipamentosBndesComponent.instance.onSelectFabricante(fabricante);
	}
	
}
