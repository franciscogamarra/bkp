package gm.utils.robo;

import gm.utils.comum.SystemPrint;
import gm.utils.comum.USystem;

public class PontoKanban {
	
//	private static void abrirKanban() {
//		Mouse.click(606,1063);
//	}

	public static void main(String[] args) {

		/*
		
		Mouse.click(1635,-760);
		Mouse.click(1635,-760);
		Mouse.click(1635,-760);
		Mouse.click(1635,-760);
		Mouse.click(1635,-760);
		
		Mouse.click(518,1059);
		USystem.sleepSegundos(1);
		Mouse.click(450,-718);
		USystem.sleepSegundos(1);
		Teclado.digita("https://app.tangerino.com.br/Tangerino/pages/LoginPage");
		Teclado.enter();
		USystem.sleepSegundos(1);
		Mouse.click(1095,-510);
		USystem.sleepSegundos(1);
		Mouse.click(1000,-375);
		USystem.sleepSegundos(1);
		Teclado.digita("5990");
		Mouse.move(1000,-305);
		
		/**/
		for (int i = 0; i < 1000; i++) {
			USystem.sleepSegundos(1);
			SystemPrint.ln(Mouse.getPoint());
		}
		/**/
		
		/*
		SeleniumPlus se = new SeleniumPlus();
		se.get("https://app.tangerino.com.br/Tangerino/pages/LoginPage");
		se.li().unique(i -> i.toString().contains("Registrar Ponto")).click();
		se.inputs().unique(i -> i.isId("codigoEmpregador")).set("9KZ5N");
		se.inputs().unique(i -> i.isId("codigoPin")).set("5990");
		se.buttons().unique(i -> i.isId("registraPonto")).click();
		*/
	}
	
}