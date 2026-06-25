package br.gerar;

import br.support.SuperObject;
import br.support.comum.LstString;

public class CadastroEmpreendimentoController extends SuperObject {

	public static void exec() {
		
		LstString list = Main.load("/interfaces/CadastroEmpreendimentoController.ts");
		
		int rowInit = list.indexOf("	public init() : void {");
		
		for (Aba aba : Aba.abas) {
			
			String pm = primeiraMaiuscula(aba.nome);
			
			if (!list.anyMatch(s -> s.trim().startsWith("public readonly "+aba.nome+" :"))) {
				list.add(rowInit-1, "	public readonly "+aba.nome+" : "+pm+"Controller = new "+pm+"Controller(this);");
				rowInit++;
			}
			
			if (!list.anyMatch(s -> s.trim().equals("this."+aba.nome+".init();"))) {
				String last = list.filter(s -> s.trim().startsWith("this.") && s.trim().endsWith(".init();")).getLast();
				list.add(list.indexOf(last)+1, "		this."+aba.nome+".init();");
			}
			
			String imp = "import { "+pm+"Controller } from './"+pm+"Objects';";
			
			if (!list.contains(imp)) {
				String last = list.filter(s -> s.trim().startsWith("import ")).getLast();
				list.add(list.indexOf(last)+1, imp);
			}
			
		}
		
		Main.save(list);
		
	}
	
}