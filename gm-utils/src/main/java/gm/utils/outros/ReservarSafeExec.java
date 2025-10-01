package gm.utils.outros;

import java.io.File;
import java.util.List;

import gm.utils.files.UFile;
import gm.utils.string.ListString;
import src.commom.utils.string.StringContains;

public class ReservarSafeExec {

	public static void main(String[] args) {
//		reservar("/opt/desen/cooperforte/mobile/");
		voltar("/opt/desen/cooperforte/mobile/");
//		voltarSa("/opt/desen/cooperforte/mobile/");
	}

	private String de;
	private String para;

	private ReservarSafeExec(String de, String para) {
		this.de = de;
		this.para = para;
		exec(new File(de));
	}

	private void exec(File path) {
		List<File> files = UFile.getFiles(path);
		for (File file : files) {
			String s = file.toString().replace(de, para);
			
			if (UFile.exists(s)) {
				ListString de = new ListString().load(file);
				ListString para = new ListString().load(s);
				
				if (!para.eq(de)) {
					de.save(s);
				}
				
			} else {
				UFile.copy(file, new File(s));
			}
			
		}
		List<File> directories = UFile.getDirectories(path);
		for (File file : directories) {
			String s = file.toString();
			if (ignorar(s)) {
				continue;
			}
			exec(file);
		}
	}

	private boolean ignorar(String s) {
		return StringContains.is(s, "node_modules") || StringContains.is(s, ".git") || StringContains.is(s, "/target/") || StringContains.is(s, ".expo");
	}

	public static void reservar(String s) {
		UFile.delete("/tmp/reserva/");
		new ReservarSafeExec(s, "/tmp/reserva/");
	}
	public static void voltar(String s) {
		new ReservarSafeExec("/tmp/reserva/", s);
	}

}
