package gm.utils.outros;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import gm.utils.lambda.F0;

public class ThreadParam<T> {

	private static Map<Thread, Map<String, Object>> map = new HashMap<>();

	private String key;
	private F0<T> create;

	public ThreadParam(String key, F0<T> create) {
		this.key = key;
		this.create = create;
	}

	@SuppressWarnings("unchecked")
	public T get() {
		clearDeadThreads();
		Map<String, Object> values = map.get(Thread.currentThread());
		if (values == null) {
			values = new HashMap<>();
			T o = create.call();
			values.put(key, o);
			map.put(Thread.currentThread(), values);
			return o;
		}
		return (T) values.get(key);
	}

	private static void clearDeadThreads() {
		List<Thread> list = new ArrayList<>();
		map.forEach((k,v) -> {
			if (!k.isAlive()) {
				list.add(k);
			}
		});
		for (Thread key : list) {
			map.remove(key);
		}
	}

}
