package gm.languages.react.termos;

import gm.languages.palavras.Palavra;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class ReactTagClose extends Palavra {

	public ReactTagClose() {
		super(")");
	}

	public ReactTagClose(ReactTagOk ok) {
		this();
		setOk(ok);
	}
	
	private ReactTagOk ok;

	public void setOk(ReactTagOk abertura) {
		this.ok = abertura;
		if (abertura.getFechamento() != this) {
			abertura.setFechamento(this);
		}
	}
	
	@Override
	public String getS() {
		if (ok == null) {
			return "|?ReactTagClose?|)";
		}
		return super.getS();
	}

}
