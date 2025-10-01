package gm.utils.extrato.copiar;

import gm.utils.files.UFile;
import gm.utils.string.ListString;

public class CopiarDoCorporativo {

	public static void main(String[] args) {

		ListString naoExistentes = new ListString();
		naoExistentes.load("/tmp/naoExistentes.txt");

		ListString naoExistentes2 = new ListString();

		for (String s : naoExistentes) {

			if (corporativo(s)) {
				continue;
			}

			if (utils(s)) {
				continue;
			}

			naoExistentes2.add(s);

		}

		naoExistentes2.save("/tmp/naoExistentes2.txt");

	}

	private static boolean corporativo(String s) {
		return ok(s, "cooperforte-corporativo");
	}

	private static boolean utils(String s) {
		return ok(s, "cooperforte-comuns-utils");
	}

	private static boolean ok(String s, String path) {

		String origem = nomeOrigem(s, path);

		if (UFile.exists(origem)) {
			ListString list = new ListString().load(origem);
			String destino = origem.replace(path, "extrato/cooperforte-backend-extrato");
			list.rtrim();
			list.removeDoubleWhites();
			list.removeLastEmptys();
			list.removeFisrtEmptys();
			list.save(destino);
			return true;
		} else {
			return false;
		}

	}

	private static String nomeOrigem(String s, String path) {
		s = s.replace(".", "/");
		s = "/opt/desen/cooperforte/"+path+"/src/main/java/" + s + ".java";
		return s;
	}

}
