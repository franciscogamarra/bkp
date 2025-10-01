import ArrayLst from '../array/ArrayLst';
import StringCompare from '../string/StringCompare';

export default class FeriadoRecessoNaoFuncionamento {

	static tests = new ArrayLst();

	static addTest(test) {
		FeriadoRecessoNaoFuncionamento.tests.add(test);
	}

	static add(data) {
		FeriadoRecessoNaoFuncionamento.knows.add(data.format("[dd]/[mm]/[yyyy]"));
	}

	static test(data) {

		let ddmm = data.format("[dd]/[mm]");
		if (FeriadoRecessoNaoFuncionamento.defaults.anyMatch(s => StringCompare.eq(ddmm, s))) {
			return true;
		}

		let ddmmyyyy = data.format("[dd]/[mm]/[yyyy]");
		if (FeriadoRecessoNaoFuncionamento.knows.anyMatch(s => StringCompare.eq(ddmmyyyy, s)) || FeriadoRecessoNaoFuncionamento.tests.anyMatch(o => o.test(data))) {
			return true;
		}

		return false;

	}

}
FeriadoRecessoNaoFuncionamento.defaults = ArrayLst.build("01/01", "21/04", "01/05", "07/09", "12/10", "02/11", "15/11", "25/12");
FeriadoRecessoNaoFuncionamento.knows = ArrayLst.build("10/04/2020","11/06/2020");
