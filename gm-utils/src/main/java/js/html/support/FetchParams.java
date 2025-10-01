package js.html.support;

import gm.utils.anotacoes.Ignorar;
import gm.utils.map.MapSO;
import gm.utils.map.MapSoFromJson;
import gm.utils.map.MapSoFromObject;
import js.annotations.Support;
import src.commom.utils.string.StringEmpty;

@Support @Ignorar
public class FetchParams {

	public String method;
	public Headers headers;
	public String body;
	public String mode;
	public String cache;
	public String credentials;

	public MapSO getBody() {
		if (body == null) {
			return null;
		}
		if (!(body instanceof String)) {
			return MapSoFromObject.get(body);
		}
		String s = body;
		if (StringEmpty.is(s)) {
			return null;
		}
		return MapSoFromJson.get(s);
	}

	public FetchParams method(String o){method = o; return this;}
	public FetchParams headers(Headers o){headers = o; return this;}
	public FetchParams headers(Object o){return this;}
	public FetchParams body(String o){body = o; return this;}
	public FetchParams mode(String o){mode = o; return this;}
	public FetchParams cache(String o){cache = o; return this;}
	public FetchParams credentials(String o){credentials = o; return this;}

}
