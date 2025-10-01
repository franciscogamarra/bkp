import StringBox from './StringBox';
import StringConstants from './StringConstants';
import StringEmpty from './StringEmpty';
import StringReplace from './StringReplace';
import StringToCamelCaseSepare from './StringToCamelCaseSepare';
import StringTrim from './StringTrim';

export default class StringToConstantName {

	static exec(s) {
		s = StringTrim.plus(s);
		if (StringEmpty.is(s)) {
			return s;
		}
		s = s.replace("%", " percent ");
		s = s.replace("/", " barra ");
		s = s.replace(">", " maior ");
		s = s.replace("<", " menor ");
		s = s.replace("=", " igual ");
		s = s.replace("\"", " aspasduplas ");
		s = s.replace("-", " ");
		s = StringTrim.plus(s);
		s = StringToCamelCaseSepare.exec(s);
		s = s.replace("-", "");
		s = s.replace(" ", "_");
		s = s.toUpperCase();
		s = StringNormalizer.get(s);
		let box = new StringBox(s);
		StringConstants.NUMEROS.forEach(numero => {
			box.replace("_" + numero, numero);
		});
		s = box.get();
		StringReplace.exec(s, "__", "_");
		if (UInteger.isInt(s.substring(0, 1))) {
			s = "_" + s;
		}
		return s;
	}

	static main(args) {
		let s = StringClipboard.get();
		s = StringToConstantName.exec(s);
		console.log("private static final String SQL_" + s + " = ");
	}

}
