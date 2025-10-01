package gm.utils.map;

import java.io.File;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import gm.utils.anotacoes.IgnoreJson;
import gm.utils.classes.UClass;
import gm.utils.comum.Lst;
import gm.utils.comum.SystemPrint;
import gm.utils.comum.UBoolean;
import gm.utils.comum.UList;
import gm.utils.comum.UObject;
import gm.utils.comum.UType;
import gm.utils.date.Data;
import gm.utils.exception.NaoImplementadoException;
import gm.utils.exception.UException;
import gm.utils.files.GFile;
import gm.utils.lambda.F1;
import gm.utils.lambda.F2;
import gm.utils.number.ListInteger;
import gm.utils.number.Numeric15;
import gm.utils.number.Numeric2;
import gm.utils.number.Numeric3;
import gm.utils.number.Numeric4;
import gm.utils.number.Numeric5;
import gm.utils.number.Numeric8;
import gm.utils.number.UBigDecimal;
import gm.utils.number.UDouble;
import gm.utils.number.ULong;
import gm.utils.reflection.Atributo;
import gm.utils.reflection.Atributos;
import gm.utils.reflection.AtributosBuild;
import gm.utils.reflection.ListMetodos;
import gm.utils.reflection.Metodo;
import gm.utils.reflection.Metodos;
import gm.utils.reflection.Parametro;
import gm.utils.string.ListString;
import js.array.Array;
import lombok.Setter;
import src.commom.utils.integer.IntegerParse;
import src.commom.utils.string.StringAfterFirst;
import src.commom.utils.string.StringBeforeFirst;
import src.commom.utils.string.StringContains;
import src.commom.utils.string.StringEmpty;
import src.commom.utils.string.StringParse;
import src.commom.utils.string.StringRight;
import src.commom.utils.string.StringToCamelCaseSepare;
import src.commom.utils.string.StringTrim;

public class MapSO extends LinkedHashMap<String, Object> {

	private static final long serialVersionUID = 1L;

	private static int count = Integer.MIN_VALUE;
	private int uniqueId = count++;

	@IgnoreJson
	private MapSO pai;

	@IgnoreJson @Setter
	private Class<?> classeOrigem;

	public Class<?> getClasseOrigem() {
		return classeOrigem == null ? MapSO.class : classeOrigem;
	}

	public MapSO() {}

	public MapSO(Map<String, Object> map) {
		this.add(map);
	}
	public static <T> T get(MapSO map, String... keys){
		for (int i = 0; i < keys.length-1; i++) {
			String key = keys[i];
			Map<String, Object> mp = map.get(key);
			map = MapSO.toMapSO(mp);
		}
		String key = keys[keys.length-1];
		return map.get(key);
	}
	public <T> T get(String... keys){
		return MapSO.get(this, keys);
	}
	public static MapSO toMapSO(Map<String, Object> map) {
		if (map instanceof MapSO) {
			return (MapSO) map;
		}
		return new MapSO(map);
	}
	public ListInteger getInts(String... keys){
		Object o = this.get(keys);
		if (o == null) {
			return null;
		}
		if (o instanceof ListInteger) {
			return (ListInteger) o;
		}
		if (o instanceof Object[]) {
			Object[] os = this.get(keys);
			ListInteger list = new ListInteger();
			for (Object i : os) {
				list.add(IntegerParse.toInt(i));
			}
			return list;
		}
		throw UException.runtime("Não foi possível converver o objeto para um ListInteger: " + o.getClass().getSimpleName());
	}
	public Integer getInt(String key){
		return IntegerParse.toInt(this.get(key));
	}
	public Long getLong(String key){
		return ULong.toLong(this.get(key));
	}
	public long getLongObrig(String key) {
		return ULong.toLong(this.getObrig(key));
	}
	public Integer getInt(String key, Integer def){
		return IntegerParse.toIntDef(this.get(key), def);
	}
	public String getString(String key){
		return StringParse.get(this.get(key));
	}
	public String getStringTrim(String key){
		return StringTrim.plusNull(getString(key));
	}

