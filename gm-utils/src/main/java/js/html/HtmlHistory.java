package js.html;
import js.support.console;

public class HtmlHistory {
	public static final HtmlHistory instance = new HtmlHistory();
	public void back() {
		console.log("back");
	}
}
