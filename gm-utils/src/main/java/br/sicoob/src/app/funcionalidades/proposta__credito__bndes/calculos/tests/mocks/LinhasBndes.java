package br.sicoob.src.app.funcionalidades.proposta__credito__bndes.calculos.tests.mocks;

import br.sicoob.SicoobTranspilar;
import br.sicoob.src.app.funcionalidades.proposta__credito__bndes.models.LinhaBndes;
import gm.utils.anotacoes.IgnorarDaquiPraBaixo;
import js.array.Array;

public class LinhasBndes {

	public static final Array<LinhaBndes> itens = new Array<>();
	public static final LinhaBndes finame = LinhaBndes.json().id(301).descricao("FINAME");
	public static final LinhaBndes finameAgricola2015 = LinhaBndes.json().id(355).descricao("FINAME AGRÍCOLA 2015");
	public static final LinhaBndes bndesAutomatico = LinhaBndes.json().id(500).descricao("BNDES AUTOMÁTICO");
	
	static {
		itens.push(finame);
		itens.push(finameAgricola2015);
		itens.push(bndesAutomatico);
	}
	
	
	@IgnorarDaquiPraBaixo

	public static void main(String[] args) {
		SicoobTranspilar.exec(LinhasBndes.class);
	}
	
}
