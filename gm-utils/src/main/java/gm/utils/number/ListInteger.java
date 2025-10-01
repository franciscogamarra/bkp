package gm.utils.number;

import java.util.Collections;
import java.util.List;
import java.util.Set;

import gm.utils.abstrato.ExtraidId;
import gm.utils.abstrato.ObjetoComId;
import gm.utils.comum.Lst;
import gm.utils.comum.ULog;
import gm.utils.exception.UException;
import gm.utils.string.ListString;
import src.commom.utils.integer.IntegerParse;
import src.commom.utils.string.StringEmpty;

public class ListInteger extends Lst<Integer> {

	private static final long serialVersionUID = 1L;
	
	public static void main(String[] args) {
		ListInteger list = new ListInteger();
		list.add(1);
		list.add(3);
		list.add(7);
		list.add(2);
		list.sort();
		list.print();
	}

	public ListInteger() {
	}

	public ListInteger(Set<Integer> keySet) {
		this();
		addAll(keySet);
	}

	public void inc(int x){
		ListInteger list = copy();
		clear();
		for (Integer o : list) {
			add(o+x);
		}
	}

	public void dec(int x){
		ListInteger list = copy();
		clear();
		for (Integer o : list) {
			add(o-x);
		}
	}

	@Override
	public ListInteger copy() {
		return (ListInteger) super.copy();
	}

	public static ListInteger byDelimiter(String s, String delimiter) {

		ListInteger o = new ListInteger();

		ListString list = ListString.byDelimiter(s, delimiter);

		for (String string : list) {
			o.add( IntegerParse.toInt(string) );
		}

		return o;

	}

	public static ListInteger destrincha(String s) {
		if ( !UInteger.isLongInt(s) ) {
			throw UException.runtime("Deve ser 100% numérico: " + s );
		}

		ListInteger list = new ListInteger();

		while ( !StringEmpty.is(s) ) {
			int x = IntegerParse.toInt(s.substring(0,1));
			s = s.substring(1);
			list.add(x);
		}

		return list;
	}

	public ListInteger(int... is) {
		for (int i : is) {
			add(i);
		}
	}

	public ListInteger(List<Integer> list) {
		for (int i : list) {
			add(i);
		}
	}

	public ListInteger inverter() {
		ListInteger list = new ListInteger();
		for (int i : this) {
			list.add(0, i);
		}
		clear();
		addAll(list);
		return this;
	}

	@Override
	public void print(){
		for (Integer i : this) {
			ULog.debug(i);
		}
	}

	public static ListInteger array(Integer... ints) {
		ListInteger list = new ListInteger();
		Collections.addAll(list, ints);
		return list;
	}
//	public static ListInteger array(int... ints) {
//		ListInteger list = new ListInteger();
//		for (int i : ints) {
//			list.add(i);
//		}
//		return list;
//	}

	public ListLong toLong() {
		ListLong list = new ListLong();
		for (Integer o : this) {
			list.add(o);
		}
		return list;
	}

	public int sum() {
		int x = 0;
		for (Integer o : this) {
			x += o;
		}
		return x;
	}
	public static ListInteger split(String s, String delimitador) {
		ListString split = ListString.split(s, delimitador);
		ListInteger list = new ListInteger();
		for (String string : split) {
			list.add( IntegerParse.toInt(string) );
		}
		return list;
	}
	@Override
	public String toString() {
		String s = super.toString();
		s = s.replace("[", "(");
		return s.replace("]", ")");
	}
	public ListInteger removeFirsts(int count) {
		ListInteger list = new ListInteger();
		for (int i = 0; i < count; i++) {
			list.add( remove(0) );
			if (isEmpty()) {
				break;
			}
		}
		return list;
	}
	public void maisIgual(int i) {
		ListInteger list = copy();
		clear();
		for (Integer x : list) {
			add(x + i);
		}
	}
	public static ListInteger readIds(List<? extends ObjetoComId<?>> list) {
		ListInteger o = new ListInteger();
		for (ObjetoComId<?> e : list) {
			Object value = ExtraidId.exec(e);
			if (value != null) {
				o.add(IntegerParse.toInt(value));
			}
		}
		return o;
	}
	public void remover(int value) {
		Object o = value;
		remove(o);
	}
	public void sort() {
		sort(new ComparatorInteger());
	}
}
