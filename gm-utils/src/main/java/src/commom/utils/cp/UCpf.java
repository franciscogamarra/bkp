package src.commom.utils.cp;

import gm.utils.anotacoes.IgnorarDaquiPraBaixo;
import src.commom.utils.comum.Randomico;
import src.commom.utils.integer.IntegerCompare;
import src.commom.utils.integer.IntegerFormat;
import src.commom.utils.integer.IntegerParse;
import src.commom.utils.string.StringCompare;
import src.commom.utils.string.StringEmpty;
import src.commom.utils.string.StringExtraiNumeros;
import src.commom.utils.string.StringLength;
import src.commom.utils.string.StringReplace;

public class UCpf {

	public static String format(String s) {
		if (StringEmpty.is(s)) {
			return null;
		}
		s = StringExtraiNumeros.exec(s);
		int len = StringLength.get(s);
		if (len > 11) {
			s = s.substring(0, 11);
		} else {
			while (len < 11) {
				s = "0" + s;
				len++;
			}
		}
		return s.substring(0, 3) + "." + s.substring(3, 6) + "." + s.substring(6, 9) + "-" + s.substring(9);
	}

	public static String formatParcial(String cpf) {
		cpf = StringExtraiNumeros.exec(cpf);
		if (cpf.length() >= 11) {
			return format(cpf);
		}
		if (cpf.length() < 4) {
			return cpf;
		}
		String s = cpf.substring(0, 3) + ".";
		cpf = cpf.substring(3);

		if (cpf.length() < 4) {
			return s + cpf;
		}
		s += cpf.substring(0, 3) + ".";
		cpf = cpf.substring(3);

		if (cpf.length() < 4) {
			return s + cpf;
		}
		s += cpf.substring(0, 3) + "-";
		cpf = cpf.substring(3);
		if (cpf.length() > 2) {
			cpf = cpf.substring(0, 2);
		}
		s += cpf;
		return s;

	}

	public static boolean isValid(String cpf) {

		if (StringEmpty.is(cpf)) {
			return false;
		}

		cpf = StringExtraiNumeros.exec(cpf);

		if (!StringLength.is(cpf, 11)) {
			return false;
		}

		for (int i = 0; i < 10; i++) {
			String s = i + "";
			s = StringReplace.exec(cpf, s, "");
			if (StringEmpty.is(s)) {
				return false;
			}
		}

		String numDig = cpf.substring(0, 9);
		String digitoInformado = cpf.substring(9, 11);
		String digitoCalculado = calculaDigitoVerificador(numDig);

		if (!StringCompare.eq(digitoCalculado, digitoInformado)) {
			return false;
		}

		return true;
	}

	public static String calculaDigitoVerificador(String num) {

		int soma = 0;
		int peso = 10;
		for (int i = 0; i < num.length(); i++) {
			soma += IntegerParse.toInt(num.substring(i, i + 1)) * peso;
			peso--;
		}

		int primDig = 0;
		int resto = soma % 11;

		if (IntegerCompare.isZero(resto) || IntegerCompare.eq(resto, 1)) {
			primDig = 0;
		} else {
			primDig = IntegerParse.toInt(11 - resto);
		}

		soma = 0;
		peso = 11;

		for (int i = 0; i < num.length(); i++) {
			soma += IntegerParse.toInt(num.substring(i, i + 1)) * peso;
			peso--;
		}

		soma += primDig * 2;
		resto = soma % 11;

		int segDig = 0;
		if (IntegerCompare.isZero(resto) || IntegerCompare.eq(resto, 1)) {
			segDig = 0;
		} else {
			segDig = IntegerParse.toInt(11 - resto);
		}
		return "" + primDig + segDig;
	}

	public static String mock(int i) {
		
		if (i == 0) {
			throw new RuntimeException();
		}

		String s = IntegerFormat.zerosEsquerda(i, 9);
		int digito = 0;
		while (!isValid(s + IntegerFormat.xx(digito))) {
			digito++;
		}
		return format(s + IntegerFormat.xx(digito));

	}

	public static String aleatorio() {
		String s = Randomico.getIntString(9);
		s += calculaDigitoVerificador(s);
		return format(s);
	}

	@IgnorarDaquiPraBaixo
	
	public static void main(String[] args) {
		System.out.println(aleatorio());
//		for (int i = 1; i < 10; i++) {
//			SystemPrint.ln(mock(i));
//			SystemPrint.ln("insert into lce.lcetb002_apostador select '" + StringExtraiNumeros.exec(mock(i)) + "';");
//		}
		
		
	}
		
		
}