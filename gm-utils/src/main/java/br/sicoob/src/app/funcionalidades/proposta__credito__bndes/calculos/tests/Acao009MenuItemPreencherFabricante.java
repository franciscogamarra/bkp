package br.sicoob.src.app.funcionalidades.proposta__credito__bndes.calculos.tests;

import br.sicoob.src.app.funcionalidades.proposta__credito__bndes.calculos.tests.proxies.AdicionaItensForm;
import br.sicoob.src.app.funcionalidades.proposta__credito__bndes.models.FabricanteLegado;

public class Acao009MenuItemPreencherFabricante extends PcbAcao {

	private final FabricanteLegado fabricante;

	public Acao009MenuItemPreencherFabricante(FabricanteLegado fabricante) {
		super("Acao009aMenuItemPreencherFabricante: " + fabricante.codigoFabricante);
		new Acao016MenuItemDispararOnSelectFabricante(fabricante);
		this.fabricante = fabricante;
	}

	@Override
	public void acao() {
		AdicionaItensForm.setFabricante(fabricante);
	}

}
