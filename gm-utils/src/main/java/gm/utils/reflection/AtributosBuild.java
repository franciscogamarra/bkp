package gm.utils.reflection;
import java.lang.reflect.Type;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;

import gm.utils.anotacoes.Lookup;
import gm.utils.classes.UClass;
import gm.utils.comum.UAssert;
import gm.utils.string.ListString;

public class AtributosBuild {

	private static final Map<Class<?>, Atributos> all = new HashMap<>();
	private static final Map<Class<?>, Atributos> persists = new HashMap<>();
	private static final Map<Class<?>, Atributos> lookups = new HashMap<>();
	private static final Map<Class<?>, Atributos> persistAndLookups = new HashMap<>();
	private static final Map<Class<?>, Atributos> orderBy = new HashMap<>();
	private static final Map<Class<?>, Atributos> statics = new HashMap<>();
	private static final Map<Class<?>, Atributos> noStatics = new HashMap<>();
	
	private static final ListString arrayOrderBy = ListString.array("ordem","sequencia","numero","nome","titulo","descricao","enunciado","sigla");
	public static Atributos orderBy(Class<?> classe) {
		UAssert.notEmpty(classe, "classe == null");
		Atributos as = orderBy.get(classe);
		if (as != null) {
			return as;
		}
		Atributos atributos = getPersists(classe, false);
		as = new Atributos();
		for (String string : arrayOrderBy) {
			if (atributos.contains(string)) {
				as.add(atributos.removeAndGet(string));
			}
		}
		as.add(atributos.getId());
		as.addAll(atributos);
		return as;
	}
	public static Atributos persistAndLookups(Class<?> classe) {
		Atributos as = persistAndLookups.get(classe);
		if (as == null) {
			as = get(classe, false);
			Atributos lookups = as.getWhereAnnotation(Lookup.class);
			Atributos persistentes = getPersists(classe, false);

			Atributos as2 = get(classe, false);
			as.clear();
			for (Atributo a : as2) {
				if (lookups.contains(a) || persistentes.contains(a)) {
					as.add(a);
				}
			}
			persistAndLookups.put(classe, as);
		}
		return as.copy();

	}

	public static Atributos lookups(Class<?> classe) {
		UAssert.notEmpty(classe, "classe == null");
		Atributos as = lookups.get(classe);
		if (as == null) {
			as = get(classe, false).getWhereAnnotation(Lookup.class);
			lookups.put(classe, as);
		}
		return as.copy();
	}

	public static Atributos getPersists(Object o) {return getPersists(o.getClass());}
	public static Atributos getPersists(Object o, boolean withId) {return getPersists(o.getClass(), withId);}
	public static Atributos getPersists(Class<?> classe) {return getPersists(classe, false);}
	public static Atributos getPersists(Class<?> classe, boolean withId) {
		UAssert.notEmpty(classe, "classe == null");
		classe = UClass.getClass(classe);
		Atributos as = persists.get(classe);
		if (as == null) {
			as = get(classe, false);
			as.removeNaoPersistiveis();
			persists.put(classe, as);
//			if (as.existe("pagina")) {
//				throw UException.runtime( "N\u00e3o pode haver um atributo chamado pagina pois este \u00e9 um nome reservado: " + classe.getSimpleName() );
//			}
		}
		Atributos copy = as.copy();
		if (withId) {
			Atributo id = as.getId();
			if (id != null) {
				copy.add(id);
			}
		}
		return copy;
	}
	
	public static Atributos fks(Class<?> classe) {
		return getPersists(classe, false).getFks();
	}

	public static Atributos get(Object o) {
		return get(UClass.getClass(o), false);
	}

	public static Atributos get(Type type) {
		Class<?> classe = (Class<?>) type;
		if (classe == java.lang.System.class) {
			throw new RuntimeException();
		}
		return get(classe);
	}
	
	public static Atributos get(Class<?> classe) {
		return get(classe, false);
	}

	public static Atributos get(Object o, boolean withId) {
		return get(UClass.getClass(o), withId);
	}

	public static Atributos get(Class<?> classe, boolean withId) {

		classe = UClass.getClass(classe);
		Atributos as = all.get(classe);
		if (as == null) {
			as = getSynchronized(classe);
		}
		Atributos copy = as.copy();
		if (withId) {
			Atributo id = as.getId();
			if (id != null) {
				copy.add(id);
			}
		}
		return copy;
	}
	
	public static final Map<Class<?>, Comparator<? super Atributo>> defaultSorts = new HashMap<>();

	private synchronized static Atributos getSynchronized(Class<?> classe) {
		Atributos as = all.get(classe);
		if (as == null) {
			as = new Atributos(classe);
			as.getId();
//			as.sortByName();
			
			Comparator<? super Atributo> comparator = defaultSorts.get(classe);
			
			if (comparator != null) {
				as.sort(comparator);
			}
			
			all.put(classe, as);
			
		}
		return as;
	}
	public static Atributo getId(Class<?> classe) {
		return get(classe, false).getId();
	}
	
	
//	public static Lst<Atributo> get(Class<?> classe) {
//		Lst<Atributo> lst = all.get(classe);
//		if (lst == null) {
//			lst = getFields(classe).map(i -> new Atributo(i));
//			lst.sort((a,b) -> StringCompare.compare(a.getName(), b.getName()));
//			Atributo id = lst.unique(i -> StringCompare.eq(i.getName(), "id"));
//			if (id != null) {
//				lst.remove(id);
//				lst.add(0, id);
//			}
//			all.put(classe, lst);
//		}
//		return lst.clone();
//	}
	
	public static Atributos getStatics(Class<?> classe) {
		Atributos lst = statics.get(classe);
		if (lst == null) {
			lst = get(classe);
			lst.removeIf(i -> !i.isStatic());
			statics.put(classe, lst);
		}
		return lst.clone();
	}
	
	public static Atributos getNoStatics(Class<?> classe) {
		Atributos lst = noStatics.get(classe);
		if (lst == null) {
			lst = get(classe);
			lst.removeIf(i -> i.isStatic());
			noStatics.put(classe, lst);
		}
		return lst.clone();
	}
	
//	public static Lst<Atributo> getPersists(Class<?> classe) {
//		Lst<Atributo> lst = persists.get(classe);
//		if (lst == null) {
//			lst = getNoStatics(classe);
//			lst.removeIf(i -> i.isAnnotationPresent(Transient.class));
//			persists.put(classe, lst);
//		}
//		return lst.clone();
//	}
	
//	public static Atributo getId(Class<?> classe) {
//		Lst<Atributo> as = get(classe);
//		Atributo o = as.unique(a -> a.isAnnotationPresent(Id.class));
//		if (o == null) {
//			o = as.unique(a -> StringCompare.eq(a.getName(), "id"));
//		}
//		return o;
//	}	

}
