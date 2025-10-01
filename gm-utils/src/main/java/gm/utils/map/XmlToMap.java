package gm.utils.map;

import gm.utils.comum.Lst;
import gm.utils.exception.NaoImplementadoException;
import gm.utils.string.ListString;
import src.commom.utils.string.StringAfterFirst;
import src.commom.utils.string.StringBeforeFirst;
import src.commom.utils.string.StringRight;

public class XmlToMap {

	public static void main(String[] args) {

		ListString list = new ListString();
		list.load("/opt/desen/cooperforte/mobile/pom.xml");
		list.trimPlus();
		list.removeIfStartsWith("<!--");

		ListString list2 = new ListString();

		for (String s : list) {

			if (!s.startsWith("<") || s.startsWith("</") || !s.contains("</")) {
				list2.add(s);
				continue;
			}

			String tag = StringAfterFirst.get(s, "</");

			if (!s.startsWith("<"+tag)) {
				throw new NaoImplementadoException();
			}

			s = StringAfterFirst.get(s, "<" + tag);
			s = StringBeforeFirst.get(s, "</" + tag);

			list2.add("<"+tag);
			list2.add(s);
			list2.add("</"+tag);

		}

		MapSO map = exec(list2);

//		map.asXml

		map.asJson().print();
//		map.print();

	}

	private static MapSO exec(ListString list) {

		String open = list.remove(0);

		MapSO o = new MapSO();

		String tagFull = StringRight.ignore1(open.substring(1));

		o.put("a_tag", tagFull);

		String tag = StringBeforeFirst.get(tagFull + " ", " ");
		String close = "</" + tag + ">";

		if (list.get(0).startsWith("<")) {

			Lst<MapSO> itens = new Lst<>();

			while (!list.get(0).contentEquals(close)) {
				itens.add(exec(list));
			}

			o.put("b_itens", itens);

		} else {
			o.put("b_value", list.remove(0));
		}

		if (!list.get(0).contentEquals(close)) {
			throw new RuntimeException();
		}

		list.remove(0);

		return o;

	}

}
