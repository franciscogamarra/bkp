package br.utils;

import br.utils.lambda.P1;

public class Print {
	
	public static void ln(Object o) {
		System.out.println(o);
	}

	public static void err(Object o) {
		System.err.println(o);
	}

	public static void verde(String s) {
		Print.ln("\u001B[32m" + s + "\u001B[0m");
	}
	
	public static void azul(String s) {
		Print.ln("\u001B[34m" + s + "\u001B[0m");
	}

	public static void amarelo(String s) {
		Print.ln("\u001B[33m" + s + "\u001B[0m");
	}
	
	public static void magenta(String s) {
		Print.ln("\u001B[35m" + s + "\u001B[0m");
	}
	
	public static void rosa(String s) {
		Print.ln("\\033[38;2;255;105;180m" + s + "\\033[0m");
	}

	private static void bloco(String s, P1<String> func) {
		if (s == null) {
			s = "null";
		}
		s = "= " + s + " =";
		String linha = "\n" + "=".repeat(s.length()) + "\n";
		func.call(linha + s + linha);
	}
	
	public static void blocoVerde(String s) {
		bloco(s, Print::verde);
	}
	
	public static void blocoAzul(String s) {
		bloco(s, Print::azul);
	}
	
	public static void blocoAmarelo(String s) {
		bloco(s, Print::amarelo);
	}

	public static void blocoMagenta(String s) {
		bloco(s, Print::magenta);
	}
	
	public static void blocoRosa(String s) {
		bloco(s, Print::rosa);
	}

	public static void blocoRed(String s) {
		bloco(s, Print::err);
	}
	
}
