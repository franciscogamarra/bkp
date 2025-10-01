package br.sicoob.src.app.funcionalidades.proposta__credito__bndes.calculos;

import br.sicoob.SicoobTranspilar;
import br.sicoob.src.app.shared.forms.StateFormControl;
import br.sicoob.src.app.shared.utils.Utils;
import gm.utils.anotacoes.IgnorarDaquiPraBaixo;
import gm.utils.lambda.F1;
import js.array.Array;
import js.decimaljs.Decimal;
import js.support.console;
import src.commom.utils.comum.SeparaMilhares;
import src.commom.utils.object.Null;
import src.commom.utils.string.StringAfterFirst;
import src.commom.utils.string.StringBeforeFirst;
import src.commom.utils.string.StringContains;
import src.commom.utils.string.StringEmpty;

public class Num {
	
	private static final int ARREDONDAR_POR_PROXIMIDADE = 0;
	private static final int ARREDONDAR_PARA_CIMA = 1;
	private static final int ARREDONDAR_PARA_BAIXO = 2;

	/* indica o roundMode default para quando da criação de novas instâncias */
	public static int ROUND_MODE_DEFAULT = ARREDONDAR_POR_PROXIMIDADE;
	
	private int roundMode = ROUND_MODE_DEFAULT;

	/* constantes de valores comuns para facilitar a utilização */
	public static final Num ZERO = Num.fromNumber(0, 0).imutavel();
	public static final Num UM_CENTAVO = Num.fromString(2, "0.01").imutavel();
	public static final Num UM = Num.fromNumber(0, 1).imutavel();
	public static final Num DEZ = Num.fromNumber(0, 10).imutavel();
	public static final Num CEM = Num.fromNumber(0, 100).imutavel();
	public static final Num MIL = Num.fromNumber(0, 1000).imutavel();

	/* indica a quantidade máxima de casas decimais do objeto */
	public int casas;

	/* caso seja setado como verdadeiro, impedirá via exception uma tentativa de alteração de valor */
	private boolean immutable = false;
	
	/* esta variavel existe para facilitar a inspeção do elemento, pois o tipo decimal não é tão legível */
	public String text;
	
	private Decimal valor;
	
	private Num(int casas, Num valorInicial) {
		this.casas = casas;
		if (Null.is(valorInicial)) {
			setValue(new Decimal("0.0"));
		} else {
			setValue(valorInicial.valor);
		}
	}
	

	public Num imutavel() {
		immutable = true;
		return this;
	}
	
	/* constructors */
	
	public static Num fromString(int casas, String s) {
		
		Num o = new Num(casas, null);
		
		if (!StringEmpty.is(s)) {
			if (s.startsWith("R$ ")) {
				s = s.substring(3);
				if (StringEmpty.is(s)) {
					return o;
				}
			}
		}
		
		o.setValue(new Decimal(s));
		
		return o;
		
	}
	
	public static Num fromNumber(int casas, Number valor) {
		Num o = new Num(casas, null);
		o.setValue(new Decimal(valor));
		return o;
	}
	
	public static Num fromNumberObj(int casas, Object o) {
		return Num.fromNumber(casas, Utils.toDouble(o));
	}

	public static Num fromState(int casas, StateFormControl o) {
		return Num.fromNumberObj(casas, o.get());
	}
	
	public static Num novo(int casas) {
		return new Num(casas, ZERO);
	}
	
	/* publics */
	
	public Num mais(Num value) {
		if (Null.is(value)) {
			value = ZERO;
		}
		int casas = getMaiorCasa(this, value);
		Num a = new Num(casas, this);
		Num b = new Num(casas, value);
		a.setValue(a.valor.plus(b.valor));
		return a;
	}
	
	public void maisIgual(Num value) {
		if (immutable) {
			throw new Error("immutable");
		}
		setValue(mais(value).valor);
	}

	public void menosIgual(Num value) {
		if (immutable) {
			throw new Error("immutable");
		}
		setValue(menos(value).valor);
	}
	
	private static int getMaiorCasa(Num a, Num b) {
		return a.casas > b.casas ? a.casas : b.casas;
	}
	
	public Num menos(Num value) {
		if (Null.is(value)) {
			value = ZERO;
		}
		int casas = getMaiorCasa(this, value);
		Num a = new Num(casas, this);
		Num b = new Num(casas, value);
		a.setValue(a.valor.minus(b.valor));
		return a;
	}
	
	private static int getSomaCasas(Num a, Num b) {
		int cs = a.casas + b.casas;
		return cs > 8 ? 8 : cs;
	}

	public Num vezes(Num value) {
		int cs = getSomaCasas(this, value);
		Num a = new Num(cs, this);
		Num b = new Num(cs, value);
		a.setValue(a.valor.times(b.valor));
		return a;
	}

	public Num dividido(Num value) {
		int cs = getSomaCasas(this, value);
		Num a = new Num(cs, this);
		Num b = new Num(cs, value);
		a.setValue(a.valor.dividedBy(b.valor));
		return a;
	}
	
	public boolean maior(Num value) {
		return valor.gt(value.valor);
	}

	public boolean menor(Num value) {
		return valor.lt(value.valor);
	}

	public boolean menorOuIgual(Num value) {
		return menor(value) || igual(value);
	}
	
