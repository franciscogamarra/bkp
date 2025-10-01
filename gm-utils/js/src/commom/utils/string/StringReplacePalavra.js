import Box from '../comum/Box';
import StringConstants from './StringConstants';
import StringContains from './StringContains';
import StringEmpty from './StringEmpty';
import StringReplace from './StringReplace';

export default class StringReplacePalavra {

	static palavraInterno(de, expressao, resultado) {

		if (StringEmpty.is(de)) {
			return "";
		}

		de = " " + de + " ";

		/* nao usar StringBox pois dá referencia circular */
		let box = new Box(de);

		StringConstants.SIMBOLOS.forEach(a => {
			StringConstants.SIMBOLOS.forEach(b => {
				let c = a + expressao + b;
				let d = a + resultado + b;
				box.set(StringReplace.exec(box.get(), c, d));
			});
		});

		de = box.get();
		de = de.substring(1);
		return de.substring(0, de.length - 1);

	}

	static exec(de, expressao, resultado) {

		if (!StringContains.is(de, expressao)) {
			return de;
		}

		if (StringContains.is(resultado, expressao)) {
			de = StringReplacePalavra.palavraInterno(de, expressao, StringReplace.OCORRENCIA_IMPROVAVEL);
			expressao = StringReplace.OCORRENCIA_IMPROVAVEL;
		}

		return StringReplacePalavra.palavraInterno(de, expressao, resultado);

	}

}
