package src.commom.utils.date;

import src.commom.utils.array.Itens;
import src.commom.utils.integer.IntegerCompare;

public class UAnoMes {

	private static Itens<Integer> MESES_31 = new Itens<>();
	static {
		MESES_31.add(1).add(3).add(5).add(7).add(8).add(10).add(12);
	}

	public static int getUltimoDiaDoMes(int ano, int mes) {
		if (IntegerCompare.eq(mes, 2)) {
			if (IntegerCompare.isZero(ano % 4)) {
				return 29;
			} else {
				return 28;
			}
		}
		if (UAnoMes.MESES_31.anyMatch(o -> IntegerCompare.eq(o, mes))) {
			return 31;
		} else {
			return 30;
		}
	}

}
