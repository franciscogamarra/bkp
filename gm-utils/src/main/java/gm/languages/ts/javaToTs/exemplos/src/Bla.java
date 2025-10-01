package gm.languages.ts.javaToTs.exemplos.src;

import gm.languages.ts.javaToTs.JavaToTs;
import gm.utils.anotacoes.IgnorarDaquiPraBaixo;
import gm.utils.comum.SystemPrint;
import gm.utils.string.ListString;
import lombok.Getter;

@Getter
public enum Bla {
	
	segunda, terca;
	
	@IgnorarDaquiPraBaixo
	
	public static void main(String[] args) {
		ListString list = JavaToTs.execJavaToTs(Bla.class);
		SystemPrint.ln();
		SystemPrint.ln();
		SystemPrint.ln();
		list.print();
//		list.save("C:\\opt\\desen\\gm\\elfa-care-front\\src\\commom\\utils\\tempo\\Bla.tsx");
	}
	
}
