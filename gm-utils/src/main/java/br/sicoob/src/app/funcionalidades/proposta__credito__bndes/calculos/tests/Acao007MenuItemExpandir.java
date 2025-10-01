package br.sicoob.src.app.funcionalidades.proposta__credito__bndes.calculos.tests;

import br.sicoob.SicoobTranspilar;
import br.sicoob.src.app.funcionalidades.licenca_ambiental.models.TipoInvestimento;
import br.sicoob.src.app.funcionalidades.proposta__credito__bndes.comps.AbaItensInvestimentoComponent;
import gm.utils.anotacoes.IgnorarDaquiPraBaixo;

public class Acao007MenuItemExpandir extends PcbAcao {

	private final TipoInvestimento tipo;

	public Acao007MenuItemExpandir(TipoInvestimento tipo) {
		super("Acao007MenuItemExpandir: " + tipo.id);
		this.tipo = tipo;
		new Acao007MenuItemExpandirContinue(tipo);
	}

	@Override
	public void acao() {
		AbaItensInvestimentoComponent.instance.incluirItensInvestimento(tipo);
	}
	
	@Override
	public int tempoDeExecucao() {
		return 1000;
	}
	
	
	@IgnorarDaquiPraBaixo

	public static void main(String[] args) {
		SicoobTranspilar.exec(Acao007MenuItemExpandir.class);
	}

}
