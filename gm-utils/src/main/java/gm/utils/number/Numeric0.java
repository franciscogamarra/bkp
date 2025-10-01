package gm.utils.number;

import java.math.BigDecimal;

import lombok.Getter;
@Getter
public class Numeric0 extends Numeric<Numeric0> {
	public static final Numeric0 ZERO = new Numeric0(0);

	public Numeric0(String s) {
		super(s, 0);
	}

	public Numeric0(Integer value) {
		super(value, 0);
	}

	public Numeric0(int inteiros, int centavos) {
		super(inteiros, centavos, 0);
	}

	public Numeric0() {
		super(0);
	}

	public Numeric0(Double value) {
		super(value, 0);
	}

	public Numeric0(BigDecimal valor) {
		super(valor, 0);
	}

	@Override
	protected Numeric0 newInstance() {
		return new Numeric0();
	}

}
