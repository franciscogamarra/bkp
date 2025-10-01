package gm.utils.rest;

import java.io.OutputStreamWriter;
import java.net.CookieHandler;
import java.net.CookieManager;
import java.net.HttpCookie;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.io.IOUtils;

import gm.utils.comum.SystemPrint;
import gm.utils.exception.NaoImplementadoException;
import gm.utils.exception.UException;
import gm.utils.lambda.F1;
import gm.utils.map.MapSO;
import gm.utils.map.MapSoFromJson;
import gm.utils.map.MapSoFromObject;
import gm.utils.map.MapSoToJson;
import js.support.console;
import lombok.Getter;
import src.commom.utils.comum.Box;
import src.commom.utils.string.StringBox;
import src.commom.utils.string.StringEmpty;
import src.commom.utils.string.StringParse;

@Getter
public class URest {

    private MapSO data;
    private int code;
    private String message;
    private String cookies;
	private Map<String, List<String>> headers;

    public static void main(String[] args) {

//    	String host = "http://localhost:8080";
    	String host = "http://appdesen";

//		get("http://localhost:8080/mobile/service/gerenciamento/controle/disponibilidade", null).print();
//		get("http://localhost:8080/mobile/service/convenios/conveniosEducacionais", null).print();
//		post("http://localhost:8080/mobile/service/logon96", null).print();
		URest o = URest.get(host+"/mobile/service/logon-teste/97");
		console.log(o.cookies);
//		private Integer inscricao;
//		private Boolean sugeridos;
//		private String token;
		MapSO map = new MapSO().add("inscricao", 96).add("sugeridos", true).add("token", null);
		Map<String, String> headers = new HashMap<>();
		headers.put("cookie", o.cookies);
		URest.post(host+"/mobile/service/listarProdutosComCredito", headers, map).print();

	}

    private static Map<String, String> getMapStringString(GetMapStringString o) {
    	return o == null ? null : o.getMapStringString();
    }

	public static URest post(String url, GetMapStringString headers, MapSO dados) {
    	return URest.post(url, URest.getMapStringString(headers), dados);
    }

	public static URest get(String url) {
		GetMapStringString headers = null;
		MapSO dados = null;
		return get(url, headers, dados);
	}

	public static URest get(String url, GetMapStringString headers, MapSO dados) {
    	return URest.get(url, URest.getMapStringString(headers), dados);
    }
	public static URest post(String url, Map<String, String> headers, String dados) {
		return post(url, headers, dados, null);
	}
	public static URest post(String url, Map<String, String> headers, String dados, F1<String,String> tratarReader) {
		return new URest(url, headers, dados, true, tratarReader);
	}
	public static URest post(String url, Map<String, String> headers, Object dados) {
    	MapSO map = MapSoFromObject.get(dados);
		return post(url, headers, map, null);
    }
	public static URest post(String url, Map<String, String> headers, MapSO dados) {
    	return post(url, headers, dados, null);
    }
	public static URest post(String url, Map<String, String> headers, MapSO dados, F1<String,String> tratarReader) {
		return new URest(url, headers, dados, true, tratarReader);
	}
    public static URest get(String url, Map<String, String> headers, MapSO dados) {
    	return get(url, headers, dados, null);
    }
    public static URest get(String url, Map<String, String> headers, MapSO dados, F1<String,String> tratarReader) {
    	return new URest(url, headers, dados, false, tratarReader);
    }
    public static URest get(String url, Map<String, String> headers, String dados) {
    	return get(url, headers, dados, null);
    }
    public static URest get(String url, Map<String, String> headers, String dados, F1<String,String> tratarReader) {
    	return new URest(url, headers, dados, false, tratarReader);
    }

