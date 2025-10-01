package js.html.support;

import js.annotations.Support;

@Support
public class SelectInputProps {
	public String name;
	public String id;
	public SelectInputProps name(String o){name = o; return this;}
	public SelectInputProps id(String o){id = o; return this;}
}
