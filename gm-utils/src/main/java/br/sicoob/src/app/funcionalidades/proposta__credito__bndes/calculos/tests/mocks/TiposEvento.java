package br.sicoob.src.app.funcionalidades.proposta__credito__bndes.calculos.tests.mocks;

import br.sicoob.SicoobTranspilar;
import br.sicoob.src.app.funcionalidades.proposta__credito__bndes.models.TipoEvento;
import gm.utils.anotacoes.IgnorarDaquiPraBaixo;
import js.array.Array;

public class TiposEvento {
	
	public static final Array<TipoEvento> itens = new Array<>();
	
	static {
		itens.push(TipoEvento.json().id(9).descricao("Homologação de Contratação"));
	}
	
	@IgnorarDaquiPraBaixo

	public static void main(String[] args) {
		SicoobTranspilar.exec(TiposEvento.class);
	}	
}
