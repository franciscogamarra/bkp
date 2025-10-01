package src.commom.utils.date;

import gm.utils.lambda.F1;
import src.commom.utils.array.Itens;
import src.commom.utils.string.StringCompare;

public class FeriadoRecessoNaoFuncionamento {

	private static final Itens<String> nacionaisFixos = new Itens<>();
	private static final Itens<String> nacionaisMoveis = new Itens<>();
	
	static {
		nacionaisFixos.add("01/01").add("21/04").add("01/05").add("07/09").add("12/10").add("02/11").add("15/11").add("25/12");
		nacionaisMoveis.add("10/04/2020").add("11/06/2020").add("15/02/2021").add("16/02/2021").add("02/04/2021").add("03/06/2021").add("15/04/2022");
	}

	private static Itens<F1<BaseData, Boolean>> tests = new Itens<>();

	public static void addTest(F1<BaseData, Boolean> test) {
		tests.add(test);
	}

	public static void add(BaseData data) {
		nacionaisMoveis.add(data.format("[dd]/[mm]/[yyyy]"));
	}

	public static boolean test(BaseData data) {

		String ddmm = data.format("[dd]/[mm]");
		if (nacionaisFixos.anyMatch(s -> StringCompare.eq(ddmm, s))) {
			return true;
		}

		String ddmmyyyy = data.format("[dd]/[mm]/[yyyy]");
		if (nacionaisMoveis.anyMatch(s -> StringCompare.eq(ddmmyyyy, s)) || tests.anyMatch(o -> o.call(data))) {
			return true;
		}

		return false;

	}

}
