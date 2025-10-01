package gm.utils.number;

import java.math.BigDecimal;

import lombok.Getter;
@Getter
public class Numeric18 extends Numeric<Numeric18> {

	public Numeric18(String s) {
		super(s, 18);
	}

	public Numeric18(Integer value) {
		super(value, 18);
	}

	public Numeric18(int inteiros, int centavos) {
		super(inteiros, centavos, 18);
	}

	public Numeric18() {
		super(18);
	}

	public Numeric18(Double value) {
		super(value, 18);
	}

	public Numeric18(BigDecimal valor) {
		super(valor, 18);
	}

	@Override
	protected Numeric18 newInstance() {
		return new Numeric18();
	}

}
