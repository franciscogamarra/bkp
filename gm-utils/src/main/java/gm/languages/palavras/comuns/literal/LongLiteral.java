package gm.languages.palavras.comuns.literal;

import src.commom.utils.string.StringRight;

public class LongLiteral extends Literal {

	private boolean semLnofinal = false;
	
	public LongLiteral(String s) {
		super(s);
	}
	
	public long toLong() {
		return Long.parseLong(getS());
	}
	
	public void negativar() {
		setS("-" + getS());
	}
	
	@Override
	public String getS() {
		if (semLnofinal) {
			return StringRight.ignore1(super.getS());
		} else {
			return super.getS();
		}
	}
	
	public void semLnofinal() {
		this.semLnofinal = true;
	}

}
