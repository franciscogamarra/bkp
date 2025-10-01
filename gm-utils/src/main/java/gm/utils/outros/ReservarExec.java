package gm.utils.outros;

import gm.utils.comum.Lst;
import gm.utils.comum.SystemPrint;
import gm.utils.files.GFile;

public class ReservarExec {

	public static void main(String[] args) {
		voltar("C:/dev/projects/cre-concessao-bndes-web");
//		reservar("/opt/desen/cooperforte/mobile/");
//		voltar("/opt/desen/cooperforte/mobile/");
//		voltarSa("/opt/desen/cooperforte/mobile/");
	}

	private GFile de;
	private GFile para;

	private ReservarExec(GFile de, GFile para) {
		this.de = de;
		this.para = para;
		if (para.exists()) {
			deletes(para);
		}
		exec(de);
	}

	private void exec(GFile path) {
		
		SystemPrint.ln(path);
		
		Lst<GFile> files = path.getFiles();
		
		for (GFile file : files) {
			GFile s = file.replace(de, para);
			file.copy(s);
		}
		
		Lst<GFile> dirs = path.getDirs();
		
		for (GFile file : dirs) {
			if (ignorar(file)) {
				continue;
			}
			exec(file);
		}
	}

	private boolean ignorar(GFile s) {
		return s.contains("node_modules") || s.contains(".git") || s.contains("/target/") || s.contains(".expo");
	}

	private void deletes(GFile path) {
		Lst<GFile> files = path.getAllFiles();
		for (GFile file : files) {
			if (ignorar(file)) {
				continue;
			}
			file.delete();
		}
	}
	
	private static final GFile reserva = GFile.get("/tmp/reserva");

	public static void reservar(String s) {
		reserva.delete();
		new ReservarExec(GFile.get(s), reserva);
	}
	public static void voltar(String s) {
		new ReservarExec(reserva, GFile.get(s));
	}
	
}
