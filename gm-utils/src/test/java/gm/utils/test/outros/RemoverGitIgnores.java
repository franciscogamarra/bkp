package gm.utils.test.outros;

import java.io.File;

import gm.utils.comum.Lst;
import gm.utils.files.UFile;

public class RemoverGitIgnores {

	public static void main(String[] args) {
		Lst<File> list = UFile.getAllFiles("/opt/desen/gm/cs2019").filter(o -> o.getName().equalsIgnoreCase(".gitignore"));
		list.removeIf(o -> o.toString().equalsIgnoreCase("/opt/desen/gm/cs2019/.gitignore"));
		UFile.delete(list);
	}

}
