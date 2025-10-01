package js.support;

import com.google.gson.Gson;

import gm.utils.classes.ListClass;
import gm.utils.lambda.F2;
import gm.utils.map.MapSoFromJson;
import gm.utils.reflection.ListMetodos;
import js.annotations.NaoConverter;
import js.annotations.Support;
import src.commom.utils.object.Null;

public class JSON {

//	@Deprecated//usar UString.toString
//	public static String stringify(Object o) {
//		return ToJson.get(o).toString("");
//	}

//	private static int vez = 0;

	public static String stringify(Object o) {
		return stringify(o, null, 0);
	}

	public static String stringify(Object o, F2<String,Object,Object> func) {
		return stringify(o, func, 0);
	}
	
	private static final Gson GSON = new Gson();
	
	public static String stringify(Object o, F2<String,Object,Object> func, int space) {
		
		if (o == null) {
			return "null";
		}
		
		return GSON.toJson(o);

//		if (o instanceof ObjJs) {
//			ObjJs ob = (ObjJs) o;
//			return ob.toJSON();
//		}
//
////		vez++;
////		if (vez == 6) {
////			SystemPrint.ln(vez);
////		}
//
//		if (o instanceof Response) {
//			Response res = (Response) o;
//			if (res.body != null) {
//				return MapSoToJson.get(MapSoFromObject.get(res.body));
//			}
//			return MapSoToJson.get(MapSoFromObject.get(res.data));
//		}
//
//		if (isSupport(o.getClass())) {
//			return MapSoToJson.get(MapSoFromObject.get(o));
//		}
//
//		if (hasToString(o.getClass()) || naoConverter(o.getClass())) {
//			return o.toString();
//		}
//
//		MapSO map = MapSoFromObject.get(o);
////		String s = map.asJson().toString("");
//		String s = MapSoToJson.get(map);
//		if (s.endsWith(",")) {
//			s = StringRight.ignore(s, 1);
//		}
//		s = s.replace("\t", " ");
//		s = s.replace("  ", " ");
//		s = s.replace(",}", "}");
//		s = s.replace(", }", "}");
//		return s.replace("{ ", "{");
//		return ToJson.get(o).toString("");
	}

	private static ListClass classesNaoConverter = new ListClass();
	private static ListClass classesConverter = new ListClass();
/*
	private static boolean naoConverter(Class<?> classe) {

		if (classesNaoConverter.contains(classe)) {
			return true;
		}
		if (classesConverter.contains(classe)) {
			return false;
		}

		if (classe.isAnnotationPresent(NaoConverter.class)) {
			classesNaoConverter.add(classe);
			return true;
		}
		classesConverter.add(classe);
		return false;

	}
/**/
	private static ListClass classesSupport = new ListClass();
	private static ListClass classesNaoSupport = new ListClass();
/*
	private static boolean isSupport(Class<?> classe) {

		if (classesSupport.contains(classe)) {
			return true;
		}
		if (classesNaoSupport.contains(classe)) {
			return false;
		}

		if (classe.isAnnotationPresent(Support.class)) {
			classesSupport.add(classe);
			System.err.print("WARN: A classe " + classe.getSimpleName() + " possui a annotation @Support. Evite usar classes support quando precisar stringfica-las, pois isso causa lentidao no js.");
			return true;
		}
		classesNaoSupport.add(classe);
		return false;

	}
/**/
	private static ListClass classesComToString = new ListClass();
	private static ListClass classesSemToString = new ListClass();
/*
	private static boolean hasToString(Class<?> classe) {

		if (classesComToString.contains(classe)) {
			return true;
		}
		if (classesSemToString.contains(classe)) {
			return false;
		}

		Class<?> c = classe;

		int count = 0;

		while (c != Object.class && c != null) {

			Class<?> cc = c;

			if (!ListMetodos.get(c).filter(o -> o.getName().contentEquals("toString") && o.getDeclaringClasse().getClasse().equals(cc)).isEmpty()) {
				classesComToString.add(classe);
				return true;
			}

			c = c.getSuperclass();

			count++;
			if (count > 100) {
				throw new RuntimeException();
			}

		}

		String s = "A classe " + classe.getSimpleName() + " nao possui o metodo toString() implementado."
				+ "Quando o Js eh compilado a comparacao muitas vezes ocorre pelo toJSON do objeto. Alem disso causa lentidao.";
		classesSemToString.add(classe);
		throw new RuntimeException(s);

	}
/**/
	public static <T> T parse(String s, Class<T> classe) {
		if (Null.is(s)) {
			return null;
		}
		return MapSoFromJson.get(s).as(classe);
	}

}
