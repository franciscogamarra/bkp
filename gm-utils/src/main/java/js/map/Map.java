package js.map;

import java.util.HashMap;

import gm.utils.anotacoes.Ignorar;
import gm.utils.lambda.P2;

@Ignorar
public class Map<K,V> {

	private final HashMap<K, V> map = new HashMap<>();
	public int size = 0;

	public IterableIterator<K> keys() {
		return new IterableIterator<>(map.keySet());
	}

	public Map<K, V> set(K key, V value) {
		map.put(key, value);
		refreshSize();
		return this;
	}

	public V get(K key) {
		return map.get(key);
	}

	public void forEach(P2<V, K> func) {
		map.forEach((k, v) -> func.call(v, k));
	}

	public void clear() {
		map.clear();
		refreshSize();
	}

	private void refreshSize() {
		size = map.size();
	}

	public void delete(Object key) {
		map.remove(key);
		refreshSize();
	}

}
