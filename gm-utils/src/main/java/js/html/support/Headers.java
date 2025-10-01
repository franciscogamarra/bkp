package js.html.support;

import java.util.HashMap;
import java.util.Map;

import gm.utils.anotacoes.Ignorar;
import gm.utils.lambda.P2;
import js.annotations.Support;

@Ignorar @Support
public class Headers {

	public final Map<String, String> map;

	public Headers(Map<String, String> map) {
		this.map = map;
	}

	public Headers() {
		this(new HashMap<>());
	}
	
	public Headers Content__Type(String s) {
		return this;
	}

	public void set(String key, String value) {
		map.put(key, value);
	}

	public String get$(String key) {
		return map.get(key);
	}

	public void delete(String key) {
		map.remove(key);
	}

	public boolean has(String key) {
		return map.containsKey(key);
	}

	public void forEach(P2<String, String> func) {
		map.forEach((k,v) -> func.call(v, k));
	}

}
