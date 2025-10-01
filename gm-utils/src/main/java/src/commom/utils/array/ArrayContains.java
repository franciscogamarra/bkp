package src.commom.utils.array;

import gm.utils.anotacoes.Ignorar;
import gm.utils.lambda.F1;
import js.array.Array;
import src.commom.utils.classe.ClassSimpleName;
import src.commom.utils.object.Equals;
import src.commom.utils.object.Null;
import src.commom.utils.string.StringCompare;

public class ArrayContains {

	/*@IgnoreStart*/
	@SafeVarargs @Ignorar
	public static <T> boolean val(T value, T... values) {
		return val(value, new Array<>(values));
	}
	/*@IgnoreEnd*/

	public static <T> boolean val(T value, Array<T> array) {

		if (array.indexOf(value) > -1) {
			return true;
		}

		if (Null.is(value)) {
			return exists(array, o -> Null.is(o));
		}

		if (ClassSimpleName.is(value, "String")) {
			String v = (String) value;
			ArrayContains.exists(array, o -> StringCompare.eq(v, (String) o));
		}

		return ArrayContains.exists(array, o -> Equals.is(o, value));

	}

	public static <T> boolean exists(Array<T> array, F1<T, Boolean> func) {
		if (ArrayEmpty.is0(array)) {
			return false;
		}
		return !ArrayEmpty.is0(array.filter(o -> func.call(o)));
	}

}
