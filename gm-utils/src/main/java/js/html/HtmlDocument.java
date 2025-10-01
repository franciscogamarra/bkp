package js.html;

import gm.utils.comum.SystemPrint;
import gm.utils.lambda.P1;
import src.commom.utils.string.StringCompare;

public class HtmlDocument {

	public static IHtmlDocument implementacao;

	public HtmlInstance body = new HtmlInstance();
	public DocumentElement documentElement = new DocumentElement();
	public String cookie;
	public Element activeElement;

	public Object hardware;
	
	private static final Element HTML_TAG = new Element() {
		@Override public void scrollIntoView(boolean b) {}
		@Override public void focus() {}
		@Override public void click() {}
	};

	public Element[] getElementsByTagName(String tag) {
		if (StringCompare.eq(tag, "html")) {
			return new Element[] {HTML_TAG};
		}
		SystemPrint.err("HtmlDocument - Não é possível utilizar este metodo em testes. Os componentes react mudam suas tags para tgs imprevisiveis. Utilize getElementsByClassName ou leia diretamente dos nodes");
		return new Element[] {};
	}

	public Element getElementById(String id) {
		return implementacao.getElementById(id);
	}

	public Element createElement(String tag) {
		return null;
	}

	public void addEventListener(String event, P1<ListenerEvent> func) {
		if ("keydown".equals(event)) {

		}
	}

	public NodeList querySelectorAll(String string) {
		return new NodeList();
	}

	public NodeList getElementsByClassName(String string) {
		return implementacao.getElementsByClassName(string);
	}

}
