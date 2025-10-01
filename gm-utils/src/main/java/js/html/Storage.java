package js.html;

import java.util.HashMap;
import java.util.Map;

import gm.utils.anotacoes.Ignorar;
import gm.utils.string.ListString;

@Ignorar
public class Storage {

	private Map<String, String> map = new HashMap<>();

	public String getItem(String key) {
		return map.get(key);
	}

	public void setItem(String key, String value) {
		map.put(key, value);
	}

	public void removeItem(String key) {
		map.remove(key);
	}

	public void clear() {
		map.clear();
	}

	public String key(int index) {
		return new ListString(map.keySet()).get(index);
	}

}
