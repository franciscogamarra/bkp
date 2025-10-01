import StringCamelCase from './StringCamelCase';
import StringPrimeiraMaiuscula from './StringPrimeiraMaiuscula';

export default class StringPascalCase {

	static exec(s) {
		return StringPrimeiraMaiuscula.exec(StringCamelCase.exec(s));
	}

}
