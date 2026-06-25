package br.gerar;

import br.support.comum.Lst;
import br.support.strings.StringToConstant;

public class MontarHtml {
	
	public static void exec(Aba aba) {

		Lst<Campo> camposAba = aba.campos.filter(campo -> campo.container != null);
		
		Lst<String> containers = camposAba.map(i -> i.container).distincts();
		
		for (String container : containers) {
			
			Lst<Campo> campos = camposAba.filter(i -> i.container == container);
			
			new ManipularArquivo() {
				
				@Override
				protected void impl() {
					String margem = " ".repeat(regionStart.length() - regionStart.trim().length());
					for (Campo campo : campos) {
						list.add(margem + "<div class=\"col-"+campo.cols+"\" *ngIf=\"principal."+campo.nome+".isVisible()\">");
						list.add(margem + "  <campo [campo]=\"principal."+campo.nome+"\"></campo>");
						list.add(margem + "</div>");
						if (campo.last) {
							list.add(margem + "<div class=\"hr-grid\"></div>");
						}
					}
				}
				
				@Override
				public String getRegionStart() {
					return "<!--"+container+" start-->";
				}
				
				@Override
				public String getRegionEnd() {
					return "<!--"+container+" end-->";
				}
				
				@Override
				public String getFileName() {
					String nome = "aba-"+StringToConstant.exec(aba.nome).toLowerCase().replace("_", "-");
					return "/cadastro-empreendimento-"+nome+"/cadastro-empreendimento-"+nome+".component.html";
				}
				
			}.exec();
			
		}
		
	}
	
}
