package gm.utils.number;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import org.nevec.rjm.BigDecimalMath;

import gm.utils.comum.ULog;
import lombok.Getter;
import lombok.Setter;
import src.commom.utils.integer.IntegerCompare;
import src.commom.utils.integer.IntegerFormat;
import src.commom.utils.integer.IntegerParse;
import src.commom.utils.string.StringAfterFirst;
import src.commom.utils.string.StringBeforeFirst;
import src.commom.utils.string.StringContains;
import src.commom.utils.string.StringEmpty;
import src.commom.utils.string.StringLength;
import src.commom.utils.string.StringRight;

@Getter
public abstract class Numeric<T extends Numeric<T>> {
	
	public static final RoundingMode ROUNDING_MODE_DEFAULT = RoundingMode.HALF_UP;

	@Setter
	private RoundingMode roundingMode = ROUNDING_MODE_DEFAULT;
	
	public RoundingMode getRoundingMode() {
		return roundingMode == null ? ROUNDING_MODE_DEFAULT : roundingMode;
	}

	BigDecimal valor;
	private int casas;

	public Numeric(int casas) {
		this.casas = casas;
		this.valor = BigDecimal.ZERO;
	}
	public Numeric(String s, int casas) {
		this( UBigDecimal.toBigDecimal(s, casas), casas );
	}
	public Numeric(int inteiros, int centavos, int casas) {
		this(inteiros + "." + IntegerFormat.zerosEsquerda(centavos, casas), casas);
	}
	public Numeric(BigDecimal valor, int casas) {
		this(casas);
		this.setValor(valor);
	}
	public Numeric(Double value, int casas) {
		this( UBigDecimal.toBigDecimal(value, casas), casas );
	}
	public Numeric(Integer value, int casas) {
		this( UBigDecimal.toBigDecimal(value, casas), casas );
	}
	public Numeric(Number value, int casas) {
		this( UBigDecimal.toBigDecimal(value, casas), casas );
	}
	
	@SuppressWarnings("unchecked")
	public T THIS(){
		return (T) this;
	}
	
	public T inc(Numeric<?> x){
		return this.inc(x.getValor());
	}
	public T dec(Numeric<?> x){
		return this.dec(x.getValor());
	}
	public T inc(Integer x){
		return add(UBigDecimal.toBigDecimal(x));
	}
	public T inc(Double x){
		return add(UBigDecimal.toBigDecimal(x));
	}
	public T add(Integer value){
		if (value == null) {
			return THIS();
		}
		BigDecimal money = UBigDecimal.toBigDecimal(value, this.casas);
		return this.add(money);
	}
	public T add(Double value){
		if (value == null) {
			return THIS();
		}
		BigDecimal money = UBigDecimal.toBigDecimal(value, this.casas);
		return add(money);
	}
	public T add(BigDecimal value) {
		if (value != null){
			this.valor = this.valor.add(value);
		}
		return THIS();
	}
	public T add(Numeric<?> o) {
		return this.add(o.valor);
	}
	public T menosIgual(Numeric<?> x) {
		return menosIgual(x.getValor());
	}
	public T menosIgual(BigDecimal valor){
		return setValor(menos(valor));
	}
	@Override
	public String toString() {

		if (this.isZeroOrEmpty()) {
			if (casas == 0) {
				return "0";
			} else {
				return "0," + IntegerFormat.zerosEsquerda(0, casas);
			}
		}

		String s = this.valor.toString().toLowerCase();

		if (StringContains.is(s, "e")) {
			s = this.toDouble().toString();
		}
		
		if (!s.contains(".")) {
			s += ".";
		}

		String ints = StringBeforeFirst.get(s, ".");
		s = "," + StringAfterFirst.get(s, ".");

		if (StringEmpty.is(ints)) {
			ints = "0";
		} else {
			while (ints.length() > 3) {
				s = "." + StringRight.get(ints, 3) + s;
				ints = StringRight.ignore(ints, 3);
			}
		}
		
		if (casas == 0) {
			return ints;
		}
		
		return ints + s;
	}

	public String toStringPonto() {
		String s = this.toString();
		if (casas == 0) {
			return s;
		}
		String ints = StringBeforeFirst.get(s, ",");
		String decimais = StringAfterFirst.get(s, ",");
		
		if (decimais == null) {
			decimais = "";
		}
		
		while (StringLength.get(decimais) < this.casas) {
			decimais += "0";
		}
		
		if (ints.isEmpty()) {
			ints = "0";
		}
		
		if (decimais.isEmpty()) {
			return ints;
		}

		decimais = StringLength.max(decimais, this.casas);

		return ints.replace(".", "") + "." + decimais;
	}

