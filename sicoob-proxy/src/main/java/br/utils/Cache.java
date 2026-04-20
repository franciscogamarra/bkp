package br.utils;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.ResponseEntity;

import br.support.comum.Lst;
import br.utils.strings.StringAfterFirst;
import br.utils.strings.StringBeforeFirst;
import br.utils.strings.StringSplit;

public class Cache {
	
	private static final boolean enabled = false;
	private static final Map<String, ResponseEntity<String>> MAP = new HashMap<>();
	private static final ResponseEntity<String> NULL = ResponseEntity.ok().build(); 
	
	private static String trataKey(String s) {
		s = StringAfterFirst.get(s, "/api/");
//		if (s.contains("pagina=")) {
//			return null;
//		}
		if (s.contentEquals("numeros-proposta-credito-bndes")) {
			return null;
		}
		s = s.replace("&usuarioLogado=fabioo0001_00", "");
		s = s.replace("?usuarioLogado=fabioo0001_00", "?");
		s = s.replace("?&", "?");
//		s = s.replace("?", "/");
//		s = s.replace("&", "_");
//		s = s.replace("=", "");
//		for (int i = 0; i < 10; i++) {
//			s = s.replace("/" + i, "" + i);
//		}
		return s;
	}

	public static ResponseEntity<String> get(String key) {
		
		if (!enabled) {
			return null;
		}
		
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
		
		try {
			value = JsonUtils.format(value);
		} catch (Exception e) {
			// TODO: handle exception
		}
		
		Lst<String> lst = StringSplit.exec(value, "\n");
		
		if (key.contains("?")) {
			String queryParams = StringAfterFirst.get(key, "?");
			key = StringBeforeFirst.get(key, "?");
			lst.removeLast();
			lst.add("	,\"queryParams\": \"" + queryParams + "\"");
			lst.add("}");
		}
		
		lst.save("c:/dev/tmp/sicoob-proxy/requests/" + key + ".json");
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