package js.html;

import js.annotations.Support;

@Support
public class PostMessage {

	public String origin;

	public Object data;
	public final PostMessageCurrentTarget source = new PostMessageCurrentTarget();

	public static class PostMessageData {
		public String msg;
		public Object params;
	}

	public static class PostMessageCurrentTarget {
		public HtmlWindowLocation location = HtmlWindowLocation.instance;
	}

}