	@Override
	public int hashCode() {
		return Objects.hash(casas, valor);
	}

	@Override
	public boolean equals(Object o) {
		
		if (o == null) {
			return false;
		}
		
		if (this == o || this.valor == o) {
			return true;
		}
		
		if (o instanceof String) {
			String s = (String) o;
			return toString().contentEquals(s) || toStringPonto().contentEquals(s);
		}
		
		if (o instanceof Number) {
			Number n = (Number) o;
			T ni = newInstance();
			ni.setValor(n.doubleValue());
			return toString().contentEquals(ni.toString());
		}
		
		if (o instanceof Numeric) {
			
			Numeric<?> n = (Numeric<?>) o;
			
			if (n.valor.equals(valor)) {
				return true;
			}

			T ni = newInstance();
			ni.setValor(n);
			return toString().contentEquals(ni.toString());
			
		}
		
		String s = o.toString();
		return toString().contentEquals(s) || toStringPonto().contentEquals(s);
		
	}
	
	public T dividido(GetNumeric valor) {
		return this.dividido(valor.valor());
	}
	public T dividido(Double valor) {
		return this.dividido( UBigDecimal.toBigDecimal(valor, this.casas) );
	}
	public T dividido(Numeric<?> valor) {
		return this.dividido(valor.valor);
	}
	public T dividido(int peso) {
		if (this.isZero()) {
			return this.newT(0.);
		}
		return this.dividido( UBigDecimal.toBigDecimal(peso, this.casas) );
	}

