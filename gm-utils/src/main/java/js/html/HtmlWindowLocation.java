package js.html;

public class HtmlWindowLocation {
	
	public static HtmlWindowLocation instance = new HtmlWindowLocation();
	public String search = "?dev=bla";
	public String href = "/";
	public String pathname = "/";
	public String origin = "http://localhost:3000";
	public String host = "localhost:3000";

	public void assign(String url) {}
	public void replace(String url) {}

}
