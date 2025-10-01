package br;

public class SystemPrint {

	public static void ln(Object o) {
		
		if (o != null && o.toString().contentEquals("128")) {
			System.out.println();
		}
		
		System.out
		.println(o);
	}
	
	public static void ln() {
		ln("");
	}
	
	public static void row(Object o) {
		System.out
		.print(o);
	}

	public static void err(Object o) {
		System.err
		.println(o);
	}
	
}
