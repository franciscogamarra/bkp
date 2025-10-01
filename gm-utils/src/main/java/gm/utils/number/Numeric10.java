package gm.utils.number;

import java.math.BigDecimal;

import lombok.Getter;
@Getter
public class Numeric10 extends Numeric<Numeric10> {

	public Numeric10(String s) {
		super(s, 10);
	}

	public Numeric10(Integer value) {
		super(value, 10);
	}

	public Numeric10(int inteiros, int centavos) {
		super(inteiros, centavos, 10);
	}

	public Numeric10() {
		super(10);
	}

	public Numeric10(Double value) {
		super(value, 10);
	}

	public Numeric10(BigDecimal valor) {
		super(valor, 10);
	}

	@Override
	protected Numeric10 newInstance() {
		return new Numeric10();
	}

}
