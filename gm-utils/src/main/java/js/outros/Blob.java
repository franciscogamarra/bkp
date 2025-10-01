package js.outros;

import gm.utils.anotacoes.Ignorar;

@Ignorar
public class Blob {
	public BlobData data;
	public Object size;
	public Blob data(BlobData o){data = o; return this;}
	public Blob size(Object o){size = o; return this;}
	
	public Blob() {}
	public Blob(Object... args) {}
	
}