    private static String convertDados(Map<String, String> headers, MapSO dados, boolean post) {
    	if (dados == null || dados.isEmpty()) {
			return null;
		}

		boolean form = headers != null && headers.get("Content-Type") != null && "application/x-www-form-urlencoded".contentEquals(headers.get("Content-Type"));

    	if (post && !form) {

    		try {
    			return new String(MapSoToJson.get(dados).getBytes("UTF-8"), "UTF-8");
			} catch (Exception e) {
				throw new RuntimeException(e);
			}

		}
		StringBox box = new StringBox("");

		dados.forEach((key, value) -> {
			box.add("&" + key + "=" + value);
		});

		String s = box.get().substring(1);

		if (post) {
			byte[] postData = s.getBytes( StandardCharsets.UTF_8 );
			int postDataLength = postData.length;
			if (headers == null) {
				throw new NaoImplementadoException();
			}
			headers.put("charset", "utf-8");
			headers.put("Content-Length", Integer.toString(postDataLength));
			return s;
		} else {
			return "?" + s;
		}

    }

    private URest(String url, Map<String, String> headers, MapSO dados, boolean post, F1<String,String> tratarReader) {
    	this(url, headers, URest.convertDados(headers, dados, post), post, tratarReader);
    }
	@SuppressWarnings("deprecation")
	private URest(String url, Map<String, String> headers, String dados, boolean post, F1<String,String> tratarReader) {

		CookieManager cookieManager = new CookieManager();
		CookieHandler.setDefault(cookieManager);

		if (!post && dados != null) {
			url += "?" + dados;
		}

		Box<HttpURLConnection> box = new Box<>();

		try {

			MapSO finalHeaders = new MapSO();
			finalHeaders.put("Content-Type", "application/json; charset=UTF-8");
			finalHeaders.put("Accept", "*/*");

			if (headers != null) {
				headers.forEach((key, value) -> finalHeaders.put(key, value));
			}

			URL obj = new URL(url);

			HttpURLConnection con = (HttpURLConnection) obj.openConnection();

			box.set(con);
			con.setDoOutput(true);
			con.setRequestMethod(post ? "POST" : "GET");

			finalHeaders.forEach((k,v) ->
				con.setRequestProperty(k, StringParse.get(v))
			);

			if (post && dados != null) {
				OutputStreamWriter out = new OutputStreamWriter(box.get().getOutputStream());
//				MapSO map = MapSoFromObject.get(dados);
//				String json = map.asJson().toString("");
				out.write(dados);
				out.close();
			}

			String reader;

			try {
				reader = IOUtils.toString(con.getInputStream(), "UTF-8");
			} catch (Exception e) {
				SystemPrint.err(e.getMessage());
				try {
					reader = IOUtils.toString(con.getErrorStream(), "UTF-8");
				} catch (Exception e2) {
					reader = e.getMessage() + " / " + e2.getMessage();

					try {
						console.log(con.getContent());
					} catch (Exception e3) {
						// TODO: handle exception
					}

				}
			}

			if (tratarReader != null) {
				reader = tratarReader.call(reader);
			}

			if (StringEmpty.is(reader)) {
				data = null;
			} else {

				if (!reader.contains("{")) {
					reader = "{result:"+reader+"}";
				}

				data = MapSoFromJson.get(reader);

			}

			code = con.getResponseCode();
			message = con.getResponseMessage();

			this.headers = con.getHeaderFields();

			List<HttpCookie> cookies2 = cookieManager.getCookieStore().getCookies();

			if (cookies2.isEmpty()) {
				cookies = null;
			} else {
				cookies = "";
				cookies2.forEach(o -> cookies += ";"+o);
				cookies = cookies.substring(1);
			}

			if (code < 200 || code > 299) {
				throw new ResponseException(message, code, data);
			}

		} catch (ResponseException e) {
			throw e;
		} catch (Exception e) {
			SystemPrint.err(url);
			throw UException.runtime(e);
		} finally {
			if (box.isNotNull()) {
				box.get().disconnect();
			}
		}
	}

	public void print() {

		console.log("---------------------");
		console.log("statusCode: " + code);
		console.log("message: " + message);
		console.log("cookies: " + cookies);

		if (getData() == null) {
			console.log("data: null");
		} else {
			getData().print();
		}

	}

	public String getAuthorization() {

		List<String> list = getHeaders().get("Authorization");

		if (list == null || list.isEmpty()) {
			return null;
		}
		return list.get(0);

	}

	public void printHeaders() {

		getHeaders().forEach((key, value) -> {
			console.log(key);
			for (String s : value) {
				console.log("\t" + s);
			}
		});

	}

}
