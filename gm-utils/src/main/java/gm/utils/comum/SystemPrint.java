package gm.utils.comum;

import gm.utils.number.Numeric18;
import src.commom.utils.string.StringRight;

public class SystemPrint {
	
//	private static int vez = 0;
	
	private static String margem = "";

	private static void lnFinal(Object o) {
		
//		vez++;
//		
//		if (vez == 16) {
//			System.out
//			.println("vez = " + vez);
//		}

		System.out
		.println(margem + o);
	}
	
	public static void ln(Object o) {
		
		if (o == null) {
			lnFinal(null);
			return;
		}
		
		if (o instanceof Double || o.getClass().equals(double.class)) {
			Double d = (Double) o;
			String s = new Numeric18(d).toStringPonto();
			if (s.contains(".")) {
				while (s.endsWith("0")) {
					s = StringRight.ignore1(s);
				}
			}
			lnFinal(s);
			return;
		}

		lnFinal(o);
		
	}
	
	public static void ln() {
		ln("");
	}
	
	public static void row(Object o) {
		lnFinal(o);
	}

	public static void err(Object o) {
		System.err
		.println(margem + o);
	}

	public static void margemInc() {
		margem += "  ";
	}
	
	public static void margemDec() {
		margem = margem.substring(2);
	}

}