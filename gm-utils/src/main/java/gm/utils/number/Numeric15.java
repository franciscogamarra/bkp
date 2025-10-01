package gm.utils.number;

import java.math.BigDecimal;

import lombok.Getter;
@Getter
public class Numeric15 extends Numeric<Numeric15> {

	public Numeric15(String s) {
		super(s, 15);
	}

	public Numeric15(Integer value) {
		super(value, 15);
	}

	public Numeric15(int inteiros, int centavos) {
		super(inteiros, centavos, 15);
	}

	public Numeric15() {
		super(15);
	}

	public Numeric15(Double value) {
		super(value, 15);
	}

	public Numeric15(BigDecimal valor) {
		super(valor, 15);
	}

	@Override
	protected Numeric15 newInstance() {
		return new Numeric15();
	}

}
