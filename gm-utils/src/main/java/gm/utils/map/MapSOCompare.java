package gm.utils.map;

import java.util.List;

import gm.utils.comum.Lst;
import gm.utils.comum.UList;
import gm.utils.number.UDouble;
import src.commom.utils.array.Itens;
import src.commom.utils.object.Equals;
import src.commom.utils.string.StringCompare;
import src.commom.utils.string.StringExtraiCaracteres;
import src.commom.utils.string.StringParse;

public class MapSOCompare {

	private static final Itens<String> DOUBLE_CHARS = Itens.build("0","1","2","3","4","5","6","7","8","9",".");

	public static MapSO get(MapSO a, MapSO b) {
		return get(a, b, 9);
	}

	public static MapSO get(MapSO a, MapSO b, int limiteDecimal) {

		MapSO main = new MapSO();
		exec("", main, a, b, limiteDecimal);

		if (main.isEmpty()) {
			return null;
		}
		return main;

	}

	private static void exec(String mainKey, MapSO main, MapSO a, MapSO b, int limiteDecimal) {

		if (Equals.is(a, b) || StringCompare.eq(a.toString(), b.toString())) {
			return;
		}

		Lst<String> keys = UList.distinct(a.getKeys().union(b.getKeys()));

		for (String key : keys) {

			Object va = a.get(key);
			Object vb = b.get(key);

			if (Equals.is(va, vb)) {
				continue;
			}

			if (va == null || vb == null) {
				MapSO map = new MapSO();
				map.put("a", va);
				map.put("b", vb);
				main.add(mainKey + key, map);
				continue;
			}

			if (va.equals(vb)) {
				continue;
			}

			if (va instanceof MapSO && vb instanceof MapSO) {
				exec(mainKey + key + ".", main, (MapSO) va, (MapSO) vb, limiteDecimal);
				continue;
			}

			String sa = StringParse.get(va);
			String sb = StringParse.get(vb);

			if (StringCompare.eq(sa, sb)) {
				continue;
			}

			if (va instanceof List && vb instanceof List) {
				List<?> la = (List<?>) va;
				List<?> lb = (List<?>) vb;

				if (la.size() != lb.size()) {
					MapSO map = new MapSO();
					map.put("a", "size " + la.size());
					map.put("b", "size " + lb.size());
					main.add(mainKey + key, map);
				} else {
					for (int i = 0; i < la.size(); i++) {
						va = la.get(0);
						vb = lb.get(0);
						MapSO ma = new MapSO().add("v", va);
						MapSO mb = new MapSO().add("v", vb);
						exec(mainKey + key + "["+i+"].", main, ma, mb, limiteDecimal);
					}
				}

				continue;

			}

			if (isDouble(sa) && isDouble(sb)) {
				double da = UDouble.toDouble(sa, limiteDecimal);
				double db = UDouble.toDouble(sb, limiteDecimal);
				if (da == db) {
					continue;
				}
			}

			MapSO map = new MapSO();
			map.put("a", va);
			map.put("b", vb);
			main.add(mainKey + key, map);

		}

	}

	private static boolean isDouble(String s) {
		return StringExtraiCaracteres.exec(s, DOUBLE_CHARS).contentEquals(s);
	}

}
