import ArrayLst from '../array/ArrayLst';
import StringExtraiCaracteres from './StringExtraiCaracteres';
import StringLength from './StringLength';
import StringRemoveAcentos from './StringRemoveAcentos';
import StringRight from './StringRight';
import StringTrim from './StringTrim';

export default class StringAbreviacaoAmericana {

	static exec(s) {

		s = StringTrim.plus(s);

		if (StringLength.get(s) < 3) {
			return s;
		}

		s = s.toLowerCase();

		s = StringRemoveAcentos.exec(s);
		let before = s.substring(0, 1);
		let after = StringRight.get(s, 1);

		s = s.substring(1);
		s = StringRight.ignore1(s);

		s = StringExtraiCaracteres.exec(s, StringAbreviacaoAmericana.LETRAS);

		return before + s + after;

	}

}
StringAbreviacaoAmericana.LETRAS = ArrayLst.build(
	"b","c","d","f","g","h","j","k","l","m","n","p","q","r","s","t","v","w","x","z"
);
