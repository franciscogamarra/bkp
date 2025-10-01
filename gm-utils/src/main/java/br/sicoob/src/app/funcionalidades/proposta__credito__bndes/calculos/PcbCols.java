package br.sicoob.src.app.funcionalidades.proposta__credito__bndes.calculos;

import br.sicoob.SicoobTranspilar;
import gm.utils.anotacoes.IgnorarDaquiPraBaixo;
import js.support.console;

public class PcbCols {

	public String col1 = "";
	public String col2 = "";
	public String col3 = "";
	public String col4 = "";
	public String col5 = "";
	public String col6 = "";
	public String col7 = "";
	public String col8 = "";
	public String col9 = "";

	public void print() {
		String s = col1;
		while (s.length() < 25) {
			s = s + " ";
		}
		s += "|";
		console.log(s + f(col2) + f(col3) + f(col4) + f(col5) + f(col6) + f(col7) + f(col8) + f(col9));
	}

	private String f(String s) {
		while (s.length() < 23) {
			s = " " + s;
		}
		return s + " |";
	}

	@IgnorarDaquiPraBaixo

	public static void main(String[] args) {
		SicoobTranspilar.exec(PcbCols.class);
	}

}
