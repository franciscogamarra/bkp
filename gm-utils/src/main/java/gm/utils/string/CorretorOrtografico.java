package gm.utils.string;

import gm.utils.comum.SystemPrint;
import gm.utils.comum.UConstantes;
import src.commom.utils.string.StringTrim;

public class CorretorOrtografico {

	private static final String ao = UConstantes.a_til + "o";
	private static final String orio = UConstantes.o_agudo + "rio";
	private static final String ario = UConstantes.a_agudo + "rio";
	private static final String itona = UConstantes.i_agudo + "tona";
	private static final String ivel = UConstantes.i_agudo + "vel";

	public static String exec(String s) {

		s = UString.replacePalavra(s, "eh", UConstantes.e_agudo);
		s = UString.replacePalavra(s, "Eh", UConstantes.E_agudo);

		s = s.replace("ah", UConstantes.a_agudo);

		s = s.replace("nvalid", "nv"+UConstantes.a_agudo+"lid");

		for (String b : UConstantes.SIMBOLOS) {
			s = s.replace("ao"+b, ao + b);
			s = s.replace("orio"+b, orio + b);
			s = s.replace("ario"+b, ario + b);
			s = s.replace("itona"+b, itona + b);
			s = s.replace("ivel"+b, ivel + b);
		}

		return s.replace(" "+ao+" ", " ao ");
	}

	public static void main(String[] args) {
		String s = StringClipboard.get();
		s = StringTrim.right(s);
		s = exec(s);
		StringClipboard.set(s);
		SystemPrint.ln(s);
	}

}