	@SuppressWarnings("unchecked")
	public <T> T get(String key){
		Object o = super.get(key);
		if (o == null) {
			Set<String> keySet = keySet();
			for (String s : keySet) {
				if (s.equalsIgnoreCase(key)) {
					o = super.get(s);
					break;
				}
			}
		}
		return (T) o;
	}
	public <T> T getObrig(String key){
		T o = this.get(key);
		if (o == null) {
			throw UException.runtime("key não encontrado: " + key);
		}
		return o;
	}
	@Override
	public Object put(String key, Object value) {
		if (key == null) {
			throw UException.runtime("key == null");
		}
		return super.put(key, value);
	}
	public MapSO add(String key, Object value){
		put(key, value);
		return this;
	}
	public MapSO add(Params params) {
		this.add(params.getMap());
		return this;
	}
	public MapSO add(Map<String, Object> map) {
		Set<String> ks = map.keySet();
		for (String key : ks) {
			Object value = map.get(key);
			put(key, value);
		}
		return this;
	}

	public <T> Lst<T> asList(Class<T> classe) {
		return getSubList("array").map(o -> o.as(classe));
	}

	@SuppressWarnings("unchecked")
	public <T> T as(Atributo a) {

		if (a.fromMap == null) {
			Class<T> classe = (Class<T>) a.getType();
			return as(classe);
		}
		return (T) a.fromMap.call(this);

	}

	@SuppressWarnings("unchecked")
	public <T> T as(Class<T> classe) {
		if (classe == String.class) {
			return (T) MapSoToJson.get(this);
		}
		T o = UClass.newInstance(classe);
		setInto(o, false);
		return o;
	}

	public void setInto(Object o) {
		setInto(o, false);
	}

	public void setInto(Object o, boolean clear) {
		Class<Object> classe = UClass.getClass(o);
		setInto(o, classe, clear);
	}
	public void setInto(Object o, Class<?> classe, boolean clear) {
		Metodos metodos = ListMetodos.get(classe);
		Atributos as = AtributosBuild.get(classe);
		as.removeStatics();
		Atributo id = as.getId();
		if (id != null) {
			as.add(0, id);
		}
		Set<String> keys = keySet();
		for (String key : keys) {

			Object value = this.get(key);

			Atributo a = as.get(key);
			if (a != null) {
				if (value == null) {
					if (clear) {
						a.set(o, null);
					}
				} else if (a.isPrimitivo()){
					a.set(o, value);
				} else {
					if (value instanceof MapSO && !a.getType().equals(MapSO.class)) {
						MapSO map = (MapSO) value;
						try {
							value = map.as(a);
						} catch (Exception e) {
							map.as(a);
							throw new RuntimeException(e);
						}
					}

					if (a.isList()) {
						
						if (value instanceof String) {
							value = MapSoFromJson.get((String) value).get("array");
						}

						List<?> values = (List<?>) value;

						if (values.isEmpty() || !(values.get(0) instanceof MapSO)) {
							a.set(o, value);
						} else {

							Class<?> tipo = a.getTypeOfList();
							Lst<Object> lst = new Lst<>();

							for (Object x : values) {
								MapSO map = (MapSO) x;
								Object obj = map.as(tipo);
								map.setInto(obj, false);
								lst.add(obj);
							}

							a.set(o, lst);

						}

					} else if (a.getType() == Array.class) {

						Array<?> values;

						if (value instanceof ArrayList) {
							List<?> valuesList = (List<?>) value;
							values = new Array<>();
							for (Object x : valuesList) {
								values.java_addCast(x);
							}
						} else if (value instanceof Array) {
							values = (Array<?>) value;
						} else {
							throw new NaoImplementadoException();
						}

						if ((values.lengthArray() == 0) || !(values.array(0) instanceof MapSO)) {
							a.set(o, value);
						} else {

							Class<?> tipo = a.getTypeOfList();

							if (tipo == Object.class || tipo == MapSO.class) {
								a.set(o, values);
							} else {
								Array<Object> lst = values.map(x -> {
									MapSO map = (MapSO) x;
									Object obj = map.as(tipo);
									map.setInto(obj, false);
									return obj;
								});

								a.set(o, lst);
							}

						}

					} else {
						a.set(o, value);
					}

				}
			} else {
				Metodo metodo = metodos.get("set" + key);
				if (metodo != null) {
					Parametro p = metodo.getParametros().get(0);
					if (value == null) {
						if (clear) {
							metodo.invoke(o, value);
						}
					} else {
						if (UType.isPrimitiva(p.getType())){
						} else if (value instanceof MapSO && !p.getType().equals(MapSO.class)) {
							MapSO map = (MapSO) value;
							value = map.as(p.getType());
						}
						metodo.invoke(o, value);
					}
				}

			}
		}
	}
	public void print() {
		Set<String> keys = keySet();
		for (String key : keys) {
			Object value = this.get(key);
			SystemPrint.ln(key + ": " + value);
		}
	}

