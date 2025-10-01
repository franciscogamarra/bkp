package gm.utils.reflection;

import java.util.HashMap;
import java.util.Map;

import gm.utils.anotacoes.Aba;
import gm.utils.anotacoes.DependenciaCom;
import gm.utils.anotacoes.EntidadeVinculada;
import gm.utils.anotacoes.SemDependenciaCom;
import gm.utils.anotacoes.SemDependencias;
import gm.utils.classes.ListClass;
import gm.utils.comum.UCompare;
import gm.utils.jpa.IEntityHierarquica;
import gm.utils.lambda.P1;
import gm.utils.string.ListString;

public class UAtributos {

	public static void ordenaPorTitulo(Atributos as) {
		as.sort((a, b) -> UCompare.compare(a.getTitulo(), b.getTitulo()));
	}

	private static Map<Class<?>, Atributos> persistAndVirtuais = new HashMap<>();
	private static Map<Class<?>, Atributos> persists = new HashMap<>();

	private static Atributos get(Class<?> classe, Map<Class<?>, Atributos> map) {
		Atributos as = map.get(classe);
		if (as == null) {
			as = AtributosBuild.persistAndLookups(classe);
			for (Atributo a : as) {
				if (a.is(IEntityHierarquica.class) && a.eq("pai")) {
					a.setType(classe);
				}
			}
			map.put(classe, as);
		}
		return as.copy();
	}

	public static Atributos getPersists(Class<?> classe) {
		return UAtributos.get(classe, UAtributos.persists);
	}
	public static Atributos getPersistAndVirtuais(Class<?> classe) {
		return UAtributos.get(classe, UAtributos.persistAndVirtuais);
	}

	/* -------------- */

	public static Atributos getDependencias(Atributo a, ListClass classesStatus, ListClass classesEstruturais) {
		return UAtributos.getDependencias(a, AtributosBuild.fks(a.getClasse()), classesStatus, classesEstruturais);
	}
	public static Atributos getDependencias(Atributo a, Atributos atributos, ListClass classesStatus, ListClass classesEstruturais) {
		return UAtributos.getDependencias(a, atributos, classesStatus, classesEstruturais, null);
	}

	public static Atributos getDependencias(Atributo a, Atributos atributos, ListClass classesStatus, ListClass classesEstruturais, P1<Atributos> filter) {

		Atributos dependencias = a.getProp("dependencias");

		if ( dependencias != null ) {
			return dependencias;
		}

		dependencias = new Atributos();
		a.setProp("dependencias", dependencias);

		if (a.isPrimitivo() || a.hasAnnotation(SemDependencias.class) || classesStatus.contains(a.getType()) || a.getType().isAnnotationPresent(EntidadeVinculada.class)) {
			return dependencias;
		}

		SemDependenciaCom sem = a.getAnnotation(SemDependenciaCom.class);
		DependenciaCom com =  a.getAnnotation(DependenciaCom.class);
		Atributos as = AtributosBuild.getPersists(a.getType()).getFks();
		as.remove("problema");
		as.remove("excluido");

		as.removeByType(a.getType());
		classesStatus.forEach(classe -> as.removeByType(classe));
		classesEstruturais.forEach(classe -> as.removeByType(classe));

		atributos = atributos.getFks();
		atributos.removeByType(a.getType());
		classesStatus.forEach(classe -> as.removeByType(classe));
		classesEstruturais.forEach(classe -> as.removeByType(classe));

		if (filter != null) {
			filter.call(as);
		}

		for (Atributo atributo : as) {
			for (Atributo x : atributos) {
				if (!dependencias.contains(x) && (x.is(atributo.getType()) /*&& !x.eq(atributo)*/ && UAtributos.possuiDependencia(com, sem, x))) {
					dependencias.add(x);
					x.setAux(atributo.nome());
				}
			}
		}

		return dependencias;

	}
	private static boolean possuiDependencia(DependenciaCom com, SemDependenciaCom sem, Atributo x) {
		if (sem != null) {
			return UAtributos.possuiDependenciaSem(sem, x);
		}
		if (com != null) {
			return UAtributos.possuiDependenciaCom(com, x);
		} else {
			return true;
		}
	}
	private static boolean possuiDependenciaSem(SemDependenciaCom sem, Atributo x) {
		String value = sem.value();
		if ( value.equalsIgnoreCase(x.nome()) ) {
			return false;
		}
		ListString list = ListString.byDelimiter(value, ",");
		list.trimPlus();
		return !list.containsIgnoreCase(x.nome());
	}
	private static boolean possuiDependenciaCom(DependenciaCom com, Atributo x) {
		String value = com.value();
		if ( value.equalsIgnoreCase(x.nome()) ) {
			return true;
		}
		ListString list = ListString.byDelimiter(value, ",");
		list.trimPlus();
		return list.containsIgnoreCase(x.nome());
	}

	public static String getAba(Atributo a) {

		String value = a.getProp("aba");

		if (value != null) {
			return value;
		}

		if (a.isId()) {
			value = "Sistema";
		} else {

			Aba aba = null;
			Atributos as = AtributosBuild.get(a.getClasse());
			for (Atributo at : as) {
				Aba annotation = at.getAnnotation(Aba.class);
				if (annotation != null) {
					aba = annotation;
				}
				if (at == a) {
					break;
				}
			}

			if (aba == null) {
				value = "Geral";
			} else {
				value = aba.value();
			}

		}

		UAtributos.setAba(a, value);

		return value;
	}

	public static Atributos getWhereAbaGeral(Atributos as) {
		return UAtributos.getWhereAba(as, "Geral");
	}

	public static Atributos getWhereAba(Atributos as, String... nomes) {
		Atributos list = new Atributos();
		list.setId(as.getId());
		for (Atributo a : as) {
			String aba = UAtributos.getAba(a);
			for (String s : nomes) {
				if (aba.equalsIgnoreCase(s)) {
					list.add(a);
					break;
				}
			}
		}
		return list;
	}

	public static void setAba(Atributo a, String nome) {
		a.setProp("aba", nome);
	}

}
