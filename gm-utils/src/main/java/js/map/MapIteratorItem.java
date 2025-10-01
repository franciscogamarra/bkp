package js.map;

public class MapIteratorItem<K> {
	public final K value;
	public final boolean done;
	public MapIteratorItem(K value, boolean done) {
		this.value = value;
		this.done = done;
	}
}
