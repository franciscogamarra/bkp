package js.html.support;

import js.annotations.Support;

@Support
public class ParseHighlight {
	public String text;
	public boolean highlight;

	@Override
	public String toString() {
		return text + " " + highlight;
	}

	public ParseHighlight text(String o){text = o; return this;}
	public ParseHighlight highlight(boolean o){highlight = o; return this;}
}
