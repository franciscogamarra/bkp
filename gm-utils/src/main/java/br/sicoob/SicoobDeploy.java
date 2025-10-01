package br.sicoob;

import gm.utils.comum.USystem;
import gm.utils.files.GFile;

public class SicoobDeploy {
	
	public static final GFile PATH = GFile.get("c:/dev/projects/cre-concessao-bndes-web");
	
	public static void main(String[] args) {

		
		if (USystem.hostGamarraWindowsNote()) {
			PATH.delete();
		}
		
//		C:\dev\projects\angular11\cre-concessao-bndes-web\src\app\shared\ utils\Itens.ts

		
		SicoobTranspilar.PATH.getAllFiles().each(file -> file.copyOf(SicoobTranspilar.PATH, PATH));
		
//		GFile JS = GFile.get("C:\\opt\\desen\\gm\\cs2019\\gm-utils\\js\\");
//		
//		GetClasses.all().each(classe -> {
//			String s = classe.getName();
//			s = s.replace(".", SO.barra()) + ".js";
//			GFile js = JS.join(s);
//			if (js.exists()) {
//				js.copyOf(JS, PATH);
//			}
//		});
		
	}
	
}
