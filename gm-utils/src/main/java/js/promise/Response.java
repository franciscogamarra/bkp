package js.promise;

import js.annotations.Support;
import js.html.support.Headers;
import js.outros.Blob;
import src.commom.utils.comum.PromiseBuilder;

@Support
public class Response {
	public Object body;
	public String text;
	public Object data;
	public String url;
	public int status;
	public String statusText;
	public String message;
	public Object rawResponse;
	public Headers headers;
	
	public Promise<Blob> blob(){
		return null;
	}
	
	public Promise<Object> json() {
		return PromiseBuilder.ft(() -> body);
	}

	public Promise<String> text() {
		return PromiseBuilder.ft(() -> text);
	}
	
	public static Response jsonn() {
		return new Response();
	}	

	public Response body(Object o){body = o; return this;}
	public Response data(Object o){data = o; return this;}
	public Response url(String o){url = o; return this;}
	
	@SuppressWarnings("unchecked")
	public <T> T dataCast(Class<T> classe) {
		return (T) data;
	}
	
	public Response status(int o) {
		status = o;
		return this;
	}

	public Response statusText(String o) {
		statusText = o;
		return this;
	}
	
	public Response headers(Object o) {
		return this;
	}
	
	public Response config(Object o) {
		return this;
	}
	
	public Response request(Object o) {
		return this;
	}
	
}