	public ListString asJson() {
		return asJson(true);
	}

	private static void addHtml(ListString list, String a, Object b) {

		a = StringToCamelCaseSepare.exec(a);

		if (b instanceof Boolean) {
			b = UBoolean.isTrue(b) ? "Sim" : "Não";
		}

//		TODO melhorar no futuro
		list.add("<tr><td style=\"text-align: right; font-weight: bold; padding: 3px;\">"+a+":</td><td style=\"padding: 3px;\">"+b+"</td></tr>");
	}

	public ListString asHtml() {
		ListString list = new ListString();
		list.add("<table style=\"font-size: 15px\">");
		forEach((a,b) -> addHtml(list, a, b));
    	list.add("</table>");
		return list;
	}

	public ListString asJson(boolean sortKeys) {
		return asJson(sortKeys, false);
	}

	public ListString asJson(boolean sortKeys, boolean showTypes) {

		ListString list = this.asJson(new Lst<>(), sortKeys, showTypes);
		ListString lst = new ListString();

//		remover virgulas excedentes
		while (!list.isEmpty()) {
			String s = list.remove(0);

			if (s.endsWith(",")) {

				if (list.isEmpty()) {
					s = StringRight.ignore1(s);
				} else {
					String x = list.get(0).trim();
					if (x.startsWith("}") || x.startsWith("]")) {
						s = StringRight.ignore1(s);
					}
				}

			}

			lst.add(s);
		}

		return lst;

	}

	private ListString asJson(Lst<MapSO> jaProcessados, boolean sortKeys, boolean showTypes) {

		ListString list = new ListString();

		if (jaProcessados.contains(this) && jaProcessados.anyMatch(o -> o.uniqueId == uniqueId)) {
			return list;
		}

		jaProcessados = jaProcessados.copy();

		jaProcessados.add(this);
		list.add("{");
		list.margemInc();

		ListString keys = new ListString(keySet());

		if (sortKeys) {
			keys.sort();
		}

		for (String key : keys) {

			Object value = this.get(key);

			if (value == null) {
				list.add("\"" + key + "\": null,");
				continue;
			}

			if (showTypes) {
				Class<?> c = value instanceof MapSO ? ((MapSO) value).getClasseOrigem() : value.getClass();
				key += " (" + c.getSimpleName() + ")";
			}

			if (UType.isPrimitiva(value) || value instanceof GFile) {
				if (UType.isData(value)) {
					String s = Data.to(value).format("[yyyy]-[mm]-[dd] [hh]:[nn]:[ss]");
					s = s.replace(" 00:00:00", "");
					value = s;
				}
				list.add("\"" + key + "\": \"" + value + "\",");
				continue;
			}

			if (value instanceof MapSO) {
				MapSO m = (MapSO) value;//jaProcessados.size()
				ListString lst = m.asJson(jaProcessados, sortKeys, showTypes);
				if (lst.isEmpty()) {
					list.add("\"" + key + "\": {},");
				} else {
					list.add("\"" + key + "\": {");
					lst.remove(0);
					list.add(lst);
				}
				continue;
			}

			if (value instanceof List) {

				List<?> lst = (List<?>) value;

				if (lst.isEmpty()) {
					list.add("\"" + key + "\": [],");
				} else {

					if (UType.isPrimitiva(lst.get(0))) {

						list.add("\"" + key + "\": [");
						list.margemInc();
						for (Object obj : lst) {
							list.add("\"" + obj + "\",");
						}

					} else {

						list.add("\"" + key + "\": [");
						list.margemInc();

						for (Object obj : lst) {

							if (obj == null) {
								continue;
							}

							if (obj instanceof IJson) {

								IJson json = (IJson) obj;
								list.add(json.toJson() + ",");

							} else {

								MapSO m;
								if (obj instanceof MapSO) {
									m = (MapSO) obj;
								} else {
									m = MapSoFromObject.get(obj);
								}

								ListString asJson = m.asJson(jaProcessados, sortKeys, showTypes);
								list.add(asJson);

							}

						}

					}
					list.margemDec();
					list.add("],");
				}

				continue;

			}

			if (value instanceof IJson) {
				IJson json = (IJson) value;
				list.add("\"" + key + "\": ");
				list.add(json.toJson());
				continue;
			}

			MapSO m = MapSoFromObject.get(value);
			ListString asJson = m.asJson(jaProcessados, sortKeys, showTypes);
			list.add("\"" + key + "\":");
			list.add(asJson);

		}

		list.margemDec();
		list.add("},");

		return list;
	}

