package gm.utils.number;

import java.math.BigDecimal;

import lombok.Getter;
@Getter
public class Numeric8 extends Numeric<Numeric8> {

	public Numeric8(String s) {
		super(s, 8);
	}

	public Numeric8(Integer value) {
		super(value, 8);
	}

	public Numeric8(int inteiros, int centavos) {
		super(inteiros, centavos, 8);
	}

	public Numeric8() {
		super(8);
	}

	public Numeric8(Double value) {
		super(value, 8);
	}

	public Numeric8(BigDecimal valor) {
		super(valor, 8);
	}
	
	public Numeric8(Number value) {
		super(value, 8);
	}

	@Override
	protected Numeric8 newInstance() {
		return new Numeric8();
	}

}
