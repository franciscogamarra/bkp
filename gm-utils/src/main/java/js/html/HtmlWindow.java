package js.html;

import gm.utils.lambda.P0;
import gm.utils.lambda.P1;
import js.outros.Blob;

public class HtmlWindow {

	public static final HtmlWindow instance = new HtmlWindow();
	
	
	public static class HtmlWindowURL {

		public String createObjectURL(Blob csv_file) {
			return null;
		}
		
	}
	
	public final HtmlWindowURL URL = null;
	
//	public sta

	static {
		instance.parent = instance;
		instance.ReactNativeWebView = instance;
	}

	public int innerWidth;
	public int innerHeight;
	public int outerWidth;
	public int outerHeight;

	public HtmlWindow parent;
	public HtmlWindow ReactNativeWebView;
	public HtmlScreen screen = HtmlScreen.instance;
	public HtmlHistory history = HtmlHistory.instance;
	public HtmlWindowLocation location = HtmlWindowLocation.instance;
	
	public Storage sessionStorage;
	public Storage localStorage = new Storage();

	public void addEventListener(String s, P0 func) {

	}

	public void addEventListener(String s, P1<?> func, boolean b) {

	}

	public void addEventListener(String s, P0 func, HtmlWindowEventListenerParams params) {

	}

	public void removeEventListener(String s, P0 func) {}
	public void removeEventListener(String s, P1<?> func) {}

	public HtmlWindow open(String url) {
		return new HtmlWindow();
	}
	
	public HtmlWindow open(String url, String complemento) {
		return new HtmlWindow();
	}

	public void postMessage(Object o) {

	}

}