	public int getIntObrig(String key) {
		Object o = this.getObrig(key);
		if (o instanceof String) {
			String s = (String) o;
			o = s.replace(".", "");
		}
		return IntegerParse.toInt(o);
	}
	public String getStringObrig(String key) {
		return StringParse.get(this.getObrig(key));
	}
	public boolean isEmpty(String key) {
		Object o = this.get(key);
		return UObject.isEmpty(o);
	}
	public Data getData(String key) {
		Object o = this.get(key);
		return Data.to(o);
	}
	public Data getDataObrig(String key) {
		Object o = this.getObrig(key);
		return Data.to(o);
	}
	public Double getDouble(String key) {
		Object o = this.get(key);
		return UDouble.toDouble(o);
	}
	public double getDoubleSafe(String key) {
		Double o = getDouble(key);
		return o == null ? 0 : o;
	}
	public Calendar getCalendar(String key) {
		Data data = getData(key);
		return data == null ? null : data.getCalendar();
	}

	@SuppressWarnings("unchecked")
	public Numeric2 getNumeric2(String key) {

		Object o = this.get(key);

		if (o == null) {
			return null;
		}

		if (o instanceof Numeric2) {
			return (Numeric2) o;
		}

		if (o instanceof Map<?, ?>) {
			Map<String, Object> map = (Map<String, Object>) o;
			if ("Numeric2".equals(map.get("tipo"))) {
				String s = StringParse.get(map.get("id"));
				if (StringContains.is(s, ".")) {
					int inteiros = IntegerParse.toInt(StringBeforeFirst.get(s, "."));
					int centavos = IntegerParse.toInt(StringAfterFirst.get(s, "."));
					return new Numeric2(inteiros, centavos);
				}
				return new Numeric2(IntegerParse.toInt(s));
			}
		}

		String s = getString(key);
		if (s == null) {
			return null;
		}
		return new Numeric2(s);
	}

	@SuppressWarnings("unchecked")
	public Numeric3 getNumeric3(String key) {

		Object o = this.get(key);

		if (o == null) {
			return null;
		}

		if (o instanceof Numeric3) {
			return (Numeric3) o;
		}

		if (o instanceof Map<?, ?>) {
			Map<String, Object> map = (Map<String, Object>) o;
			if ("Numeric3".equals(map.get("tipo"))) {
				String s = StringParse.get(map.get("id"));
				if (StringContains.is(s, ".")) {
					int inteiros = IntegerParse.toInt(StringBeforeFirst.get(s, "."));
					int centavos = IntegerParse.toInt(StringAfterFirst.get(s, "."));
					return new Numeric3(inteiros, centavos);
				}
				return new Numeric3(IntegerParse.toInt(s));
			}
		}

		String s = getString(key);
		if (s == null) {
			return null;
		}
		return new Numeric3(s);
	}

