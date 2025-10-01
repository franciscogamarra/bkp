package gm.utils.jpa.constructor;

import gm.utils.lambda.F1;
import lombok.Getter;
import lombok.Setter;
import src.commom.utils.string.StringEmpty;

@Getter @Setter
public class PojoCampo {

	private String nome;
	private String alias;
	private F1<Object, String> function;

	@Override
	public String toString() {
		if (!StringEmpty.is(alias) && !nome.equals(alias)) {
			return nome + " - " + alias;
		}
		return nome;
	}

}
