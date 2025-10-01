package gm.utils.javaCreate.annotations;

import gm.utils.javaCreate.JcAnotacao;
import gm.utils.javaCreate.JcAnotacoes;
import gm.utils.reflection.Atributo;
import jakarta.validation.constraints.Digits;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class JcDigits extends JcAnotacaoGetSet {

	private Integer integer;
	private Integer fraction;

	public JcDigits() {
		super(Digits.class);
	}

	public static JcDigits from(Atributo a) {

		if (!a.isNumeric()) {
			return null;
		}

		JcAnotacao an = new JcAnotacao(Digits.class);
		an.addParametro("integers", a.digitsIntegers());
		an.addParametro("fraction", a.digitsFraction());

		JcDigits o = new JcDigits();
		o.set(an);
		return o;

	}

	public static JcDigits from(JcAnotacoes as) {
		JcAnotacao an = as.get(Digits.class);
		if (an == null) {
			return null;
		}
		JcDigits o = new JcDigits();
		o.set(an);
		return o;
	}

}