	@SuppressWarnings("unchecked")
	public Numeric4 getNumeric4(String key) {

		Object o = this.get(key);

		if (o == null) {
			return null;
		}

		if (o instanceof Numeric4) {
			return (Numeric4) o;
		}

		if (o instanceof Map<?, ?>) {
			Map<String, Object> map = (Map<String, Object>) o;
			if ("Numeric4".equals(map.get("tipo"))) {
				String s = StringParse.get(map.get("id"));
				if (StringContains.is(s, ".")) {
					int inteiros = IntegerParse.toInt(StringBeforeFirst.get(s, "."));
					int centavos = IntegerParse.toInt(StringAfterFirst.get(s, "."));
					return new Numeric4(inteiros, centavos);
				}
				return new Numeric4(IntegerParse.toInt(s));
			}
		}

		String s = getString(key);
		if (s == null) {
			return null;
		}
		return new Numeric4(s);
	}

	@SuppressWarnings("unchecked")
	public Numeric5 getNumeric5(String key) {

		Object o = this.get(key);

		if (o == null) {
			return null;
		}

		if (o instanceof Numeric5) {
			return (Numeric5) o;
		}

		if (o instanceof Map<?, ?>) {
			Map<String, Object> map = (Map<String, Object>) o;
			if ("Numeric5".equals(map.get("tipo"))) {
				String s = StringParse.get(map.get("id"));
				if (StringContains.is(s, ".")) {
					int inteiros = IntegerParse.toInt(StringBeforeFirst.get(s, "."));
					int centavos = IntegerParse.toInt(StringAfterFirst.get(s, "."));
					return new Numeric5(inteiros, centavos);
				}
				return new Numeric5(IntegerParse.toInt(s));
			}
		}

		String s = getString(key);
		if (s == null) {
			return null;
		}
		return new Numeric5(s);
	}

	@SuppressWarnings("unchecked")
	public Numeric8 getNumeric8(String key) {

		Object o = this.get(key);

		if (o == null) {
			return null;
		}

		if (o instanceof Numeric8) {
			return (Numeric8) o;
		}

		if (o instanceof Map<?, ?>) {
			Map<String, Object> map = (Map<String, Object>) o;
			if ("Numeric8".equals(map.get("tipo"))) {
				String s = StringParse.get(map.get("id"));
				if (StringContains.is(s, ".")) {
					int inteiros = IntegerParse.toInt(StringBeforeFirst.get(s, "."));
					int centavos = IntegerParse.toInt(StringAfterFirst.get(s, "."));
					return new Numeric8(inteiros, centavos);
				}
				return new Numeric8(IntegerParse.toInt(s));
			}
		}

		String s = getString(key);
		if (s == null) {
			return null;
		}
		return new Numeric8(s);
	}

	@SuppressWarnings("unchecked")
	public Numeric15 getNumeric15(String key) {

		Object o = this.get(key);

		if (o == null) {
			return null;
		}

		if (o instanceof Numeric15) {
			return (Numeric15) o;
		}

		if (o instanceof Map<?, ?>) {
			Map<String, Object> map = (Map<String, Object>) o;
			if ("Numeric15".equals(map.get("tipo"))) {
				String s = StringParse.get(map.get("id"));
				if (StringContains.is(s, ".")) {
					int inteiros = IntegerParse.toInt(StringBeforeFirst.get(s, "."));
					int centavos = IntegerParse.toInt(StringAfterFirst.get(s, "."));
					return new Numeric15(inteiros, centavos);
				}
				return new Numeric15(IntegerParse.toInt(s));
			}
		}

		String s = getString(key);
		if (s == null) {
			return null;
		}
		return new Numeric15(s);
	}

