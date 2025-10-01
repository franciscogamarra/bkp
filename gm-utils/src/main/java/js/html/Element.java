package js.html;

import gm.languages.ts.javaToTs.annotacoes.Any;
import js.html.support.Rect;

@Any
public abstract class Element  {

	public Object[] files;
	public Object src;
	public String type;
	public boolean async;
	public String innerHTML;
	public String rel;
	public String href;
	public int clientHeight;
	public int clientWidth;
	public Element parentNode;
	public int offsetWidth;
	public int offsetLeft;
	public int scrollLeft;
	public HtmlElementStyle style = new HtmlElementStyle();
	
	public Object value;
	public boolean checked;
	public Element nextSibling;
	public Object result;
	public int selectionStart;
	
	public final DOMTokenList classList = new DOMTokenList();

	public void blur() {}	

	public int scrollTop;
	public int scrollHeight;
	public final HtmlWindow contentWindow = new HtmlWindow();
	public String tagName;
	public String download;

	public static class HtmlElementStyle {
		public int width;
		public int opacity;
		public String display;
	}

	public abstract void focus();
	public abstract void scrollIntoView(boolean b);
	public abstract void click();

//	public final void focus() {
//		node.getComponent().focus();
//	}
//
//	public void scrollIntoView(boolean b) {
//		node.getComponent().focus();
//	}
//
//	public void click() {
//		node.getComponent().click();
//	}

	public void remove() {

	}
	public Rect getBoundingClientRect() {
		// TODO Auto-generated method stub
		return null;
	}

}
