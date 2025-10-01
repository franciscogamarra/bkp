package js.html;

import js.annotations.Support;

@Support
public class ListenerEvents {
	public static final ListenerEvent ESC;
	static {
		ESC = new ListenerEvent();
		ESC.key = "Escape";
	}
}