	public Numeric2 getNumeric2Obrig(String key) {
		Numeric2 o = getNumeric2(key);
		if (o == null) {
			throw UException.runtime("key não encontrado: " + key);
		}
		return o;
	}

	public Numeric3 getNumeric3Obrig(String key) {
		Numeric3 o = getNumeric3(key);
		if (o == null) {
			throw UException.runtime("key não encontrado: " + key);
		}
		return o;
	}

	public Numeric4 getNumeric4Obrig(String key) {
		Numeric4 o = getNumeric4(key);
		if (o == null) {
			throw UException.runtime("key não encontrado: " + key);
		}
		return o;
	}

	public Numeric5 getNumeric5Obrig(String key) {
		Numeric5 o = getNumeric5(key);
		if (o == null) {
			throw UException.runtime("key não encontrado: " + key);
		}
		return o;
	}

	public Numeric8 getNumeric8Obrig(String key) {
		Numeric8 o = getNumeric8(key);
		if (o == null) {
			throw UException.runtime("key não encontrado: " + key);
		}
		return o;
	}

	public Numeric15 getNumeric15Obrig(String key) {
		Numeric15 o = getNumeric15(key);
		if (o == null) {
			throw UException.runtime("key não encontrado: " + key);
		}
		return o;
	}

	public BigDecimal getBigDecimal(String key) {
		Numeric2 o = getNumeric2(key);
		return o == null ? null : o.getValor();
	}
	public BigDecimal getBigDecimal(String key, int casas) {
		Object o = this.get(key);
		if (o == null) {
			return null;
		}
		return UBigDecimal.toBigDecimal(o, casas);
	}

	public int id() {
		return getIntObrig("id");
	}

	public Boolean getBoolean(String key) {
		return UBoolean.toBoolean( this.get(key) );
	}
	public boolean getBooleanObrig(String key) {
		Boolean o = getBoolean(key);
		if (o == null) {
			throw UException.runtime("key não encontrado: " + key);
		}
		return o;
	}

	public boolean getBooleanSave(String key) {
		Boolean o = getBoolean(key);
		if (o == null) {
			return false;
		}
		return o;
	}

	public MapSO sub(String key) {
		MapSO o = new MapSO();
		o.pai = this;
		this.add(key, o);
		return o;
	}

	public MapSO out() {
		return pai;
	}
	public Lst<MapSO> getSubList(String key) {
		Lst<MapSO> list = new Lst<>();

		List<?> lst = this.get(key);
		if (!UList.isEmpty(lst)) {
			for (Object o : lst) {
				list.add(MapSoFromObject.get(o));
			}
		}
		return list;
	}
	public MapSO getSubObrig(String key) {
		MapSO o = getSub(key);
		if (o == null) {
			throw UException.runtime("key não encontrado: " + key);
		}
		return o;
	}
	public MapSO getSub(String key) {
		Object o = this.get(key);
		if (o instanceof MapSO) {
			return (MapSO) o;
		}
		return MapSoFromObject.get(o);
	}

	public ListString struct() {
		ListString list = new ListString();
		list.add("new MapSO()");
		this.struct(list);
		return list;
	}

	private void struct(ListString list) {
		Set<String> keys = keySet();
		for (String key : keys) {
			Object value = this.get(key);
			if (value == null) {
				list.add(".add(\"" + key + "\", null)");
			} else if (UType.isPrimitiva(value)) {
				if (value instanceof String) {
					list.add(".add(\"" + key + "\", \""+value+"\")");
				} else {
					list.add(".add(\"" + key + "\", "+value+")");
				}
			} else if (value instanceof MapSO) {
				list.add(".sub(\"" + key + "\")");
				list.getMargem().inc();
				MapSO map = (MapSO) value;
				map.struct(list);
				list.getMargem().dec();
				list.add(".out()");
			} else if (value instanceof List) {

				List<?> lst = (List<?>) value;

				if (lst.isEmpty()) {
					list.add(".add(\"" + key + "\", [])");
				} else {

					list.add(".add(\"" + key + "\", Arrays.asList(");
					list.getMargem().inc();

					Object first = lst.get(0);

					if (first instanceof String) {
						for (Object o : lst) {
							list.add("\"" + o + "\",");
						}
					} else if (UType.isPrimitiva(first)) {
						for (Object o : lst) {
							list.add(o + ",");
						}
					} else if (first instanceof MapSO) {
						for (Object o : lst) {
							MapSO map = (MapSO) o;
							list.add(map.struct());
							list.add(",");
						}
					} else {
						throw new RuntimeException("???");
					}

					String s = list.removeLast();
					s = StringRight.ignore1(s);
					if (!StringEmpty.is(s)) {
						list.add(s);
					}
					list.getMargem().dec();
					list.add("))");

				}

			} else {
				throw new RuntimeException("???");
			}
		}
	}

