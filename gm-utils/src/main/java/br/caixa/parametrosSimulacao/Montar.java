package br.caixa.parametrosSimulacao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import br.caixa.parametrosSimulacao.dtos.Dto;
import br.caixa.parametrosSimulacao.pgInicial.PgInicialDto;
import gm.utils.comum.Lst;
import gm.utils.comum.UType;
import gm.utils.javaCreate.JcAtributo;
import gm.utils.javaCreate.JcClasse;
import gm.utils.javaCreate.JcTipo;
import gm.utils.map.MapSO;
import gm.utils.map.MapSoFromJson;
import gm.utils.string.ListString;
import src.commom.utils.doubles.DoubleIs;
import src.commom.utils.idioma.TextoSingularOuPlural;
import src.commom.utils.integer.IntegerIs;
import src.commom.utils.string.StringEmpty;
import src.commom.utils.string.StringPrimeiraMaiuscula;
import src.commom.utils.string.StringRemoveAcentos;

public class Montar {
	
	public static void main(String[] args) {
		ListString list = new ListString();
		list.load("C:\\desenvolvimento\\opt\\desen\\gm\\cs2019\\gm-utils\\src\\main\\java\\br\\caixa\\parametrosSimulacao\\mock.json");
		MapSO map = MapSoFromJson.get(list.toString(""));
//		exec("principal", map, ParametrosSimulacaoPrincipalDto.class);
//		exec("principal", map, ConsultaComboPrincipalDto.class);
//		exec("principal", map, ConfiguracoesWebPrincipalDto.class);
		exec("principal", map, PgInicialDto.class);
	}
	
	private static Map<String, JcClasse> classes = new HashMap<>();
	
	private static JcClasse exec(final String nomee, MapSO map, Class<?> classePrincipal) {

		String nomeClass = StringPrimeiraMaiuscula.exec(nomee);
		
		if (nomeClass.contentEquals("Equipe1") || nomeClass.contentEquals("Equipe2")) {
			nomeClass = "Equipe";
		} else if (nomeClass.contentEquals("Payload")) {
			nomeClass = "ParametrosSimulacao";
		} else if (nomeClass.contentEquals("Principal")) {
			nomeClass = "ParametrosSimulacaoPrincipal";
		}
		
		nomeClass += "Dto";

		JcClasse jc;
		
		JcClasse jcCache = classes.get(nomeClass);

		if (jcCache != null) {
			jc = jcCache;
		} else {
			jc = JcClasse.build(classePrincipal.getPackage(), nomeClass);
			jc.extends_(Dto.class);
		}
		
		map.forEach((k,v) -> {
			
//			String ss = nomee + "." + k;
//			
//			if (ss.contentEquals("proximoConcurso.teimosinhas")) {
//				SystemPrint.ln(ss);
//			}
			
			boolean atributoJaExiste = jc.getAtributos().anyMatch(i -> i.getNome().contentEquals(k));
			
			if (UType.isPrimitiva(v)) {
				
				if (!atributoJaExiste) {
					jc.atributo(k, getTipoPrimitivo(k, v)).public_();
				}
				
			} else if (v instanceof MapSO) {
				JcClasse jc2 = exec(k, (MapSO) v, classePrincipal);
				if (!atributoJaExiste) {
					jc.atributo(k, jc2).public_();
				}
			} else if (v instanceof List) {
				
				List<?> list = (List<?>) v;
				
				if (list.isEmpty()) {
					if (!atributoJaExiste) {
						jc.atributo(k, Lst.class, Object.class).public_();
					}
				} else {
					
					for (Object o : list) {

						if (UType.isPrimitiva(o)) {
							
							Class<?> tipo = getTipoPrimitivo(k, o);
							
							if (atributoJaExiste) {
								JcAtributo a = jc.getAtributos().unique(i -> i.getNome().contentEquals(k));
								if (a.getTipo().getGenerics().get(0).eq(Object.class)) {
									JcTipo novoTipo = JcTipo.build(Lst.class);
									novoTipo.addGenerics(tipo);
									a.setTipo(novoTipo);
								} else if (a.getTipo().getGenerics().get(0).eq(Integer.class) && tipo == Double.class) {
									JcTipo novoTipo = JcTipo.build(Lst.class);
									novoTipo.addGenerics(tipo);
									a.setTipo(novoTipo);
								}

							} else {
								jc.atributo(k, Lst.class, tipo).public_();
							}
							
						} else {
							JcClasse exec = exec(StringRemoveAcentos.exec(TextoSingularOuPlural.getSingular(k)), (MapSO) o, classePrincipal);
							if (!atributoJaExiste) {
								jc.atributo(k, Lst.class, exec).public_();
							}
						}
						
						atributoJaExiste = true;
						
					}
					
				}
				
			} else {
				
			}
			
		});
		
		jc.save();
		
		classes.put(nomeClass, jc);
		
		return jc;
		
	}

	public static Class<?> getTipoPrimitivo(String nomeCampo, Object v) {
		
		if (v == null) {
			return String.class;
		}
		
		String s = (String) v;
		
		if (StringEmpty.is(s)) {
			return String.class;
		}
		
		if (IntegerIs.is(s)) {
			return Integer.class;
		}
		
		if (s.contentEquals("true") || s.contentEquals("false")) {
			return Boolean.class;
		}
		
		if (DoubleIs.is(v)) {
			return Double.class;
		}
		
		return String.class;
		
	}
	
}