	public T dividido(BigDecimal divisor) {
		BigDecimal resultado = this.valor.divide(divisor, getRoundingMode());
		return this.newT(resultado);
	}
	public T setValor(Double valor){
		return setValor(UBigDecimal.toBigDecimal(valor, casas));
	}
	public T setValor(BigDecimal valor){
		if (valor == null) {
			valor = BigDecimal.ZERO;
		} else {
			valor = valor.setScale(this.casas, getRoundingMode());
		}
		this.valor = valor;
		return THIS();
	}
	public T setValor(Integer valor){
		return setValor(UBigDecimal.toMoney(valor));
	}
	public T setValor(Numeric<?> valor){
		return setValor(valor.getValor());
	}
	public Double toDouble(){
		return valor.doubleValue();
	}
	public Integer toInt() {
		return valor.intValue();
	}
	public boolean menor(Numeric<?> x) {
		return this.menor(x.getValor());
	}
	public boolean maior(Numeric<?> x) {
		return this.maior(x.getValor());
	}
	public boolean menor(GetNumeric x) {
		return this.menor(x.valor());
	}
	public boolean maior(GetNumeric x) {
		return this.maior(x.valor());
	}
	public boolean menor(Integer x) {
		return this.menor(UBigDecimal.toBigDecimal(x, this.casas));
	}
	public boolean menor(BigDecimal x) {
		return this.valor.compareTo(x) < 0;
	}
	public boolean maior(Double x) {
		return this.maior( UBigDecimal.toBigDecimal(x, this.casas) );
	}
	public boolean maior(Long x) {
		return this.maior( UBigDecimal.toBigDecimal(x, this.casas) );
	}
	public boolean maior(Integer x) {
		return this.maior( UBigDecimal.toBigDecimal(x, this.casas) );
	}
	public boolean menorOuIgual(GetNumeric x) {
		return this.menorOuIgual(x.valor());
	}
	public boolean menorOuIgual(BigDecimal x) {
		return this.eq(x)||this.menor(x);
	}
	public boolean menorOuIgual(Integer x) {
		return this.eq(x)||this.menor(x);
	}
	public boolean entre(Integer x, Integer y) {
		return this.maiorOuIgual(IntegerCompare.getMenor(x, y)) && this.menorOuIgual(IntegerCompare.getMaior(x, y));
	}
	public boolean entre(Numeric<?> x, Numeric<?> y) {
		return this.maiorOuIgual(x) && this.menorOuIgual(y);
	}
	public boolean entre(BigDecimal x, BigDecimal y) {
		return this.maiorOuIgual(x) && this.menorOuIgual(y);
	}
	public boolean entreNotNull(BigDecimal x, BigDecimal y) {
		return x != null && y != null && entre(x, y);
	}
	public boolean maior(BigDecimal x) {
		return this.valor.compareTo(x) > 0;
	}
	public boolean maiorOuIgual(Numeric<?> x) {
		return this.maiorOuIgual(x.getValor());
	}
	public boolean maiorOuIgual(GetNumeric x) {
		return this.maiorOuIgual(x.valor().getValor());
	}
	public boolean menorOuIgual(Numeric<?> x) {
		return this.menorOuIgual(x.getValor());
	}
	public boolean maiorOuIgual(Double x) {
		return this.maiorOuIgual( UBigDecimal.toBigDecimal(x, this.casas) );
	}
	public boolean maiorOuIgual(Integer x) {
		return this.maiorOuIgual( UBigDecimal.toBigDecimal(x, this.casas) );
	}
	public boolean maiorOuIgual(BigDecimal x) {
		return this.maior(x) || this.eq(x);
	}
	public boolean eq(Integer x) {
		return this.eq(this.newT(x));
	}
	public boolean eq(Double x) {
		return this.eq(this.newT(x));
	}
	public boolean eq(Numeric<?> x) {
		return this.eq(x.getValor());
	}
	public boolean ne(Numeric<?> x) {
		return !this.eq(x);
	}
	public boolean ne(GetNumeric x) {
		return this.ne(x.valor());
	}
	public boolean ne(Integer x) {
		return !this.eq(x);
	}
	public boolean ne(BigDecimal x) {
		return !this.eq(x);
	}
	public boolean eq(BigDecimal x) {
		if (x == null) {
			return this.isZero();
		}
		return this.valor.compareTo(x) == 0;
	}
	public T print() {
		ULog.debug(this + " / " + this.getValor());
		return THIS();
	}
	public T vezes(Numeric<?> x) {
		return this.vezes(x.valor);
	}
	public T vezes(BigDecimal x) {
		x = this.valor.multiply(x);
		return this.newT(x);
	}
	public T vezes(Double x) {
		BigDecimal b = UBigDecimal.toBigDecimal(x, this.casas);
		return this.vezes(b);
	}
	public T vezes(Integer x) {
		BigDecimal b = UBigDecimal.toBigDecimal(x, this.casas);
		return this.vezes(b);
	}
	public T divididoIgual(Integer i) {
		this.valor = this.dividido(i).valor;
		return THIS();
	}
	public T vezesIgual(Integer i) {
		this.valor = this.vezes(i).valor;
		return THIS();
	}
	public T vezesIgual(Numeric<?> x) {
		this.valor = this.vezes(x).valor;
		return THIS();
	}
	public boolean menor(Double valor) {
		return this.menor( this.newT(valor) );
	}
	public T newT(Integer x){
		BigDecimal b = UBigDecimal.toBigDecimal(x, this.casas);
		return this.newT(b);
	}
	public T newT(Double v){
		BigDecimal b = UBigDecimal.toBigDecimal(v, this.casas);
		return this.newT(b);
	}
	public T newT(BigDecimal v){
		BigDecimal b = UBigDecimal.toBigDecimal(v, this.casas);
		T t = newInstance();
		t.setValor(b);
		return t;
	}

	protected abstract T newInstance();

	@SuppressWarnings("unchecked")
	public T menos(T... values) {
		BigDecimal o = this.valor;
		for (T v : values) {
			o = o.subtract(v.valor);
		}
		return this.newT(o);
	}

	private List<T> toList(GetNumeric... values) {
		List<T> list = new ArrayList<>();
		for (GetNumeric v : values) {
			Numeric<?> x = v.valor();
			if (x == null) {
				continue;
			}
			BigDecimal y = x.getValor();
			if (y == null) {
				continue;
			}
			T t = this.newT(y);
			list.add(t);
		}
		return list;
	}

	public T menos(GetNumeric... values) {
		return this.menos( this.toList(values) );
	}
	public T mais(GetNumeric... values) {
		return this.mais( this.toList(values) );
	}

	public T menos(BigDecimal... values) {
		List<T> valores = new ArrayList<>();
		for (BigDecimal o : values) {
			if (o != null) {
				valores.add(this.newT(o));
			}
		}
		return this.menos(valores);
	}

	public T mais(Double d) {
		return this.mais( UBigDecimal.toBigDecimal(d, this.casas) );
	}

	public T mais(BigDecimal... values) {
		List<T> valores = new ArrayList<>();
		for (BigDecimal o : values) {
			if (o != null) {
				valores.add(this.newT(o));
			}
		}
		return this.mais(valores);
	}

