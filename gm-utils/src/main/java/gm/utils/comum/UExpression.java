package gm.utils.comum;

import java.util.Map;
import java.util.Set;

import gm.utils.classes.UClass;
import gm.utils.reflection.Atributo;
import gm.utils.reflection.Atributos;
import gm.utils.reflection.AtributosBuild;
import gm.utils.reflection.ListMetodos;
import gm.utils.reflection.Metodo;
import gm.utils.reflection.Metodos;
import gm.utils.string.ListString;
import src.commom.utils.string.StringAfterFirst;
import src.commom.utils.string.StringBeforeFirst;
import src.commom.utils.string.StringBeforeLast;
import src.commom.utils.string.StringParse;

public class UExpression {

	public static String getString(String field, Object o) {
		return StringParse.get(getValue(field, o));
	}

	public static Object getValue(String expression, Object o) {

		if (o instanceof Map) {
			@SuppressWarnings("unchecked")
			Map<String, Object> map = (Map<String, Object>) o;
			Set<String> keySet = map.keySet();
			expression = expression.toLowerCase();
			for (String key : keySet) {
				if (key.toLowerCase().equals(expression)) {
					return map.get(key);
				}
			}
			return null;
		}

		Class<? extends Object> classe = o.getClass();
		Atributos as = AtributosBuild.get(classe);
		if (expression.contains(".")) {
			ListString fields = ListString.byDelimiter(expression, ".");
			expression = fields.remove(0);
			if (expression.endsWith("()")) {
				expression = StringBeforeLast.get(expression, "()");
				return ListMetodos.get(classe).get(expression).invoke(o);
			}
			Atributo a = as.getObrig(expression);
			o = a.get(o);
			// o = getValue(getField(field, o.getClass()), o);
			if (o == null) {
				return null;
			}
			expression = fields.toString(".");
			return getValue(expression, o);
		}
		if (expression.endsWith("()")) {
			expression = StringBeforeLast.get(expression, "()");
			return ListMetodos.get(classe).get(expression).invoke(o);
		}
		Atributo a = as.get(expression);
		if (a != null) {
			return a.get(o);
		}
		Metodos metodos = ListMetodos.get(classe);
		Metodo metodo = metodos.get("get" + expression);
		if (metodo != null) {
			return metodo.invoke(o);
		}
		return o;
	}

	public static void setValue(Object o, String field, Object value) {
		AtributosBuild.get( UClass.getClass(o) ).getObrig(field).set(o, value);
	}

	public static Atributo getAtributo(Class<?> classeOrigem, String expression) {
		Atributos as = AtributosBuild.get(classeOrigem);
		if (expression.contains(".")) {
			Atributo a = as.getObrig( StringBeforeFirst.get(expression, ".") );
			expression = StringAfterFirst.get(expression, ".");
			return getAtributo(a.getType(), expression);
		}
		return as.getObrig(expression);
	}

	public static String toJavaExpression(String s) {
		return s;
	}

}