	public boolean maiorQueZero() {
		return maior(ZERO);
	}

	public boolean menorQueZero() {
		return menor(ZERO);
	}

	public boolean igual(Num value) {
		if (Null.is(value)) {
			return false;
		} else {
			return valor.eq(value.valor);
		}
	}
	
	public boolean isZero() {
		return igual(ZERO);
	}
	
	/* privates */
	
	public void set(Num value) {
		setValue(value.valor);
	}
	
	private void setValue(Decimal value) {
		
		if (roundMode == ARREDONDAR_PARA_BAIXO) {
			this.valor = value.toDecimalPlaces(casas, Decimal.ROUND_DOWN);
		} else if (roundMode == ARREDONDAR_PARA_CIMA) {
			this.valor = value.toDecimalPlaces(casas, Decimal.ROUND_UP);
		} else if (roundMode == ARREDONDAR_POR_PROXIMIDADE) {
			this.valor = value.toDecimalPlaces(casas, Decimal.ROUND_HALF_UP);
		} else {
			throw new Error("modo de arredondamento nao implementado: " + roundMode);
		}
		
		this.text = toStringPrivate();
		
	}
	
	/* methods */
	
	public static <T> Num sum(Array<T> array, F1<T, Num> get) {
		
		if (Null.is(array) || array.lengthArray() == 0) {
			return ZERO;
		}

		Num soma = ZERO;
		
		for (int i = 0; i < array.lengthArray(); i++) {
			T item = array.array(i);
			if (Null.is(item)) {
				continue;
			}
			Num valor = get.call(item);
			if (Null.is(valor)) {
				continue;
			}
			
			soma = valor.mais(soma);
			
		}
		
		return soma;
		
	}

	@Override
	public Num clone() {
		return mais(ZERO);
	}

	public void assertCasas(int casasEsperadas) {
		if (casas != casasEsperadas) {
			throw new Error("casas != casasEsperadas: casas: " + casas + ", esperadas: " + casasEsperadas);
		}
	}
	
	public static <T> Num somaNumbers(int casas, Array<T> array, F1<T, Number> get) {
		
		Num soma = fromNumber(casas, 0);
		
		for (int i = 0; i < array.lengthArray(); i++) {
			T item = array.array(i);
			if (Null.is(item)) {
				continue;
			}
			Number value = get.call(item);
			if (Null.is(value)) {
				continue;
			}
			Num n = fromNumber(casas, value);
			soma = soma.mais(n);
		}
		
		return soma;
		
	}

	/* configs */

	public Num arredondarPorProximidade() {
		roundMode = ARREDONDAR_POR_PROXIMIDADE;
		return this;
	}

	public Num setArredondarParaCima() {
		roundMode = ARREDONDAR_PARA_CIMA;
		return this;
	}

	public Num arredondarParaBaixo(int casas) {
		Num v = new Num(casas, ZERO);
		v.roundMode = ARREDONDAR_PARA_BAIXO;
		v.set(this);
		return v;
	}

	public Num arredondarParaCima(int casas) {
		Num v = new Num(casas, ZERO);
		v.roundMode = ARREDONDAR_PARA_CIMA;
		v.set(this);
		return v;
	}
	
	public static void arredondarPorProximidadeDefault() {
		ROUND_MODE_DEFAULT = ARREDONDAR_POR_PROXIMIDADE;
	}

	public static void arredondarParaCimaDefault() {
		ROUND_MODE_DEFAULT = ARREDONDAR_PARA_CIMA;
	}

	public static void arredondarParaBaixoDefault() {
		ROUND_MODE_DEFAULT = ARREDONDAR_PARA_BAIXO;
	}

	/* outputs */
	
	public String formatRs() {
		return "R$ " + toString();
	}

	public String formatPer() {
		return toString() + "%";
	}
	
	@Override
	public String toString() {
		return text;
	}
	
	private String toStringPrivate() {
		
		String s = valor.toString();
		
		String ints;
		String cents;
		
		if (StringContains.is(s, ".")) {
			ints = StringBeforeFirst.get(s, ".");
			cents = StringAfterFirst.get(s, ".");
		} else {
			ints = s;
			cents = "";
		}
		
		ints = SeparaMilhares.exec(ints);
		
		if (casas == 0) {
			return ints;
		}
		
		while (cents.length() < casas) {
			cents += "0";
		}
		
		return ints + "," + cents;

	}
	
	public Double toDouble() {
		return valor.toNumber();
	}

	public void print() {
		console.log(toString());
	}
	
	public int compare(Num o) {
		if (maior(o)) {
			return 1;
		} else if (menor(o)) {
			return -1;
		} else {
			return 0;
		}
	}

	@IgnorarDaquiPraBaixo

	public static void main(String[] args) {
		

//		Num valorFinanciado = fromString(2, "7.500,00");
//		Num valorFinanciado = fromString(2, "12.500,00");
//		Num somaDosItens = fromString(2, "15.000,00");
		
//		somaDosItens.dividido(valorFinanciado).print();
//		valorFinanciado.dividido(somaDosItens).print();
		
//		calculo.getValorFinanciadoForm();
//		calculo.getSomaDosItensFinanciaveis();
		
		
		SicoobTranspilar.exec(Num.class);
	}


}
