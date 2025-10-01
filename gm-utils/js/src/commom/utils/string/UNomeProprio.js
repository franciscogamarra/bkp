import StringBox from './StringBox';
import StringConstants from './StringConstants';
import StringContains from './StringContains';
import StringEmpty from './StringEmpty';
import StringExtraiCaracteres from './StringExtraiCaracteres';
import StringLength from './StringLength';
import StringPrimeiraMaiuscula from './StringPrimeiraMaiuscula';
import StringReplace from './StringReplace';
import StringTrim from './StringTrim';

export default class UNomeProprio {

	static VALIDOS_SEM_NUMEROS = StringConstants.MAIUSCULAS.concat(StringConstants.MINUSCULAS).add(StringConstants.aspa_simples).add(" ");

	static formatParcial(s, manterNumeros) {

		if (StringEmpty.is(s)) {
			return "";
		}

		s = StringReplace.exec(s, "\t", " ");

		let espaco = s.endsWith(" ");
		s = StringTrim.plus(s);

		while (!StringEmpty.is(s) && s.startsWith("'")) {
			s = s.substring(1);
			s = StringTrim.plus(s);
		}

		if (StringEmpty.is(s)) {
			return "";
		}

		s = s.toLowerCase();

		let validos = manterNumeros ? UNomeProprio.VALIDOS_COM_NUMEROS : UNomeProprio.VALIDOS_SEM_NUMEROS;

		s = StringExtraiCaracteres.exec(s, validos);

		if (StringEmpty.is(s)) {
			return "";
		}

		let box = new StringBox(s);

		UNomeProprio.VALIDOS_SEM_NUMEROS.forEach(o => box.replace(o+o+o, o+o));

		box.replace("''", "'");
		box.set(" " + box + " ");

		StringConstants.MINUSCULAS.forEach(o =>
			box.set(StringReplace.exec(box.get(), " " + o, " " + o.toUpperCase()))
		);

		StringConstants.PREPOSICOES_NOME_PROPRIO.forEach(x => box.replace(" " + StringPrimeiraMaiuscula.exec(x) + " ", " " + x + " "));
		StringConstants.ARTIGOS.forEach(x => box.replace(" " + StringPrimeiraMaiuscula.exec(x) + " ", " " + x + " "));
		box.replace(" E ", " e ");
		box.replace(" Ou ", " ou ");
		box.replace(" de a ", " de A ");
		box.replace(" de o ", " de O ");

		s = StringTrim.plus(box.get());

		if (StringLength.get(s) < 2) {
			return s;
		}

		s = s.substring(0,1).toUpperCase() + s.substring(1);

		if (espaco) {
			s += " ";
		}

		return StringLength.max(s, 60);

	}

	static isValid(s) {
		s = StringTrim.plus(s);
		if ((StringLength.get(s) < 7) || !StringContains.is(s, " ")) {
			return false;
		}
		return true;
	}

}
UNomeProprio.VALIDOS_COM_NUMEROS = UNomeProprio.VALIDOS_SEM_NUMEROS.concat(StringConstants.NUMEROS);
