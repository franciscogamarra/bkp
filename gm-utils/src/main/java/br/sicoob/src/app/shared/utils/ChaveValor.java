package br.sicoob.src.app.shared.utils;

import br.sicoob.SicoobTranspilar;
import gm.utils.anotacoes.IgnorarDaquiPraBaixo;

public class ChaveValor {

	public String chave;
	public String valor;
	
	public static ChaveValor build(String chave, String valor) {
		ChaveValor o = new ChaveValor();
		o.chave = chave;
		o.valor = valor;
		return o;
	}
	
	@IgnorarDaquiPraBaixo

	public static void main(String[] args) {
		SicoobTranspilar.exec(ChaveValor.class);
	}
	
	@Override
	public String toString() {
		return chave + " = " + valor;
	}

}