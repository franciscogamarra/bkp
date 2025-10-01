package js.map;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class IterableIterator<K> {

	private final List<K> list = new ArrayList<>();
	private int index = 0;

	public IterableIterator(Set<K> keySet) {
		keySet.forEach(k -> this.list.add(k));
	}

	public MapIteratorItem<K> next() {
		if (this.index == this.list.size()) {
			return new MapIteratorItem<>(null, true);
		}
		return new MapIteratorItem<>(this.list.get(this.index++), false);
	}

}
