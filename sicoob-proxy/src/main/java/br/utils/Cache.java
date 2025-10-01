package br.utils;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.springframework.http.ResponseEntity;

import br.utils.strings.StringAfterFirst;

public class Cache {
	
	private static final Map<String, ResponseEntity<String>> MAP = new HashMap<>();
	private static final ResponseEntity<String> NULL = ResponseEntity.ok().build(); 
	
	private static String trataKey(String s) {
		s = StringAfterFirst.get(s, "/api/");
		if (s.contains("pagina=")) {
			return null;
		}
		if (s.contentEquals("numeros-proposta-credito-bndes")) {
			return null;
		}
		s = s.replace("&usuarioLogado=fabioo0001_00", "");
		s = s.replace("?usuarioLogado=fabioo0001_00", "?");
		s = s.replace("?&", "?");
		s = s.replace("?", "/");
		s = s.replace("&", "_");
		s = s.replace("=", "");
		for (int i = 0; i < 10; i++) {
			s = s.replace("/" + i, "" + i);
		}
		return s;
	}

	public static ResponseEntity<String> get(String key) {
		key = trataKey(key);
		if (key == null) {
			return null;
		}
		ResponseEntity<String> res = MAP.get(key);
		if (res == null) {
			String s = loadResource(key);
			if (s == null) {
				MAP.put(key, NULL);
				return null;
			} else {
				res = ResponseEntity.ok().body(s);
				MAP.put(key, res);
				return res;
			}
		} else if (res == NULL) {
			return null;
		} else {
			return res;
		}
		
//		http://localhost:4200/cre-concessao-bndes-api-web/api/programas-bndes/2473/plano-pagamento
	}

	public static void set(String key, ResponseEntity<String> res) {
		key = trataKey(key);
		if (key == null) {
			return;
		}
		String value = res.getBody();
		Lst<String> lst = new Lst<>();
		
		try {
			value = JsonUtils.format(value);
		} catch (Exception e) {
			// TODO: handle exception
		}
		
		lst.add(value);
		lst.save("c:/dev/myprojects/sicoob-proxy/src/main/resources/jsons/cache/" + key + ".json");
		MAP.put(key, res);
	}
	
	private static String loadResource(String key) {
		try {
			return JsonUtils.loadResource("cache" + "/" + key);
		} catch (Exception e) {
			return null;
		}
	}
	
	
}