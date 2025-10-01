package br.sicoob.src.app.funcionalidades.proposta__credito__bndes.calculos.tests.mocks;

import br.sicoob.SicoobTranspilar;
import br.sicoob.src.app.funcionalidades.proposta__credito__bndes.models.Produto;
import gm.utils.anotacoes.IgnorarDaquiPraBaixo;
import js.array.Array;

public class Produtos {

	public static final Array<Produto> itens = new Array<>();
	public static final Produto creditoRural = Produto.json().id(6).descricao("CREDITO RURAL");
	public static final Produto emprestimo = Produto.json().id(7).descricao("EMPRÉSTIMO");
	
	static {
		itens.push(creditoRural);
		itens.push(emprestimo);
	}	

	
	@IgnorarDaquiPraBaixo

	public static void main(String[] args) {
		SicoobTranspilar.exec();
	}
	
}
