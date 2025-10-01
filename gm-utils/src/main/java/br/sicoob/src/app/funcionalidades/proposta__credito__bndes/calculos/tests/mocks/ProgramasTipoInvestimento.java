package br.sicoob.src.app.funcionalidades.proposta__credito__bndes.calculos.tests.mocks;

import br.sicoob.SicoobTranspilar;
import br.sicoob.src.app.funcionalidades.licenca_ambiental.models.TipoInvestimento;
import br.sicoob.src.app.funcionalidades.proposta__credito__bndes.models.ProgramaTipoInvestimento;
import gm.utils.anotacoes.IgnorarDaquiPraBaixo;
import js.array.Array;
import js.support.console;

public class ProgramasTipoInvestimento {

	public static final Array<ProgramaTipoInvestimento> itens = new Array<>();
	public static final ProgramaTipoInvestimento item1 = ProgramaTipoInvestimento.json().programa(Programas.p2424).bolTipoExclusivo(false).bolDescItemObrigatorio(true).tipoInvestimento(null);
	public static final ProgramaTipoInvestimento item8 = ProgramaTipoInvestimento.json().programa(Programas.p2424).bolTipoExclusivo(false).bolDescItemObrigatorio(true).tipoInvestimento(null);
	public static final ProgramaTipoInvestimento item9 = ProgramaTipoInvestimento.json().programa(Programas.p2424).bolTipoExclusivo(false).bolDescItemObrigatorio(true).tipoInvestimento(null);
	public static final ProgramaTipoInvestimento item14 = ProgramaTipoInvestimento.json().programa(Programas.p2424).bolTipoExclusivo(false).bolDescItemObrigatorio(true).tipoInvestimento(null);
	public static final ProgramaTipoInvestimento item17 = ProgramaTipoInvestimento.json().programa(Programas.p2424).bolTipoExclusivo(false).bolDescItemObrigatorio(true).tipoInvestimento(null);
	public static final ProgramaTipoInvestimento item18 = ProgramaTipoInvestimento.json().programa(Programas.p2424).bolTipoExclusivo(false).bolDescItemObrigatorio(true).tipoInvestimento(null);
	public static final ProgramaTipoInvestimento item32 = ProgramaTipoInvestimento.json().programa(Programas.p2424).bolTipoExclusivo(false).bolDescItemObrigatorio(true).tipoInvestimento(null);
	public static final ProgramaTipoInvestimento item34 = ProgramaTipoInvestimento.json().programa(Programas.p2424).bolTipoExclusivo(false).bolDescItemObrigatorio(true).tipoInvestimento(null);
	
	public static ProgramaTipoInvestimento getByItem(TipoInvestimento item) {
		Array<ProgramaTipoInvestimento> filter = itens.filter(i -> i.tipoInvestimento == item);
		return filter.length ? filter.array(0) : null;
	}

	private static boolean jaStartou = false;
	
	public static void start() {
		
		if (jaStartou) {
			return;
		}
		
		itens.push(item1);
		itens.push(item8);
		itens.push(item9);
		itens.push(item14);
		itens.push(item17);
		itens.push(item18);
		itens.push(item32);
		itens.push(item34);
		TiposInvestimento.item1.programaTipoInvestimentos.push(item1);
		TiposInvestimento.item8.programaTipoInvestimentos.push(item8);
		TiposInvestimento.item9.programaTipoInvestimentos.push(item9);
		TiposInvestimento.item14.programaTipoInvestimentos.push(item14);
		TiposInvestimento.item17.programaTipoInvestimentos.push(item17);
		TiposInvestimento.item18.programaTipoInvestimentos.push(item18);
		TiposInvestimento.item32.programaTipoInvestimentos.push(item32);
		TiposInvestimento.item34.programaTipoInvestimentos.push(item34);
		console.log("ProgramasTipoInvestimento start");
		
		jaStartou = true;
		
	}
	
	static {
		start();
	}

	@IgnorarDaquiPraBaixo

	public static void main(String[] args) {
		SicoobTranspilar.exec(ProgramasTipoInvestimento.class);
	}
	
}
