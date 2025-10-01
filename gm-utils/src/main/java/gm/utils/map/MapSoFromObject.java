package gm.utils.map;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import gm.utils.classes.UClass;
import gm.utils.comum.UType;
import gm.utils.number.Numeric;
import gm.utils.number.UBigDecimal;
import gm.utils.reflection.Atributo;
import gm.utils.reflection.Metodo;
import gm.utils.rest.GetMapStringString;
import src.commom.utils.string.StringParse;
import src.commom.utils.string.StringPrimeiraMinuscula;

public class MapSoFromObject {

	private final List<Object> jaComputados = new ArrayList<>();
	private final MapSO result;
	private final Map<Object, MapSO> memory = new HashMap<>();
	private final boolean elegante;

	private MapSoFromObject(Object o, boolean elegante) {

		this.elegante = elegante;
		if (UType.isArray(o) || o instanceof IToList || o instanceof List) {
			result = new MapSO();
			trataList(result, "array", o);
		} else {
			result = exec(o);
		}

	}

	public static MapSO get(Object o) {
		return new MapSoFromObject(o, false).result;
	}

	public static MapSO getElegando(Object o) {
		return new MapSoFromObject(o, true).result;
	}

	private static MapSO getFromMap(Map<?, ?> map) {
		MapSO mapSO = new MapSO();
		for (Object key : map.keySet()) {
			mapSO.add(StringParse.get(key), map.get(key));
		}
		return mapSO;
	}

	private MapSO exec(Object o) {

		if ((o == null) || o.getClass().getName().contains("$Lambda$")) {
			return null;
		}

		if ((o instanceof Numeric) || o.getClass().getSimpleName().startsWith("Numeric")) {
			throw new RuntimeException();
		}

		MapSO v = memory.get(o);
		if (v != null) {
			return v;
		}

		if (jaComputados.contains(o)) {
			return null;
		}

		jaComputados.add(o);

		MapSO x = new MapSO();
		x.setClasseOrigem(o.getClass());
		memory.put(o, x);
		MapSO y = exec2(o);
		y.setClasseOrigem(o.getClass());
		x.add(y);
		return x;
	}

	private MapSO exec2(Object o) {

		if (o instanceof MapSO) {
			return (MapSO) o;
		}
		if (o instanceof Map) {
			return MapSoFromObject.getFromMap((Map<?, ?>) o);
		}
		if (o instanceof GetMapStringString) {
			return MapSoFromObject.getFromMap(((GetMapStringString) o).getMapStringString());
		}

		Gets gets = Gets.get(UClass.getClass(o));
		MapSO map = new MapSO();

		for (Atributo a : gets.as) {
			Object value = a.get(o);
			add(o, map, value, a.nome());
		}

		for (Metodo m : gets.mts) {

			try {
				Object value = m.invoke(o);
				add(o, map, value, StringPrimeiraMinuscula.exec(m.getName().substring(3)));
			} catch (Exception e) {
				throw e;
			}

		}

		return map;

	}

	private void add(Object o, MapSO map, Object value, String nome) {

		if (value == null) {
			map.add(nome, null);
		} else if (value instanceof byte[]) {
			map.add(nome, value);
		} else if (value instanceof List || UType.isArray(value) || value instanceof IToList) {
			trataList(map, nome, value);
		} else if (UType.isPrimitiva(value)) {
			map.add(nome, value);
		} else if (value instanceof Numeric) {
			Numeric<?> n = (Numeric<?>) value;

			if (elegante) {
				map.add(nome, n.toString());
			} else {
				map.add(nome, n.toDouble());
			}

		} else if (value.getClass().getSimpleName().startsWith("Numeric")) {
			String s = value.toString().replace(".", "").replace(",", ".");
			BigDecimal v = UBigDecimal.toBigDecimal(s);

			if (elegante) {
				map.add(nome, Numeric.toNumeric(v).toString());
			} else {
				map.add(nome, v.doubleValue());
			}

		} else {
			map.add(nome, exec(value));
		}

	}

	private void trataList(MapSO map, String nome, Object value) {

		List<?> list;

		if (UType.isArray(value)) {
			try {
				Object[] os = (Object[]) value;
				list = Arrays.asList(os);
			} catch (Exception e) {
				char[] os = (char[]) value;
				list = Arrays.asList(os);
			}
		} else if (value instanceof IToList) {
			IToList toList = (IToList) value;
			list = toList.toList();
		} else {
			list = (List<?>) value;
		}

		if (list.isEmpty() || UType.isPrimitiva(list.get(0))) {
			map.add(nome, list);
		} else {
			List<MapSO> lst = new ArrayList<>();
			for (Object obj : list) {
				if (obj != null) {
					lst.add(exec(obj));
				}
			}
			map.add(nome, lst);
		}

	}

}
