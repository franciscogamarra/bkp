package br.mapa;

import gm.utils.comum.Lst;
import gm.utils.comum.SystemPrint;
import gm.utils.files.GFile;

public class ContarLinhasDeCodigo {

	public static void main(String[] args) {
		
		Lst<GFile> files = GFile.get("C:\\desenvolvimento\\projetos\\sisprocer-src\\").getAllFiles().filter(i -> i.isJava());
		
		int rows = 0;
		
		for (GFile file : files) {
			rows += file.load().size();
		}
		
		SystemPrint.ln(rows);
		
	}
	
}
