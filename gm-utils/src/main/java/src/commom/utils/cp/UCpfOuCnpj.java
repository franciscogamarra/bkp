package src.commom.utils.cp;

public class UCpfOuCnpj {

	public static String format(String s) {
		if (UCpf.isValid(s)) {
			return UCpf.format(s);
		}
		if (UCnpj.isValid(s)) {
			return UCnpj.format(s);
		} else {
			return s;
		}
	}

	public static boolean isValid(String s) {
		return UCpf.isValid(s) || UCnpj.isValid(s);
	}

}
