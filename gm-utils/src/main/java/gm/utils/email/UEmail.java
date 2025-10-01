package gm.utils.email;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import gm.utils.comum.Aleatorio;
import gm.utils.comum.UConstantes;
import src.commom.utils.integer.IntegerFormat;
import src.commom.utils.string.StringEmpty;

public class UEmail {
	private UEmail() {
		throw new IllegalStateException("Classe utilitahria");
	}
	@Deprecated//use isValid
	public static boolean valido(String email) {
		return isValid(email);
	}
	public static boolean isValid(String email) {
		if (StringEmpty.is(email)) {
			return false;
		}
		String emailPattern = "\\b(^[_A-Za-z0-9-]+(\\.[_A-Za-z0-9-]+)*@([A-Za-z0-9-])+(\\.[A-Za-z0-9-]+)*((\\.[A-Za-z0-9]{2,})|(\\.[A-Za-z0-9]{2,}\\.[A-Za-z0-9]{2,}))$)\\b";
		Pattern pattern = Pattern.compile(emailPattern, Pattern.CASE_INSENSITIVE);
		Matcher matcher = pattern.matcher(email);
		return matcher.matches();
	}
	public static String aleatorio() {
		String s = "";

		int tamanho = Aleatorio.get(3, 10);
		for (int i = 0; i < tamanho; i++) {
			s += Aleatorio.get(UConstantes.letrasMinusculas);
		}
		s += "@";
		tamanho = Aleatorio.get(2, 5);
		for (int i = 0; i < tamanho; i++) {
			s += Aleatorio.get(UConstantes.letrasMinusculas);
		}
		return s+".com";
	}
	public static String mock(int i) {
		return "email" + IntegerFormat.zerosEsquerda(i, 3) + "@gmail.com";
	}
}
