package br.sicoob.src.app.funcionalidades.proposta__credito__bndes.calculos.tests;

import br.sicoob.SicoobTranspilar;
import br.sicoob.src.app.funcionalidades.licenca_ambiental.models.TipoInvestimento;
import br.sicoob.src.app.funcionalidades.proposta__credito__bndes.calculos.TipoInvestimentoWrapper;
import br.sicoob.src.app.funcionalidades.proposta__credito__bndes.calculos.tests.proxies.AdicionaItensForm;
import br.sicoob.src.app.funcionalidades.proposta__credito__bndes.comps.AdicionaEquipamentosComponent;
import br.sicoob.src.app.funcionalidades.proposta__credito__bndes.comps.AdicionaItensInvestimentoComQuantidadeComponent;
import br.sicoob.src.app.funcionalidades.proposta__credito__bndes.comps.AdicionaItensInvestimentoComponent;
import gm.utils.anotacoes.IgnorarDaquiPraBaixo;
import js.Error;

public class Acao007MenuItemExpandirContinue extends PcbAcao {

	private final TipoInvestimento tipo;

	public Acao007MenuItemExpandirContinue(TipoInvestimento tipo) {
		super("Acao007MenuItemExpandirContinue: " + tipo.id);
		this.tipo = tipo;
	}

	@Override
	public void acao() {
		
		TipoInvestimentoWrapper t = TipoInvestimentoWrapper.get(tipo);
		
		AdicionaItensForm.tipo = t;
		
		if (t.isValores()) {
			AdicionaItensForm.form = AdicionaItensInvestimentoComponent.instance.form;
			AdicionaItensForm.formulario = AdicionaItensInvestimentoComponent.instance;
		} else if (t.isEquipamento()) {
			AdicionaItensForm.form = AdicionaEquipamentosComponent.instance.form;
			AdicionaItensForm.formulario = AdicionaEquipamentosComponent.instance;
		} else if (t.isQuantidades()) {
			AdicionaItensForm.form = AdicionaItensInvestimentoComQuantidadeComponent.instance.form;
			AdicionaItensForm.formulario = AdicionaItensInvestimentoComQuantidadeComponent.instance;
		} else {
			throw new Error("Nao implementado");
		}
		
	}
	
	@IgnorarDaquiPraBaixo

	public static void main(String[] args) {
		SicoobTranspilar.exec(Acao007MenuItemExpandirContinue.class);
	}

}