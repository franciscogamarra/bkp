package gm.utils.reflection;

import java.util.Map;

import gm.utils.comum.Lst;
import gm.utils.comum.UType;

public class AtributoX {
	
	private String key;
	private Class<?> type;
	private Atributo a;
	private Object value;

	private AtributoX(Atributo a) {
		this.a = a;
	}

	private AtributoX(String key, Object value) {
		this.key = key;
		this.value = value;
		this.type = value.getClass();
	}

	public Class<?> getType() {
		return a == null ? type : a.getType();
	}
	
	public static Lst<AtributoX> getFrom(Object o) {
		if (o instanceof Map) {
			Lst<AtributoX> list = new Lst<>();
			Map<String, ?> map = toMap(o);
			map.forEach((k,v) ->list.add(new AtributoX(k, v == null ? Object.class : v.getClass())));
			return list;
		} else {
			Lst<AtributoX> itens = Atributos.getNoStatics(o.getClass()).map(a -> new AtributoX(a));
			Atributo id = Atributos.getId(o.getClass());
			if (id != null) {
				itens.add(0, new AtributoX(id));
			}
			return itens;
		}
	}
	
	@SuppressWarnings("unchecked")
	private static Map<String, ?> toMap(Object o) {
		return (Map<String, ?>) o;
	}

	public Object get(Object o) {
		if (a == null) {
			return toMap(o).get(key);
		} else {
			return a.get(o);
		}
	}

	public String getName() {
		if (a == null) {
			return key;
		} else {
			return a.getName();
		}
	}

	public boolean isPrimitivo() {
		if (a == null) {
			return UType.isPrimitiva(value);
		} else {
			return a.isPrimitivo();
		}
	}

	public boolean isFunction() {
		if (a == null) {
			return UType.isFunction(value);
		} else {
			return a.isFunction();
		}
	}
	
}