package gm.utils.comum;

import java.util.Comparator;

import gm.utils.abstrato.IdCompare;
import gm.utils.abstrato.IdTextObject;
import gm.utils.abstrato.ObjetoComId;
import gm.utils.date.Data;
import gm.utils.reflection.Atributo;
import src.commom.utils.integer.IntegerCompare;
import src.commom.utils.longo.LongCompare;
import src.commom.utils.string.StringCompare;
import src.commom.utils.string.StringParse;

public class UCompare {

	private static class Instance implements Comparator<Object>{
		@Override
		public int compare(Object a, Object b) {
			return UCompare.compare(a, b);
		}
	}

	public static Instance instance = new Instance();

	public static boolean basicCompare0(Object a, Object b) {
		Integer x = basicCompare(a, b);
		return x != null && x == 0;
	}

	public static Integer basicCompare(Object a, Object b) {
		if (a == b) {
			return 0;
		}
		if (a == null) {
			if (b == null) {
				return 0;
			}
			return -1;
		}
		if (b == null) {
			return 1;
		}
		return null;
	}

	public static int compare(Object a, Object b, Atributo atributo) {
		Integer i = basicCompare(a, b);
		if (i != null) {
			return i;
		}
		a = atributo.get(a);
		b = atributo.get(b);
		return compare(a, b);
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static int compare(Object a, Object b) {

		Integer i = basicCompare(a, b);
		if (i != null) {
			return i;
		}

		if (a instanceof String) {
			String sa = (String) a;
			String sb = (String) b;
			return StringCompare.compare(sa, sb);
		}

		if (a instanceof Integer) {
			Integer sa = (Integer) a;
			Integer sb = (Integer) b;
			return IntegerCompare.compare(sa, sb);
		}

		if (a instanceof Long) {
			Long sa = (Long) a;
			Long sb = (Long) b;
			return LongCompare.compare(sa, sb);
		}

		if ((a instanceof Comparable) && (b instanceof Comparable)) {
			Comparable va = (Comparable) a;
			Comparable vb = (Comparable) b;
			return va.compareTo(vb);
		}

		if (a instanceof IdTextObject) {
			IdTextObject va = (IdTextObject) a;
			IdTextObject vb = (IdTextObject) b;
			String sa = va.getText();
			String sb = vb.getText();
			return StringCompare.compare(sa, sb);
		}

		if (a instanceof ObjetoComId) {
			return IdCompare.compare(a, b);
		}

		if (UType.isData(a) || UType.isData(b)) {
			return Data.to(a).compare(Data.to(b));
		}
		
		if (a.equals(b)) {
			return 0;
		}

		String sa = StringParse.get(a);
		String sb = StringParse.get(b);
		return StringCompare.compare(sa, sb);

	}

	public static boolean equals(Object a, Object b) {
		return UCompare.compare(a, b) == 0;
	}
	public static boolean eq(Object a, Object b) {
		return equals(a,b);
	}
	public static boolean ne(Object a, Object b) {
		return !equals(a,b);
	}

}
