package gm.utils.extrato.copiar;

import java.io.File;

import gm.utils.comum.Lst;
import gm.utils.files.UFile;
import gm.utils.string.ListString;

public class ListarTodasAsClasses {

	public static void main(String[] args) {

		ListString todas = new ListString();

		Lst<File> javas = UFile.getJavas("/opt/desen/cooperforte/extrato/cooperforte-backend-extrato/src/main/java/br");

		for (File file : javas) {

			ListString list = new ListString().load(file);
			list.trimPlus();
			list.removeIfNotStartsWith("import br.");
			list.removeIf(s -> s.endsWith("*;"));
			list.eachRemoveBefore("import ", true);
			list.removeRight(1);

			for (String s : list) {
				todas.addIfNotContains(s);
			}

		}

		todas.save("/tmp/todas.txt");

	}

}
