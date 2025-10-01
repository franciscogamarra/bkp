package gm.utils.number;

import java.io.Serializable;
import java.math.BigDecimal;

import lombok.Getter;
@Getter
public class Numeric3 extends Numeric<Numeric3> implements Serializable {

	private static final long serialVersionUID = -3672369795178806057L;

	public Numeric3(String s, boolean realBrasil) {
		super( (realBrasil ? s.replace(".", "").replace(",", ".") : "") , 3);
	}

	public Numeric3(String s) {
		super(s, 3);
	}

	public Numeric3(Integer value) {
		super(value, 3);
	}

	public Numeric3(int inteiros, int centavos) {
		super(inteiros, centavos, 3);
	}

	public Numeric3() {
		super(3);
	}

	public Numeric3(Double value) {
		super(value, 3);
	}

	public Numeric3(BigDecimal valor) {
		super(valor, 3);
	}

	public Numeric3 restoDivisao(int x) {
		return this.menos(this.dividido(x).inteiros() * x);
	}

	@Override
	protected Numeric3 newInstance() {
		return new Numeric3();
	}

}
