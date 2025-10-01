package js.outros;

import js.annotations.Support;

@Support
public class BlobData {
	public String type;
	public String name;
	public BlobData type(String o){type = o; return this;}
	public BlobData name(String o){name = o; return this;}
}
