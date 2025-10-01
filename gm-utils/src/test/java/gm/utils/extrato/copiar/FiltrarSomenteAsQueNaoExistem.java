package gm.utils.extrato.copiar;

import gm.utils.files.UFile;
import gm.utils.string.ListString;

public class FiltrarSomenteAsQueNaoExistem {

	public static void main(String[] args) {

		ListString todas = new ListString();
		todas.load("/tmp/todas.txt");

		ListString naoExistentes = new ListString();

		for (String s : todas) {

			if (!exists(s)) {
				naoExistentes.add(s);
			}

		}

		naoExistentes.save("/tmp/naoExistentes.txt");

	}

	private static boolean exists(String s) {
		s = s.replace(".", "/");
		s = "/opt/desen/cooperforte/extrato/cooperforte-backend-extrato/src/main/java/" + s + ".java";
		return UFile.exists(s);
	}

}
