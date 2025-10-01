package src.commom.utils.string;

import js.support.console;

public class StringCorretorOrtografico {

	private static final String cao = StringConstants.cedilha + StringConstants.a_til + "o";
	private static final String coes = StringConstants.cedilha + StringConstants.o_til + "es";
	private static final String orio = StringConstants.o_agudo + "rio";
	private static final String ario = StringConstants.a_agudo + "rio";
	private static final String itona = StringConstants.i_agudo + "tona";

	public static String exec(String s) {

		if (StringEmpty.is(s)) {
			return s;
		}

		s = StringReplacePalavra.exec(s, "eh", StringConstants.e_agudo);
		s = StringReplacePalavra.exec(s, "Eh", StringConstants.E_agudo);

		StringBox box = new StringBox(s);
		box.replace("nvalid", "nv"+StringConstants.a_agudo+"lid");

		StringConstants.SIMBOLOS.forEach(b -> {
			box.replace("cao"+b, cao + b);
			box.replace("coes"+b, coes + b);
			box.replace("orio"+b, orio + b);
			box.replace("ario"+b, ario + b);
			box.replace("itona"+b, itona + b);
		});

		return box.get();

	}

	public static void main(String[] args) {
		console.log(exec("isso eh demais"));
		console.log(exec("Eh isso eh demais"));
	}

}