	public void save(String file) {
		save(GFile.get(file));
	}
	public void save(File file) {
		save(GFile.get(file));
	}
	public void save(GFile file) {
		asJson().save(file);
	}
	
	public static MapSO load(GFile file) {
		
		if (!file.exists()) {
			return new MapSO();
		}
		
		ListString list = new ListString().load(file);
		list.trimPlus();
		list.removeEmptys();
		list.removeIfStartsWith("#");
		String s = list.toString(" ");
		return MapSoFromJson.get(s);
	}
	
	@Deprecated//utilize load
	public boolean loadIfExists(GFile file) {
		
		if (!file.exists()) {
			return false;
		}
		
		load(file);

		return true;
		
	}
	
	public boolean loadIfExists(File file) {
		return loadIfExists(GFile.get(file));
	}

	public boolean loadIfExists(String fileName) {
		return loadIfExists(GFile.get(fileName));
	}

	public void loadObrig(String fileName) {
		if (!loadIfExists(fileName)) {
			throw new RuntimeException("Arquivo nao encontrado: " + fileName);
		}
	}

	public ListString getKeys() {
		ListString list = new ListString();
		list.addAll(keySet());
		return list;
	}

	public MapSO setObrig(String key, Object value) {
		if (StringEmpty.is(key)) {
			throw new RuntimeException("key is empty");
		}
		if (UObject.isEmpty(value)) {
			throw new RuntimeException("value is empty");
		}
		return this.add(key, value);
	}

	public boolean isTrue(String key) {
		return UBoolean.isTrue(getBoolean(key));
	}

	public <T> Lst<T> map(F2<String,Object,T> func) {
		Lst<T> result = new Lst<>();
		ListString keys = getKeys();
		for (String s : keys) {
			result.add(func.call(s, get(s)));
		}
		return result;
	}

	@SuppressWarnings("unchecked")
	public void trimValues() {

		for (String key : getKeys()) {
			Object value = get(key);
			if (value instanceof String) {
				value = value.toString().trim();
				put(key, value);
			} else if (value instanceof MapSO) {
				MapSO map = (MapSO) value;
				map.trimValues();
				put(key, map);
			} else if (value instanceof List) {
				List<?> list = (List<?>) value;
				if (list.isEmpty()) {
				}
				value = list.get(0);

				if (value instanceof String) {
					List<String> lst = (List<String>) list;
					value = new ListString(lst).trim();
					put(key, value);
				} else if (value instanceof MapSO) {
					List<MapSO> lst = (List<MapSO>) list;
					for (MapSO map : lst) {
						map.trimValues();
					}
					put(key, lst);
				}
			}
		}

	}

	public <T> T as(F1<MapSO, T> func) {
		return func.call(this);
	}

	public MapSO copy() {

		MapSO map = new MapSO();

		forEach((k,v) -> {
			if (v instanceof MapSO) {
				MapSO m = (MapSO) v;
				v = m.copy();
			}
			map.put(k, v);
		});

		return map;

	}
	
	public void removeIfKeys(F1<String, Boolean> func) {
		getKeys().each(key -> {
			if (func.call(key)) {
				remove(key);
			}
		});
	}

}
