package br.utils;

import org.springframework.http.HttpMethod;
import org.springframework.web.client.RestTemplate;

import lombok.Getter;

@Getter
public class ApiClient {

	private final RestTemplate template;
	private final String urlBase;
	
	public ApiClient(String urlBase) {
		this.urlBase = urlBase;
		try {
			SSLUtil.disableSSL();
			template = new RestTemplate();
		} catch (Exception e) {
			throw DevException.build(e);
		}
	}
	
	public Req post(String url) {
		return new Req(this, url, HttpMethod.POST);
	}
	
	public Req get(String url) {
		return new Req(this, url, HttpMethod.GET);
	}
	
	public Req put(String url) {
		return new Req(this, url, HttpMethod.PUT);
	}
	
	public Req delete(String url) {
		return new Req(this, url, HttpMethod.DELETE);
	}
	
	public static final ApiClient local = new ApiClient("http://localhost:8080/cre-concessao-bndes-api-web/api");
	
}