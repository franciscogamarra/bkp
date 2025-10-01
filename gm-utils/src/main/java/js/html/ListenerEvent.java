package js.html;

import gm.languages.ts.javaToTs.annotacoes.Any;

@Any
public class ListenerEvent {

	protected ListenerEvent() {}

	public Element target;
	public String code;
	public int keyCode;
	public String key;
	public boolean shiftKey;
	public boolean ctrlKey;
	public void preventDefault() {}
	public void stopPropagation() {}
	public ListenerEvent keyCode(int o){keyCode = o; return this;}
	public ListenerEvent key(String o){key = o; return this;}
	public ListenerEvent target(Element o){target = o; return this;}
	public ListenerEvent code(String o){code = o; return this;}

}
