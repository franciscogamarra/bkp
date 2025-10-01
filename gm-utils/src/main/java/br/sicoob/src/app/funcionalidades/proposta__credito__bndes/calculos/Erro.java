package br.sicoob.src.app.funcionalidades.proposta__credito__bndes.calculos;

import br.sicoob.SicoobTranspilar;
import gm.utils.anotacoes.IgnorarDaquiPraBaixo;
import js.array.Array;

public class Erro {

	public final String msg;
	public final Array<String> detalhes = new Array<>();
	
	public Erro(String msg) {
		this.msg = msg;
	}
	
	public Erro add(String s) {
		detalhes.push(s);
		return this;
	}
	
	@IgnorarDaquiPraBaixo
	
	@Override
	public String toString() {
		return msg + " / " + detalhes;
	}

	public static void main(String[] args) {
		SicoobTranspilar.exec(Erro.class);
	}
	
}
