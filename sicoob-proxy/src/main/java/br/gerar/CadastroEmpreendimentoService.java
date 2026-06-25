package br.gerar;

import br.support.SuperObject;
import br.support.comum.Lst;
import br.support.comum.LstString;

public class CadastroEmpreendimentoService extends SuperObject {

	private static Lst<Campo> getCampos(Aba aba) {
		return aba.campos.filter(campo -> campo.tipo == CampoTipo.select && campo.options == null);
	}
	
	private static void interfacee() {
		
		LstString list = Main.load("/interfaces/CadastroEmpreendimentoService.ts");
		
		for (Aba aba : Aba.abas) {
			
			for (Campo campo : getCampos(aba)) {
				
				String startGet = "get" + primeiraMaiuscula(aba.nome) + primeiraMaiuscula(campo.nome) + "Options(): Promise<";
				String linha = list.findFirst(s -> s.trim().startsWith(startGet));
				String esperado = "\t" + startGet + campo.dto + "[]>;";
				
				if (linha == null) {
					String last = list.filter(s -> s.trim().startsWith("get" + primeiraMaiuscula(aba.nome))).getLast();
					if (last == null) {
						list.add(list.lastIndexOf("}")-1, esperado);
					} else {
						list.add(list.indexOf(last)+1, esperado);
					}
				} else {
					list.replace(linha, esperado);
				}
				
			}
			
		}
		
		Main.save(list);		
		
	}
	
	private static void mock() {
		
		LstString list = Main.load("/cadastro-empreendimento-abas-exemplo/CadastroEmpreendimentoServiceMock.ts");
		
		while (!list.getLast().trim().equals("}")) {
			list.removeLast();
		}
		
		list.removeLast();
		
		for (Aba aba : Aba.abas) {

			for (Campo campo : getCampos(aba)) {

				String startGet = "public get" + primeiraMaiuscula(aba.nome) + primeiraMaiuscula(campo.nome) + "Options(): Promise<";
				String linha = list.findFirst(s -> s.trim().startsWith(startGet));
				
				if (linha == null) {
					String nomeVar = aba.nome + primeiraMaiuscula(campo.nome) + "Options";
					list.add();
					list.add("	private " + nomeVar + ": SimpleOptionDto[] = CadastroEmpreendimentoServiceMock.mockOptions(5, '"+campo.title+"');");
					list.add("		" + startGet + campo.dto + "[]> {");
					list.add("		return PromiseBuilder.ft(() => this."+nomeVar+");");
					list.add("	}");
				}
				
			}	

		}
		
		list.add();
		list.add("}");
		
		Main.save(list);
		
	}
	
	
	public static void exec() {
		interfacee();
		mock();
	}
	
}