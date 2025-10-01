package src.commom.utils.string;

public class StringPascalCase {

	public static String exec(String s) {
		return StringPrimeiraMaiuscula.exec(StringCamelCase.exec(s));
	}

}
