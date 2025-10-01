package br.sicoob.src.app.funcionalidades.proposta__credito__bndes.calculos.tests;

import br.sicoob.src.app.funcionalidades.proposta__credito__bndes.calculos.tests.proxies.AdicionaItensForm;
import br.sicoob.src.app.funcionalidades.proposta__credito__bndes.models.EquipamentoLegado;

public class Acao010MenuItemPreencherEquipamento extends PcbAcao {

	private final EquipamentoLegado equipamento;

	public Acao010MenuItemPreencherEquipamento(EquipamentoLegado equipamento) {
		super("Acao010aMenuItemPreencherEquipamento: " + equipamento.codigoEquipamento);
		new Acao015MenuItemDispararOnSelectEquipamento(equipamento);
		this.equipamento = equipamento;
	}

	@Override
	public void acao() {
		AdicionaItensForm.setEquipamento(equipamento);
	}

}
