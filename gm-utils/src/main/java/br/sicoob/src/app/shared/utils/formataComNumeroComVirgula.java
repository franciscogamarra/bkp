package br.sicoob.src.app.shared.utils;

import gm.languages.ts.javaToTs.annotacoes.From;
import gm.languages.ts.javaToTs.annotacoes.ImportStatic;
import gm.utils.number.Numeric;

@ImportStatic
@From("@app/shared/utils/utils")
public class formataComNumeroComVirgula {

	public static String call(Double d, int casas) {
		return Numeric.toNumeric(d, casas).toString();
	}
	
	public static String call(Double d) {
		return call(d, 2);
	}
	
}