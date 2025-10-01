package gm.utils.number;

import java.util.Collections;

import gm.utils.abstrato.Lista;
import gm.utils.exception.UException;
import gm.utils.string.ListString;
import src.commom.utils.integer.IntegerParse;
import src.commom.utils.string.StringEmpty;

public class ListLong extends Lista<Long>{

	private static final long serialVersionUID = 1;

	public ListLong(){}
	public ListLong(Long... args){
		for (Long o : args) {
			add(o);
		}
	}

	public static ListLong byDelimiter(String s, String delimiter) {

		ListLong o = new ListLong();

		ListString list = ListString.byDelimiter(s, delimiter);

		for (String string : list) {
			o.add( IntegerParse.toInt(string).longValue() );
		}

		return o;

	}

	public static ListLong destrincha(String s) {

		if ( !UInteger.isLongInt(s) ) {
			throw UException.runtime("Deve ser 100% numérico: " + s );
		}

		ListLong list = new ListLong();

		while ( !StringEmpty.is(s) ) {
			Integer x = IntegerParse.toInt(s.substring(0,1));
			s = s.substring(1);
			list.add(x.longValue());
		}

		return list;

	}

//	@Override
	public ListLong removeLast(int quantidade) {
		return (ListLong) super.removeLast_(quantidade);
	}

//	@Override
	public ListLong remove(int index, int quantidade) {
		return (ListLong) super.remove_(index, quantidade);
	}

	public void add(int i) {
		add( ULong.toLong(i) );
	}

	public static ListLong array(Long... ints) {
		ListLong list = new ListLong();
		Collections.addAll(list, ints);
		return list;
	}

}
