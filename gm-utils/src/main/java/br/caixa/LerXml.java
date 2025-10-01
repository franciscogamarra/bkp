package br.caixa;

import gm.utils.files.GFile;
import gm.utils.string.ListString;
import src.commom.utils.string.StringAfterFirst;
import src.commom.utils.string.StringBeforeFirst;

public class LerXml {

	public static void main(String[] args) {
	
		ListString list = GFile.get("c:/desenvolvimento/20230523/ant/silce/silce/silce-servico-rest/resource/ParametrosMapping.xml").load();
		list.trimPlus();
		list.remove(0);
		list.remove(0);
		list.removeLast();
		list.print();
		
		ListString res = new ListString();
		
		while (!list.isEmpty()) {
			
			String s = list.remove(0);
			
			if (s.startsWith("<funcionalidade nome=")) {
				s = StringAfterFirst.get(s, "\"");
				s = StringBeforeFirst.get(s, "\"");
				res.add("funcionalidade = new Funcionalidade(\""+s+"\");");
				continue;
			}
			
			if (s.startsWith("<habilitado>")) {
				s = StringAfterFirst.get(s, ">");
				s = StringBeforeFirst.get(s, "<");
				res.add("funcionalidade.habilitado = "+s+";");
				continue;
			}
			
			if (s.startsWith("<recursos>")) {
				continue;
			}

			if (s.startsWith("</")) {
				continue;
			}

			if (s.startsWith("<texto nome")) {
				s = StringAfterFirst.get(s, "\"");
				String nome = StringBeforeFirst.get(s, "\"");
				s = StringAfterFirst.get(s, ">");
				s = StringBeforeFirst.get(s, "<");
				res.add("funcionalidade.recursos.texto."+nome+" = "+s+";");
				continue;
			}

			if (s.startsWith("<parametro nome")) {
				s = StringAfterFirst.get(s, "\"");
				String nome = StringBeforeFirst.get(s, "\"");
				s = StringAfterFirst.get(s, ">");
				s = StringBeforeFirst.get(s, "<");
				nome = nome.replace("+", "Mais");
				res.add("funcionalidade.recursos.parametro."+nome+" = "+s+";");
				continue;
			}
			
		}
		
		res.print();
		
	}
	
}
