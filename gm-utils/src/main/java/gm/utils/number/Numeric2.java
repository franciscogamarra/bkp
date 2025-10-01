package gm.utils.number;

import java.io.Serializable;
import java.math.BigDecimal;

import lombok.Getter;
@Getter
public class Numeric2 extends Numeric<Numeric2> implements Serializable {

	private static final long serialVersionUID = 1L;

	public Numeric2(String s, boolean realBrasil) {
		super( (realBrasil ? s.replace(".", "").replace(",", ".") : "") , 2);
	}

	public Numeric2(String s) {
		super(s, 2);
	}

	public Numeric2(Integer value) {
		super(value, 2);
	}
	
	public Numeric2(Numeric<?> value) {
		this(value.getValor());
	}

	public Numeric2(int inteiros, int centavos) {
		super(inteiros, centavos, 2);
	}

	public Numeric2() {
		super(2);
	}

	public Numeric2(Double value) {
		super(value, 2);
	}

	public Numeric2(BigDecimal valor) {
		super(valor, 2);
	}

	public Numeric2 restoDivisao(int x) {
		return menos(dividido(x).inteiros() * x);
	}

	public static void main(String[] args) {
		new Numeric2(15).percentual(8).print();
		new Numeric2(7).percentual(8).print();
		new Numeric2(8).percentual(8).print();
	}

	@Override
	protected Numeric2 newInstance() {
		return new Numeric2();
	}

}
