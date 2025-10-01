import StringEmpty from './StringEmpty';
import UNomeProprio from './UNomeProprio';

export default class StringToNomeProprio {

	static exec(s, manterNumeros) {

		s = UNomeProprio.formatParcial(s, manterNumeros);

		if (StringEmpty.is(s)) {
			return null;
		}
		return s.trim();

	}

}
