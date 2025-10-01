import ArrayLst from '../array/ArrayLst';
import IntegerCompare from '../integer/IntegerCompare';

export default class UAnoMes {

	static MESES_31 = ArrayLst.build(1,3,5,7,8,10,12);

	static getUltimoDiaDoMes(ano, mes) {
		if (IntegerCompare.eq(mes, 2)) {
			if (IntegerCompare.isZero(ano % 4)) {
				return 29;
			} else {
				return 28;
			}
		}
		if (UAnoMes.MESES_31.anyMatch(o => IntegerCompare.eq(o, mes))) {
			return 31;
		} else {
			return 30;
		}
	}

}
