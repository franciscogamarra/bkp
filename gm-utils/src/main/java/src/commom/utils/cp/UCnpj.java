package src.commom.utils.cp;

import gm.utils.anotacoes.IgnorarDaquiPraBaixo;
import gm.utils.comum.SystemPrint;
import js.outros.Date;
import src.commom.utils.array.Itens;
import src.commom.utils.integer.IntegerParse;
import src.commom.utils.object.Null;
import src.commom.utils.string.StringCompare;
import src.commom.utils.string.StringEmpty;
import src.commom.utils.string.StringExtraiNumeros;
import src.commom.utils.string.StringLength;
import src.commom.utils.string.StringRight;

//  68.255.668/0000-32

public class UCnpj {

	public static boolean isValid(String cnpj) {

		String s = calcDigitos(cnpj);

		if (Null.is(s)) {
			return false;
		}
		cnpj = StringExtraiNumeros.exec(cnpj);
		return StringCompare.eq(cnpj, s);

	}

	private static String calcDigitos(String cnpj) {

		if (StringEmpty.is(cnpj)) {
			return null;
		}

		cnpj = StringExtraiNumeros.exec(cnpj);

		if (!StringLength.is(cnpj, 14)) {
			return null;
		}

		String semDigitos = cnpj.substring(0, 12);
		Integer digito1 = calc1(semDigitos);
		Integer digito2 = calc1(semDigitos + digito1);
		return semDigitos + digito1 + digito2;

	}

	private static String garante14caracteres(String cnpj) {
		cnpj = StringExtraiNumeros.exec(cnpj);
		if (StringLength.get(cnpj) > 14) {
			cnpj = cnpj.substring(0, 14);
		} else {
			while (!StringLength.is(cnpj, 14)) {
				cnpj = "0" + cnpj;
			}
		}
		return cnpj;
	}

	private static int countAleatorios = 0;

	public static String aleatorio() {

		long value = new Date().getTime() + (countAleatorios++);

		String s = "" + value;
		s = StringRight.get(s, 12);

		s = calcDigitos(s+"00");
		return format(s);

	}

//	61.495.858/0001-69
	public static String formatParcial(String cnpj) {
		cnpj = StringExtraiNumeros.exec(cnpj);
		if (cnpj.length() >= 14) {
			return format(cnpj);
		}
		if (cnpj.length() < 3) {
			return cnpj;
		}
		String s = cnpj.substring(0, 2) + ".";
		cnpj = cnpj.substring(2);

		if (cnpj.length() < 4) {
			return s + cnpj;
		}
		s += cnpj.substring(0, 3) + ".";
		cnpj = cnpj.substring(3);

		if (cnpj.length() < 4) {
			return s + cnpj;
		}
		s += cnpj.substring(0, 3) + "/";
		cnpj = cnpj.substring(3);

		if (cnpj.length() < 5) {
			return s + cnpj;
		}
		s += cnpj.substring(0, 4) + "-";
		cnpj = cnpj.substring(4);

		if (cnpj.length() > 2) {
			cnpj = cnpj.substring(0, 2);
		}
		s += cnpj;
		return s;

	}

	public static String format(String cnpj) {
		cnpj = StringExtraiNumeros.exec(cnpj);
		cnpj = garante14caracteres(cnpj);
		return cnpj.substring(0, 2) + "." + cnpj.substring(2, 5) + "." + cnpj.substring(5, 8) + "/" + cnpj.substring(8,12) + "-" + cnpj.substring(12);
	}

	public static boolean isComplete(String cnpj) {
		return StringLength.is(StringExtraiNumeros.exec(cnpj), 14);
	}

	private static final Itens<Integer> peso = Itens.build(
		6, 5, 4, 3, 2, 9, 8, 7, 6, 5, 4, 3, 2
	);

	private static int calc1(String str) {
		int soma = 0;
		for (int indice = str.length() - 1; indice >= 0; indice--) {
			int digito = IntegerParse.toInt(str.substring(indice, indice + 1));
			soma += digito * peso.get(peso.size() - str.length() + indice);
		}
		soma = 11 - soma % 11;
		return soma > 9 ? 0 : soma;
	}

	@IgnorarDaquiPraBaixo
	
	public static void main(String[] args) {

//		String s = calcDigitos("100000000000");
//		SystemPrint.ln(format(s));

		
//		console.log(isValid("58106519000139"));

		for (int i = 0; i < 10; i++) {
			String s = aleatorio();
			s = StringExtraiNumeros.exec(s);
			s = ", CnpjJson.json().cnpj(\"" + s + "\").name(\"CNPJ "+i+"\")";
			SystemPrint.ln(s);
		}
		
//		C:\opt\desen\gm\portal-do-cliente\src\components\structure-components\pages\client-pages\Attendance\NewTicket\NewTicket.js
		
//		68.255.668/0000-32
//		68255668000032

	}

}