package gm.utils.lambda;

import gm.utils.comum.SystemPrint;
import gm.utils.javaCreate.JcClasse;
import gm.utils.javaCreate.JcMetodo;
import gm.utils.javaCreate.JcTipo;

public class Gerar {

	public static void main(String[] args) {
		
		for (int i = 0; i < 10; i++) {
			exec(i, true);
		}

		for (int i = 0; i < 10; i++) {
			exec(i, false);
		}
		
	}

	private static void exec(int params, boolean func) {
		
		String prefixo = func ? "F" : "P";
		
		JcClasse jc = JcClasse.build("gm.utils.lambda", prefixo + params);
		jc.setIsInterface(true);
		jc.addAnnotation(FunctionalInterface.class);
		
		JcMetodo m = jc.metodo("call");
		
		String sGen = "";
		String sParams = "";
		
		if (params > 0) {
			
			for (int i = 1; i <= params; i++) {
				JcTipo x = JcTipo.GENERICS("I"+i);
				jc.addGenerics(x);
				m.param("i"+i, x);
				sGen += ",I"+i;
				sParams += ",i"+i + ":I"+i;
			}
			
			sParams = sParams.substring(1);
			
		}
		
		
		if (func) {
			sGen += ",OUT";
		}
		
		if (!sGen.isEmpty()) {
			sGen = "<"+sGen.substring(1)+">";
		}

		String s = "export type " + prefixo + params + sGen + " = (" + sParams + ") => " + (func ? "OUT;" : "void;");
		
		SystemPrint.ln(s);
		

		if (func) {
			JcTipo out = JcTipo.GENERICS("OUT");
			jc.addGenerics(out);
			m.type(out);
		}

		jc.setPularLinhaAntesDeCadaMetodo(false);
		jc.save();
	}
	
}
