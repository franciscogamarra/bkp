package src.commom.utils.comum;

import gm.languages.ts.javaToTs.JS;
import gm.languages.ts.javaToTs.annotacoes.PodeSerNull;
import gm.utils.lambda.F3;
import gm.utils.lambda.P2;
import js.map.IterableIterator;
import js.map.Map;
import src.commom.utils.integer.IntegerCompare;
import src.commom.utils.integer.IntegerParse;
import src.commom.utils.object.Equals;
import src.commom.utils.string.StringCompare;
import src.commom.utils.string.StringIs;
import src.commom.utils.string.StringParse;

public class JsMap<K,V> extends JS {
	
	private final Map<K,V> map = new Map<>();

	public IterableIterator<K> keys() {
		return map.keys();
	}

	public JsMap<K, V> set(K key, V value) {
		map.set(key, value);
		return this;
	}
	
	public JsMap<K, V> setMap(Map<K,V> mp) {
		
		if (isNull_(mp)) {
			return this;
		}
		
		forEachSafe(mp, (k, v) -> set(k, v));
		
		return this;
		
	}

	@PodeSerNull
	public V get(K key) {
		return orNull(map.get(key));
	}
	
	private void forEachSafe(Map<K,V> mp, P2<K, V> func) {
		mp.forEach((v, k) -> func.call(k, v));
	}

	public void forEach(P2<K, V> func) {
		forEachSafe(map, func);
	}

	public void clear() {
		map.clear();
	}

	public void delete(Object key) {
		map.delete(key);
	}

	public int size() {
		return map.size;
	}

	public boolean isEmpty() {
		return IntegerCompare.eq(size(), 0);
	}

	public <T> T reduce(F3<K, V, T, T> func, T initial) {
		Box<T> box = new Box<>();
		box.set(initial);
		forEach((k,v) -> box.set(func.call(k, v, box.get())));
		return box.get();
	}

	public String asString(String separador) {
		if (isEmpty()) {
			return "";
		}
		return reduce((k, v, /*String*/ atual) -> atual + separador + k + "="+v, "").substring(separador.length());
	}

	public boolean containsValue(V value) {
		if (isEmpty()) {
			return false;
		}
		Box<Boolean> box = new Box<>();
		box.set(false);
		if (StringIs.is(value)) {
			String ss = StringParse.get(value);
			forEach((k,v) -> {
				String s = StringParse.get(v);
				if (StringCompare.eq(s, ss)) {
					box.set(true);
				}
			});
		} else {
			forEach((k,v) -> {
				if (Equals.is(k, value)) {
					box.set(true);
				}
			});
		}
		return box.get();
	}

	@PodeSerNull
	public Integer getInt(K key) {
		return IntegerParse.toInt(get(key));
	}

	public int getIntDef(K key, int def) {
		@PodeSerNull Integer o = getInt(key);
		if (isNull_(o)) {
			return def;
		}
		return o;
	}

	@Override
	public String toString() {

		if (isEmpty()) {
			return "{}";
		}
		return "{"+reduce((k, v, /*String*/ atual) -> atual + ",\"" + k + "\": \""+v+"\"", "").substring(1)+"}";

	}

	public Map<K, V> getMap() {
		return map;
	}

}
