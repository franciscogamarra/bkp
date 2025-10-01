package br.utils;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.http.MediaType;

public class Req {
	
	private final ApiClient api;
	private final HttpHeaders headers = new HttpHeaders();
	private Object body;
	private HttpMethod method;
	private String url;

	public Req(ApiClient api, String url, HttpMethod method) {
		this.api = api;
		this.url = url;
		this.method = method;
		headers.setContentType(MediaType.APPLICATION_JSON);
	}
	
	public Req header(String key, String value) {
		headers.add(key, value);
		return this;
	}
	
	public Req body(Object body) {
		this.body = body;
		return this;
	}
	
	public String call() {
		
		if (body != null) {
			
			if (body instanceof Dados) {
				Dados dados = (Dados) body;
				body = dados.getDados();
			}
			
		}
		
		String jsonEnvio = JsonUtils.toJson(body);
		
		HttpEntity<?> entity = new HttpEntity<>(jsonEnvio, headers);
		ResponseEntity<String> res = api.getTemplate().exchange(api.getUrlBase() + url, method, entity, String.class);

		int statusCode = res.getStatusCode().value();
		
		String json = res.getBody();
		
		if (statusCode >= 200 && statusCode <= 299) {
			return json;
		}
		
		Print.blocoRed(json);
		throw DevException.build("statusCode == " + statusCode);
		
	}

}
