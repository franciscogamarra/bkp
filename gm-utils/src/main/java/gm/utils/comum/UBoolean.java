package gm.utils.comum;

import src.commom.utils.string.StringEmpty;

public class UBoolean {
	
	private static final Lst<String> TRUES = new Lst<>("true","verdadeiro","sim","yes","y","s","v","1");
	private static final Lst<String> FALSES = new Lst<>("false","falso","nao","não","no","n","0");
	
	private static boolean is(Object o, Boolean b, Lst<String> itens) {

		if (o == null) {
			return false;
		}
		
		if (o instanceof Object[]) {
			Object[] os = (Object[]) o;
			if (os.length == 0) {
				return false;
			}
			o = os[0];
			if (o == null) {
				return false;
			}
		}
		
		if (o instanceof Boolean) {
			return b.equals(o);
		}
		
		String s = o.toString().trim().toLowerCase();
		if (StringEmpty.is(s)) {
			return false;
		}
		
		return itens.contains(s);
		
	}

	public static boolean isTrue(Object o) {
		return is(o, Boolean.TRUE, TRUES);
	}
	
	public static boolean isFalse(Object o) {
		return is(o, Boolean.FALSE, FALSES);
	}
	

	public static Boolean toBoolean(Object o) {
		if (isTrue(o)) {
			return true;
		}
		if (isFalse(o)) {
			return false;
		}
		return null;
	}

	public static boolean eq(Boolean a, Boolean b) {
		return a == null ? b == null : a.equals(b);
	}
	public static boolean ne(Boolean a, Boolean b) {
		return !eq(a, b);
	}
	public static String format(Boolean b) {
		if (b == null) {
			return "-";
		}
		return b ? "Sim" : "nao";
	}

}
