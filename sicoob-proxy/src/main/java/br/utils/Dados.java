package br.utils;

import java.util.HashMap;
import java.util.Map;

import br.utils.strings.StringSplit;
import lombok.Getter;

@Getter
public class Dados {
	
	private Map<String, Object> dados = new HashMap<>();
	
	@SuppressWarnings("unchecked")
	public <T> T get(String key) {
		
		Lst<String> itens = StringSplit.exec(key, ".");
		
		Map<String, Object> map = dados;
		while (itens.size() > 1) {
			map = (Map<String, Object>) map.get(itens.remove(0));
			if (map == null) {
				return null;
			}
		}
		
		Object o = map.get(itens.remove(0));
		return (T) o;
		
	}
	
	public void set(String key, Object value) {
		dados.put(key, value);
	}
	
	public void load(String resource) {
		dados = JsonUtils.fromJson(JsonUtils.loadResource(resource));
	}

	public void set(String res) {
		dados = JsonUtils.fromJson(res);		
	}
	
}
