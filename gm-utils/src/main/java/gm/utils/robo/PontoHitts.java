package gm.utils.robo;

import gm.utils.comum.USystem;
import gm.utils.selenium.SeleniumPlus;

public class PontoHitts {

	public static void main(String[] args) {
		SeleniumPlus se = new SeleniumPlus();
		se.get("https://www.mdcomune.com.br/Madis/Account/LogOn?ReturnUrl=%2F");
		se.inputs().unique(i -> i.isId("LogOnModel_UserName")).set("francisco.gamarra@globalhitss.com.br");
		se.inputs().unique(i -> i.isId("LogOnModel_Password")).set("123456");
		se.inputs().unique(i -> i.isId("LogOnModel_Password")).tab();
		
		USystem.sleepSegundos(2);
		
		se.buttons().unique(i -> i.isId("btnFormLogin")).click();
		
		USystem.sleepSegundos(5);
		
		se.inputs().unique(i -> i.isValue("Salvar")).click();
		
	}
	
}