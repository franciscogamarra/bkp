package src.commom.utils.array;

import gm.utils.lambda.F2;
import js.array.Array;
import src.commom.utils.integer.IntegerCompare;
import src.commom.utils.object.Equals;
import src.commom.utils.object.Null;

public class ArrayEquals {

	public static boolean size(Itens<?> a, Itens<?> b) {
		return IntegerCompare.eq(ArrayLength.get(a), ArrayLength.get(b));
	}

	public static boolean size0(Array<?> a, Array<?> b) {
		return IntegerCompare.eq(ArrayLength.get0(a), ArrayLength.get0(b));
	}

	public static boolean is0(Itens<?> a, Itens<?> b) {
		return is(a, b, (x,y) -> Equals.is(x, y));
	}

	public static boolean is(Itens<?> a, Itens<?> b, F2<Object,Object,Boolean> comparator) {
		if (!size(a, b)) {
			return false;
		}
		if (ArrayEmpty.is(a)) {
			return ArrayEmpty.is(b);
		} else if (ArrayEmpty.is(b)) {
			return false;
		} else {
			for (int i = 0; i < a.size(); i++) {
				if (!comparator.call(a.get(i), b.get(i))) {
					return false;
				}
			}
			return true;
		}
	}

	public static <T> boolean isT(Itens<T> a, Itens<T> b, F2<T,T,Boolean> comparator) {
		if (!size(a, b)) {
			return false;
		}
		if (ArrayEmpty.is(a)) {
			return ArrayEmpty.is(b);
		} else if (ArrayEmpty.is(b)) {
			return false;
		} else {
			for (int i = 0; i < a.size(); i++) {
				if (!comparator.call(a.get(i), b.get(i))) {
					return false;
				}
			}
			return true;
		}
	}

	public static <T> boolean isT0(Array<T> a, Array<T> b, F2<T,T,Boolean> comparator) {
		if (!size0(a, b)) {
			return false;
		}
		if (ArrayEmpty.is0(a)) {
			return ArrayEmpty.is0(b);
		} else if (ArrayEmpty.is0(b)) {
			return false;
		} else {
			for (int i = 0; i < a.lengthArray(); i++) {
				if (!comparator.call(a.array(i), b.array(i))) {
					return false;
				}
			}
			return true;
		}
	}

	public static boolean equalsNotification(Itens<?> a, Itens<?> b) {

		if (Null.is(a)) {
			return Null.is(b);
		}
		if (Null.is(b) || (a.size() != b.size())) {
			return false;
		}

		return is(a, b, (x,y) -> {
			if (x instanceof Itens) {
				Itens<?> xx = (Itens<?>) x;
				Itens<?> yy = (Itens<?>) y;
				return equalsNotification(xx, yy);
			}
			return a == b;
		});

	}

}
