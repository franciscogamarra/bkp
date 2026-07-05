package br.gerador;

import br.support.comum.Print;
import br.support.dev.Cmd;

public class SicoobRuralCommit {
	
	public static void main(String[] args) {
		new Cmd().cd("c:/dev/myprojects/gm-support").push().exec();
		new Cmd().cd("c:/dev/myprojects/gerador").push().exec();
		
		Cmd cmd = new Cmd().cd("c:/dev/rural/cre-concessao-rural-web");
		cmd.push(cmd.getBranch(), "feature/migracao_sisbr3").exec();
		
		Print.blocoVerde("concluído com sucesso");
//		C:\dev\rural\cre-concessao-rural-web\src\app\core\core.module.ts
	}
	
}
