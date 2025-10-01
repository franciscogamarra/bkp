package gm.utils.date;

import java.util.ArrayList;
import java.util.List;

import gm.utils.string.ListString;

public class UFeriadoRecessoNaoFuncionamento {

	private static List<Test> tests;

	public interface Test {
		boolean test(Data data);
	}

	public static void addTest(Test test) {
		if (tests == null) {
			tests = new ArrayList<>();
		}
		tests.add(test);
	}

	private static final ListString defaults = ListString.array("01/01", "21/04", "01/05", "07/09", "12/10", "02/11", "15/11", "25/12");
	private static final ListString knows = ListString.array("10/04/2020","11/06/2020","15/02/2021","16/02/2021","02/04/2021","03/06/2021","15/04/2022");

	public static boolean test(Data data) {
		if (defaults.contains(data.format("[dd]/[mm]")) || knows.contains(data.format("[dd]/[mm]/[yyyy]"))) {
			return true;
		}
		if (tests == null) {
			return false;
		}
		for (Test test : tests) {
			if (test.test(data)) {
				return true;
			}
		}
		return false;
	}
}
