package src.commom.utils.outros;

import js.html.ListenerEvent;
import js.support.console;
import src.commom.utils.string.StringCompare;

public class EventKey {

	private final ListenerEvent e;

	public EventKey(ListenerEvent e)  {
		this.e = e;
	}
	private boolean eq(String s) {
		return StringCompare.eq(this.e.key, s);
	}
	private boolean codeIs(String s) {
		return StringCompare.eq(this.e.code, s);
	}
	private boolean enter() {
		return eq("Enter");
	}
	public boolean enterPuro() {
		return enter() && !ctrl() && !shift();
	}
	public boolean shift() {
		return this.e.shiftKey;
	}
	public boolean ctrl() {
		return this.e.ctrlKey;
	}
	public boolean ctrlEnter() {
		return ctrl() && enter() && !shift();
	}
	public boolean shiftEnter() {
		return shift() && enter() && !ctrl();
	}
	public boolean esc() {
		return eq("Escape");
	}
	public boolean del() {
		return eq("Delete");
	}
	public boolean backspace() {
		return eq("Backspace");
	}
	public boolean arrowDown() {
		return eq("ArrowDown");
	}
	public boolean arrowUp() {
		return eq("ArrowUp");
	}
	public boolean arrowLeft() {
		return eq("ArrowLeft");
	}
	public boolean arrowRight() {
		return eq("ArrowRight");
	}
	public boolean end() {
		return eq("End");
	}
	public boolean home() {
		return eq("Home");
	}
	public boolean pageUp() {
		return eq("PageUp");
	}
	public boolean pageDown() {
		return eq("PageDown");
	}
	public boolean tab() {
		return eq("Tab");
	}
	public boolean capsLock() {
		return eq("CapsLock");
	}
	public boolean numLock() {
		return eq("NumLock");
	}
	public boolean pause() {
		return eq("Pause");
	}
	public boolean f1() {
		return eq("F1");
	}
	public boolean f2() {
		return eq("F2");
	}
	public boolean f3() {
		return eq("F3");
	}
	public boolean f4() {
		return eq("F4");
	}
	public boolean f5() {
		return eq("F5");
	}
	public boolean f6() {
		return eq("F6");
	}
	public boolean f7() {
		return eq("F7");
	}
	public boolean f8() {
		return eq("F8");
	}
	public boolean f9() {
		return eq("F9");
	}
	public boolean f10() {
		return eq("F10");
	}
	public boolean f11() {
		return eq("F11");
	}
	public boolean f12() {
		return eq("F12");
	}
	public String getKey() {
		return this.e.key;
	}
	public void preventDefault() {
		e.preventDefault();
	}
	public boolean mais() {
		return eq("+");
	}
	public boolean menos() {
		return eq("-");
	}
	public void print() {
		console.log(this.e.key);
	}
	public boolean mMinusculo() {
		return eq("m");
	}
	public boolean mMaiusculo() {
		return eq("M");
	}
	public boolean m() {
		return mMinusculo() || mMaiusculo();
	}
	public boolean keyF() {
		return codeIs("KeyF");
	}
	public boolean keyM() {
		return codeIs("KeyM");
	}
	public boolean keyZ() {
		return codeIs("KeyZ");
	}
	public boolean ctrlZ() {
		return keyZ() && ctrl() && !shift();
	}
	
}
