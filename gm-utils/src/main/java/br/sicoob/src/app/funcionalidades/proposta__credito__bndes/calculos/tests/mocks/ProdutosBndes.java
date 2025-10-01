package br.sicoob.src.app.funcionalidades.proposta__credito__bndes.calculos.tests.mocks;

import br.sicoob.SicoobTranspilar;
import br.sicoob.src.app.funcionalidades.proposta__credito__bndes.models.ProdutoBndes;
import gm.utils.anotacoes.IgnorarDaquiPraBaixo;
import js.array.Array;

public class ProdutosBndes {

	public static final Array<ProdutoBndes> itens = new Array<>();
	public static final ProdutoBndes creditoRural = ProdutoBndes.json().id(6).descricao("CREDITO RURAL");
	public static final ProdutoBndes emprestimo = ProdutoBndes.json().id(7).descricao("EMPRÉSTIMO");
	
	static {
		itens.push(creditoRural);
		itens.push(emprestimo);
	}	

	
	@IgnorarDaquiPraBaixo

	public static void main(String[] args) {
		SicoobTranspilar.exec(ProdutosBndes.class);
	}
	
}
