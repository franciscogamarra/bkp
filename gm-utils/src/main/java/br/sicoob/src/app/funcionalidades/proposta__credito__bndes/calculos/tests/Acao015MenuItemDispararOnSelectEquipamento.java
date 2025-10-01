package br.sicoob.src.app.funcionalidades.proposta__credito__bndes.calculos.tests;

import br.sicoob.src.app.funcionalidades.proposta__credito__bndes.comps.BuscarEquipamentosBndesComponent;
import br.sicoob.src.app.funcionalidades.proposta__credito__bndes.models.EquipamentoLegado;

public class Acao015MenuItemDispararOnSelectEquipamento extends PcbAcao {

	private EquipamentoLegado equipamento;

	public Acao015MenuItemDispararOnSelectEquipamento(EquipamentoLegado equipamento) {
		super("Acao015MenuItemDispararOnSelectEquipamento: " + equipamento.codigoEquipamento);
		this.equipamento = equipamento;
	}

	@Override
	public void acao() {
		BuscarEquipamentosBndesComponent.instance.onSelectEquipamento(equipamento);
	}
	
}