	public T menos(List<T> valores) {
		if (valores.isEmpty()) {
			return this.newT(this.getValor());
		}
		BigDecimal o = this.valor;
		for (T v : valores) {
			if (v == null || v.valor == null || v.isZero()) {
				continue;
			}
			o = o.subtract(v.valor);
		}
		return this.newT(o);
	}

	public T mais(List<T> valores) {
		if (valores.isEmpty()) {
			return this.newT(this.getValor());
		}
		BigDecimal o = this.valor;
		for (T v : valores) {
			if (v == null || v.valor == null || v.isZero()) {
				continue;
			}
			o = o.add(v.valor);
		}
		return this.newT(o);
	}

	@SuppressWarnings("unchecked")
	public T mais(T... values) {
		BigDecimal o = this.valor;
		for (T v : values) {
			if (v == null || v.valor == null) {
				continue;
			}
			o = o.add(v.valor);
		}
		return this.newT(o);
	}
	public T menos(Integer x) {
		return this.newT( this.getValor().subtract( UBigDecimal.toBigDecimal(x) ) );
	}
	public T mais(Integer x) {
		return this.newT( this.getValor().add( UBigDecimal.toBigDecimal(x) ) );
	}
	public T dec(Double d) {
		return this.dec(this.newT(d));
	}
	public T dec(Integer x) {
		return this.dec(this.newT(x));
	}
	@SuppressWarnings("unchecked")
	public T dec(BigDecimal o) {
		if (o != null){
			this.valor = this.valor.subtract(o);
		}
		return (T) this;
	}
	@SuppressWarnings("unchecked")
	public T inc(BigDecimal o) {
		if (o != null){
			this.valor = this.valor.add(o);
		}
		return (T) this;
	}

	private boolean isZeroOrEmpty() {
		return this.valor == null || BigDecimal.ZERO.equals(this.valor);
	}

	public boolean isZero() {
		if ( isZeroOrEmpty() || toString().replace("0", "").replace(",", "").isEmpty() ) {
			return true;
		}
		return false;
	}

	public T comoPercentualDe(Object valor) {
		return this.comoPercentualDe( UBigDecimal.toMoney(valor) );
	}
	public T comoPercentualDe(BigDecimal valor) {
		Numeric5 n = new Numeric5(valor);
		n = n.dividido(100).vezes(this);
		return this.newT( n.getValor() );
	}
	public T centavos(){
		return this.menos(this.inteiros());
	}

	public Integer inteiros(){
		String s = this.getValor().toString();
		if (StringContains.is(s, ".")) {
			s = StringBeforeFirst.get(s, ".");
		}
		return IntegerParse.toInt(s);
	}

	public T percentual(int x) {
		return this.vezes(x / 100.);
	}

	@SuppressWarnings("unchecked")
	public T menosPercentual(int percentual) {
		T x = this.percentual(percentual);
		return this.menos(x);
	}
	public T menosIgualPercentual(int percentual) {
		T x = this.menosPercentual(percentual);
		return setValor(x.getValor());
	}
	public T pow(Numeric<?> valor) {
		return pow(valor.getValor());
	}
	public T pow(BigDecimal valor) {
		return newT(BigDecimalMath.pow(getValor(), valor));
	}
	public T pow(Integer valor) {
		return pow(new Numeric15(valor));
	}
	public static Numeric<?> toNumeric(BigDecimal o, int precision) {
		if (precision == 0) {
			return new Numeric0(o);
		}
		if (precision == 1) {
			return new Numeric1(o);
		}
		if (precision == 2) {
			return new Numeric2(o);
		}
		if (precision == 3) {
			return new Numeric3(o);
		}
		if (precision == 4) {
			return new Numeric4(o);
		}
		if (precision == 5) {
			return new Numeric5(o);
		}
		if (precision == 6) {
			return new Numeric6(o);
		}
		if (precision == 8) {
			return new Numeric8(o);
		}
		if (precision == 10) {
			return new Numeric10(o);
		}
		if (precision == 15) {
			return new Numeric15(o);
		}
		if (precision == 16) {
			return new Numeric16(o);
		}
		if (precision == 18) {
			return new Numeric18(o);
		}
		throw new RuntimeException("precision nao tratada: " + precision);
	}
	
	public static Numeric<?> toNumeric(Double o, int precision) {
		return toNumeric(BigDecimal.valueOf(o), precision);
	}

	public static Numeric<?> toNumeric(int o, int precision) {
		return toNumeric(UBigDecimal.toBigDecimal(o), precision);
	}

	public static Numeric<?> toNumeric(BigDecimal o) {
		return toNumeric(o, o.scale());
	}

}
