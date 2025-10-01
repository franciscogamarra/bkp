package js.html.support;

import js.annotations.NoAuto;
import js.annotations.Support;
import js.array.Array;

@Support @NoAuto
public class OnTouchEvent {
	public Object target;
	public Object currentTarget;
	public Array<Touch> touches;
	public Array<Touch> changedTouches;
	public void preventDefault() {}
}
