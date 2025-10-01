package gm.utils.number;

import java.math.BigDecimal;

import lombok.Getter;
@Getter
public class Numeric16 extends Numeric<Numeric16> {

	public Numeric16(String s) {
		super(s, 16);
	}

	public Numeric16(Integer value) {
		super(value, 16);
	}

	public Numeric16(int inteiros, int centavos) {
		super(inteiros, centavos, 16);
	}

	public Numeric16() {
		super(16);
	}

	public Numeric16(Double value) {
		super(value, 16);
	}

	public Numeric16(BigDecimal valor) {
		super(valor, 16);
	}

	@Override
	protected Numeric16 newInstance() {
		return new Numeric16();
	}

}
