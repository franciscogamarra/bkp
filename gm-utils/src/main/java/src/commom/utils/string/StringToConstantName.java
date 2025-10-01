package src.commom.utils.string;

import gm.utils.comum.SystemPrint;
import gm.utils.string.StringNormalizer;
import src.commom.utils.integer.IntegerIs;

public class StringToConstantName {

	private StringToConstantName() {}

	public static String exec(String s) {
		s = StringTrim.plus(s);
		if (StringEmpty.is(s)) {
			return s;
		}
		
		s = s.replace("%", " percent ");
		s = s.replace("/", " barra ");
		s = s.replace(">", " maior ");
		s = s.replace("<", " menor ");
		s = s.replace("=", " igual ");
		s = s.replace("\"", " aspasduplas ");
		s = s.replace("-", " ");
		s = StringTrim.plus(s);
		s = StringToCamelCaseSepare.exec(s);
		s = s.replace("-", "");
		s = s.replace(" ", "_");
		s = s.toUpperCase();
		s = StringNormalizer.get(s);
		StringBox box = new StringBox(s);
		StringConstants.NUMEROS.forEach(numero -> {
			box.replace("_" + numero, numero);
		});
		s = box.get();
		StringReplace.exec(s, "__", "_");
		if (IntegerIs.is(s.substring(0, 1))) {
			s = "_" + s;
		}
		s = StringRemoveAcentos.exec(s.toUpperCase());
		s = s.replace("_E_NUM_ERAT", "_ENUMERAT");
		return s;
	}
	
	public static void main(String[] args) {
//		String s = StringClipboard.get();
		String s = "Instância de Servidor (Pod)";
		s = exec(s);
		SystemPrint.ln("private static final String HQL_" + s + " = ");
	}

}
