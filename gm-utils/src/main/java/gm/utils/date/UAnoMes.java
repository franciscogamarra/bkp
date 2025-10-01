package gm.utils.date;

import src.commom.utils.integer.IntegerCompare;
import src.commom.utils.integer.IntegerParse;
import src.commom.utils.string.StringEmpty;

public class UAnoMes {

	public static int compare_mmmm_yyyy(String a, String b) {
		if (StringEmpty.is(a)) {
			if (StringEmpty.is(b)) {
				return 0;
			}
			return -1;
		}
		if (StringEmpty.is(b)) {
			return 1;
		}

		int inta = UAnoMes.mmmm_yyyy_toInt(a);
		int intb = UAnoMes.mmmm_yyyy_toInt(b);

		return IntegerCompare.compare(inta, intb);

	}

	public static int mmmm_yyyy_toInt(String s) {
		Data data = Data.unformat("[mmmm]/[yyyy]", s);
		return data.getAno() * 100 + data.getMes();
	}
	public static int idAnoMes(int ano, int mes) {
		return ano * 100 + mes;
	}
	public static int indexAnoMes(int ano, int mes) {
		return (ano - 1900) * 12 + mes;
	}
	public static int getAno(int anoMes) {
		return IntegerParse.toInt(anoMes / 100);
	}
	public static int getMes(int anoMes) {
		return anoMes - UAnoMes.getAno(anoMes) * 100;
	}

	public static int menosMeses(int anoMes, int meses) {
		int ano = UAnoMes.getAno(anoMes);
		int mes = UAnoMes.getMes(anoMes);
		mes -= meses;
		if (mes < 1) {
			ano--;
			mes += 12;
		}
		return UAnoMes.idAnoMes(ano, mes);
	}

	public static int maisMeses(int anoMes, int meses) {
		int ano = UAnoMes.getAno(anoMes);
		int mes = UAnoMes.getMes(anoMes);
		mes += meses;
		if (mes > 12) {
			ano++;
			mes -= 12;
		}
		return UAnoMes.idAnoMes(ano, mes);
	}

}
