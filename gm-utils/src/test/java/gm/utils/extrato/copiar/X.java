package gm.utils.extrato.copiar;

import java.io.File;

import gm.utils.comum.Lst;
import gm.utils.comum.SystemPrint;
import gm.utils.files.UFile;
import gm.utils.string.ListString;

public class X {

	public static void main(String[] args) {
		exec("cooperforte-backend-extrato");
//		exec("cooperforte-facade-extrato");
	}

	private static void exec(String path) {

		Lst<File> javas = UFile.getJavas("/opt/desen/cooperforte/extrato/"+path+"/src/main/java/");

		for (File file : javas) {

			String origem = file.toString();

			ListString list = new ListString().load(origem);
			String destino = origem.replace(path, "cooper-extrato");
			list.rtrim();
			list.removeDoubleWhites();
			list.removeLastEmptys();
			list.removeFisrtEmptys();
			list.save(destino);

			SystemPrint.ln(origem);
			SystemPrint.ln(destino);
			SystemPrint.ln();

		}

	}

}
