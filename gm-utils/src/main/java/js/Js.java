package js;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import gm.utils.anotacoes.Ignorar;
import gm.utils.classes.UClass;
import gm.utils.comum.Aleatorio;
import gm.utils.comum.SystemPrint;
import gm.utils.comum.UType;
import gm.utils.lambda.F0;
import gm.utils.lambda.F1;
import gm.utils.lambda.P0;
import gm.utils.number.UDouble;
import gm.utils.reflection.Atributo;
import gm.utils.reflection.AtributosBuild;
import gm.utils.reflection.ListMetodos;
import gm.utils.reflection.Metodo;
import gm.utils.rest.URest;
import gm.utils.string.ListString;
import js.annotations.Support;
import js.html.HtmlDocument;
import js.html.HtmlWindow;
import js.html.support.FetchParams;
import js.html.support.Headers;
import js.promise.Promise;
import js.promise.Response;
import js.support.ThreadsList;
import js.support.console;
import src.commom.utils.comum.PromiseBuilder;
import src.commom.utils.integer.IntegerParse;
import src.commom.utils.object.Obrig;
import src.commom.utils.string.StringContains;
import src.commom.utils.string.StringEmpty;

@Support
public class Js {

	public static final boolean inJava = true;

	public static final HtmlDocument document = new HtmlDocument();
	public static final HtmlWindow window = HtmlWindow.instance;

	public static String typeof(Object o) {

		if (o == null) {
			return null;
		}
		if (o instanceof Boolean) {
			return "boolean";
		}
		if (o instanceof String) {
			return "string";
		}
		if ((o instanceof Integer) || (o instanceof Double)) {
			return "number";
		}

		Class<?> classe = o.getClass();
		if (classe.equals(boolean.class)) {
			return "boolean";
		}
		if (classe.equals(int.class)) {
			return "number";
		}
		if (classe.equals(double.class)) {
			return "number";
		}

		String s = classe.getSimpleName();
		if (StringContains.is(s, "$Lambda$")) {
			return "function";
		}
		return s;

	}

	@SuppressWarnings("unchecked")
	public static <T> T get(Object o, String key) {

		if (UType.isPrimitiva(o)) {
			return null;
		}

		Atributo a = AtributosBuild.get(o).get(key);

		if (a != null) {
			return a.get(o);
		}

		Metodo metodo = ListMetodos.get(UClass.getClass(o)).get(key);
		if (metodo != null) {
			F0<?> func = () -> metodo.invoke(o);
			return (T) func;
		}

		return null;
	}

	@Ignorar
	public static void setInterval(P0 f, int milisegundos) {
		Js.setTimeout(() -> {
			f.call();
			Js.setTimeout(f, milisegundos);
		}, milisegundos);
	}

	@Ignorar
	public static void setTimeout(P0 f) {
		Js.setTimeout(f, 0);
	}

	public static F0<Boolean> podeContinuarTimeout;

	private static boolean podeContinuarTimeout() {
		return podeContinuarTimeout == null || podeContinuarTimeout.call();
	}

	@Ignorar
	public static void setTimeout(P0 f, int milisegundos) {
		RuntimeException origem = new RuntimeException();
		origem.getStackTrace();
		setTimeout(f, milisegundos, origem);
	}

	@Ignorar
	private static void setTimeout(P0 f, int milisegundos, RuntimeException origem) {

		P0 x = () -> {
			if (podeContinuarTimeout()) {
				try {
					f.call();
				} catch (Throwable e) {
					SystemPrint.err("========================================================");
					SystemPrint.err("========= Ocorreu um erro dentro de um timeout =========");
					SystemPrint.err("========================================================");
					origem.printStackTrace();
					SystemPrint.err("========================================================");
					throw e;
				}
			} else {
				Js.setTimeout(f, 200, origem);
			}
		};

		ThreadsList.add(x);
	}

	public static final Object undefined = null;

	public static class Math {

		public static int random() {
			return Aleatorio.getInteger();
		}

		public static int floor(int i) {
			return i;
		}

	}

	public static <T> T undefined() {
		return null;
	}

	public static Integer parseInt(Object o) {
		return IntegerParse.Js.parseInt(o);
	}

	public static class JsConstructor {
		public String name;
	}

	public static class Proto {
		public JsConstructor constructor;
	}

	public Proto __proto__ = new Proto();

	public static Double parseFloat(Object o) {
		return UDouble.toDouble(o);
	}
	
	public static Promise<Response> fetch(String s) {
		return null;
	}

	private static String cookies;

	public static Promise<Response> fetch(String url, FetchParams params) {

		String method = Obrig.check(params.method);

		if (!method.contentEquals("GET") && !method.contentEquals("POST")) {
			throw new RuntimeException("Preparado apenas para GET e POST");
		}

		Map<String, String> headers = params.headers == null ? new HashMap<>() : params.headers.map;

		if (StringEmpty.notIs(cookies)) {
			headers.put("cookie", cookies);
		}

		return PromiseBuilder.ft(() -> {
			URest result;
			if (method.contentEquals("GET")) {
				result = URest.get(url, headers, params.body);
			} else {
				result = URest.post(url, headers, params.body);
			}
			Response o = new Response();
			o.data = result.getData();
			o.body = o.data;
			o.url = url;
			o.status = result.getCode();
			o.headers = new Headers();

			for (String key : result.getHeaders().keySet()) {
				List<String> list = result.getHeaders().get(key);
				String value = new ListString(list).toString("; ");
				o.headers.set(key, value);
			}

			if (StringEmpty.notIs(result.getCookies())) {
				cookies = result.getCookies();
			}

			return o;
		});

	}

	public static <T> T await(Promise<T> promise) {
		return promise.get();
	}

	public static P0 async(P0 func) {
		return func;
	}

	public static <Y,T> F1<Y,T> async(F1<Y,T> func) {
		return func;
	}

	public static void alert(String message) {
		console.log(message);
	}

	public static boolean isNaN(Number number) {
		return number == null;
	}

	public static <T> T eval(String expressao) {
		return null;
	}

	public static double toFixed(double d, int casas) {
		return gm.utils.number.Numeric.toNumeric(d, casas).toDouble();
	}

	public static Double Number(Object value) {
		return UDouble.toDouble(value);
	}
	
}