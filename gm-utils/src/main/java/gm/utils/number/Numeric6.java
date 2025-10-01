package gm.utils.number;

import java.math.BigDecimal;

import lombok.Getter;
@Getter
public class Numeric6 extends Numeric<Numeric6> {

	public Numeric6(String s) {
		super(s, 6);
	}

	public Numeric6(Integer value) {
		super(value, 6);
	}

	public Numeric6(int inteiros, int centavos) {
		super(inteiros, centavos, 6);
	}

	public Numeric6() {
		super(6);
	}

	public Numeric6(Double value) {
		super(value, 6);
	}

	public Numeric6(BigDecimal valor) {
		super(valor, 6);
	}

	public Numeric6(Numeric<?> n) {
		this(n.getValor());
	}

	@Override
	protected Numeric6 newInstance() {
		return new Numeric6();
	}
	
}
