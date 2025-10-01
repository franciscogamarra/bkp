package js.decimaljs;

import java.math.RoundingMode;

import gm.languages.ts.javaToTs.annotacoes.From;
import gm.languages.ts.javaToTs.annotacoes.ImportStatic;
import gm.utils.number.Numeric;
import gm.utils.number.Numeric8;

@ImportStatic
@From("decimal.js")
//npm install decimal.js
//npm install --save-dev @types/decimal.js
//https://mikemcl.github.io/decimal.js/
//https://ellenaua.medium.com/floating-point-errors-in-javascript-node-js-21aadd897bf8
//https://github.com/MikeMcl/decimal.js
public class Decimal {
	
	public static final RoundingMode ROUND_HALF_UP = RoundingMode.HALF_UP;
	public static final RoundingMode ROUND_UP = RoundingMode.UP;
	public static final RoundingMode ROUND_DOWN = RoundingMode.DOWN;

	private final Numeric<?> v;
	
	private Decimal(Numeric<?> v) {
		this.v = v;
	}
	
	public Decimal(String value) {
		this(new Numeric8(value));
	}

	public Decimal(Integer value) {
		this(new Numeric8(value));
	}

	public Decimal(Number value) {
		this(new Numeric8(value));
	}
	
	public Decimal plus(Decimal value) {
		return new Decimal(v.mais(value.v.getValor()));
	}

	public Decimal minus(Decimal value) {
		return new Decimal(v.menos(value.v.getValor()));
	}

	public Decimal times(Decimal value) {
		return new Decimal(v.vezes(value.v));
	}

	public Decimal dividedBy(Decimal value) {
		return new Decimal(v.dividido(value.v));
	}
	
	public boolean gt(Decimal valor) {
		return v.maior(valor.v);
	}

	public boolean lt(Decimal valor) {
		return v.menor(valor.v);
	}

	public Decimal toDecimalPlaces(int casas, RoundingMode roundingMode) {
		Numeric<?> numeric = Numeric.toNumeric(0, casas);
		numeric.setRoundingMode(roundingMode);
		numeric.setValor(v);
		return new Decimal(numeric);
	}
	
	public Double toNumber() {
		return v.toDouble();
	}

	public boolean eq(Decimal valor) {
		return v.eq(valor.v);
	}
	
	@Override
	public String toString() {
		return v.toStringPonto();
	}
	

}
