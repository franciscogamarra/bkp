package src.commom.utils.string;

import js.map.Map;
import src.commom.utils.object.Null;

public class StringRemoveAcentos {

	private StringRemoveAcentos() {}

	private static Map<String, String> map = new Map<String, String>()
		.set(StringConstants.a_agudo, "a")
		.set(StringConstants.A_agudo, "A")
		.set(StringConstants.a_crase, "a")
		.set(StringConstants.A_crase, "A")
		.set(StringConstants.a_circunflexo, "a")
		.set(StringConstants.A_circunflexo, "A")
		.set(StringConstants.a_til, "a")
		.set(StringConstants.A_til, "A")
		.set(StringConstants.a_trema, "a")
		.set(StringConstants.A_trema, "A")
		.set(StringConstants.e_agudo, "e")
		.set(StringConstants.E_agudo, "E")
		.set(StringConstants.e_circunflexo, "e")
		.set(StringConstants.E_circunflexo, "E")
		.set(StringConstants.e_crase, "e")
		.set(StringConstants.E_crase, "E")
		.set(StringConstants.e_trema, "e")
		.set(StringConstants.E_trema, "E")
		.set(StringConstants.e_til, "e")
		.set(StringConstants.E_til, "E")
		.set(StringConstants.i_agudo, "i")
		.set(StringConstants.I_agudo, "I")
		.set(StringConstants.i_crase, "i")
		.set(StringConstants.I_crase, "I")
		.set(StringConstants.i_circunflexo, "i")
		.set(StringConstants.I_circunflexo, "I")
		.set(StringConstants.i_trema, "i")
		.set(StringConstants.I_trema, "I")
		.set(StringConstants.i_til, "i")
		.set(StringConstants.I_til, "I")
		.set(StringConstants.o_agudo, "o")
		.set(StringConstants.O_agudo, "O")
		.set(StringConstants.o_crase, "o")
		.set(StringConstants.O_crase, "O")
		.set(StringConstants.o_circunflexo, "o")
		.set(StringConstants.O_circunflexo, "O")
		.set(StringConstants.o_til, "o")
		.set(StringConstants.O_til, "O")
		.set(StringConstants.o_trema, "o")
		.set(StringConstants.O_trema, "O")
		.set(StringConstants.u_agudo, "u")
		.set(StringConstants.U_agudo, "U")
		.set(StringConstants.u_crase, "u")
		.set(StringConstants.U_crase, "U")
		.set(StringConstants.u_til, "u")
		.set(StringConstants.U_til, "U")
		.set(StringConstants.u_circunflexo, "u")
		.set(StringConstants.U_circunflexo, "U")
		.set(StringConstants.u_trema, "u")
		.set(StringConstants.U_trema, "U")
		.set(StringConstants.n_til, "n")
		.set(StringConstants.N_til, "N")
		.set(StringConstants.cedilha, "c")
		.set(StringConstants.CEDILHA, "C")
	;

	public static String exec(String s) {

		if (Null.is(s)) {
			return null;
		}
		if (StringEmpty.is(s)) {
			return s;
		}

		StringBox box = new StringBox(s);
		map.forEach((v, k) -> box.set(box.get().replace(k, v)));
		return box.get();

	}

}
