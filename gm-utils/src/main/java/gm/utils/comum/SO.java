package gm.utils.comum;

public class SO {
	private static boolean contains(String s) {
		String ss = get();
		return ss == null ? false : ss.toLowerCase().contains(s.toLowerCase());
	}
	private static String get(){
		return System.getProperty("os.name");
	}
	public static boolean linux(){
		return contains("linux");
	}
	public static boolean windows(){
		return contains("windows");
	}
	public static String barra() {
		return windows() ? "\\" : "/";
	}
}