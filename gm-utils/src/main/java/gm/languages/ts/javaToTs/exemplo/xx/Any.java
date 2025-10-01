package gm.languages.ts.javaToTs.exemplo.xx;

public class Any {

	private Object _naoTipadoValue_;
	
	public String nome;

	public Any error;
	public Any erro;
	public Any message;
	public Any messagem;
	public Any mensagem;
	public Any mensagens;

	public Any(Object o) {
		this._naoTipadoValue_ = o;
	}

	@SuppressWarnings("unchecked")
	public <T> T as() {
		return (T) _naoTipadoValue_;
	}

}
