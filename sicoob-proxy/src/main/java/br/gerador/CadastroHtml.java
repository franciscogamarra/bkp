package br.gerador;

import br.definicoes.Campo;
import br.definicoes.Model;
import br.definicoes.Sistema;
import br.support.comum.Lst;
import br.support.comum.LstString;
import br.support.comum.Print;

public class CadastroHtml {

	private static void exec(Model model) {
		
		LstString list = new LstString();
		
		list.add("<div class=\"ss-grid\">");
		
		Lst<Campo> campos = model.getCampos();
		campos.removeIf(campo -> campo.front.edit.getCols() == 0);
		
		for (Campo campo : campos) {
			list.add();
			list.add("	<div class=\"col-"+campo.front.edit.getCols()+"\">");
			list.add("		<campo [campo]=\"campos."+campo.getNome()+"\"></campo>");
			list.add("	</div>");
		}		

		list.add();
		list.add("</div>");
		
		Print.ln("== "+model.getNome()+" ==");
		
		list.print();
		
	}

	public static void exec(Sistema sistema) {
		sistema.getModels().forEach(model -> exec(model));;
	}
	
}