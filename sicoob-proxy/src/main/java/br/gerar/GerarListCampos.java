package br.gerar;

import br.support.SuperObject;
import br.support.comum.Lst;
import br.support.lambda.F0;
import br.support.strings.StringTrim;

public class GerarListCampos extends SuperObject {

	public static Lst<String> exec(Aba aba) {
		return exec(aba.campos, aba.nome, "this.cadastroEmpreendimentoController.form");
	}
	
	public static Lst<String> exec(Lst<Campo> campos, String servicePrefix, String form) {
		
		Lst<String> list = new Lst<>();
		
		for (Campo campo : campos) {
			
			list.add("");
			list.add("	public readonly "+campo.nome+" : Campo = new Campo(");
			list.add("		"+form+",");
			list.add("		"+toString(campo.nome)+",");
			list.add("		"+toString(campo.title)+",");
			list.add("		'"+campo.tipo+"',");
			list.add("		"+campo.required+",");
			list.add("		"+campo.nullValue+",");
			list.add("	)");
			
			if (campo.rows > 0) {
				list.add(list.removeLast() + ".setRows("+campo.rows+")");
			}
			
			if (campo.tipo == CampoTipo.select) {
				list.add(list.removeLast() + ".setGetOptions(() => {");
				if (campo.options == null) {
					list.add("		return this.cadastroEmpreendimentoController.service.get"+primeiraMaiuscula(servicePrefix)+primeiraMaiuscula(campo.nome)+"Options().then(dtos =>");
					list.add("			["+campo.nullValue+"].concat(dtos.map(dto => SelectOption.buildSimple(dto)))");
					list.add("		);");
				} else {
					list.add("		return PromiseBuilder.ft(() => ["+campo.nullValue+"].concat("+campo.options+"));");
				}
				list.add("	})");
			}
			
			for (F0<String> visivelSe : campo.visivelSe) {
				list.add("	.addVisibleCondition(() => "+visivelSe.call()+")");
			}
			
			if (campo.readOnly) {
				list.add(list.removeLast() + ".setReadOnly()");
			}
			
			if (campo.casasInteiras > -1) {
				list.add(list.removeLast() + ".setCasasInteiras("+campo.casasInteiras+")");
			}
			
			if (campo.casasDecimais > -1) {
				list.add(list.removeLast() + ".setCasasDecimais("+campo.casasDecimais+")");
			}
			
			if (!campo.showTextoErro) {
				list.add(list.removeLast() + ".setShowTextoErro(false)");
			}

			if (campo.bold) {
				list.add(list.removeLast() + ".setBold()");
			}
			
			list.add(list.removeLast() + ";");
			list.add("");
			
		}
		
		return list;
		
	}

	private static String toString(String s) {
		s = StringTrim.plusNull(s);
		return s == null ? "''" : "'"+s+"'";
	}
	
}