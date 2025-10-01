package gm.utils.robo;

import gm.utils.comum.Lst;
import gm.utils.selenium.SeleniumPlus;
import gm.utils.selenium.Swe;

public class PontoCaixa {

	public static void main(String[] args) {
		SeleniumPlus se = new SeleniumPlus();
		se.get("https://webponto.resource.com.br/WebPonto/");
//		se.li().unique(i -> i.toString().contains("Registrar Ponto")).click();
		se.inputs().unique(i -> i.isId("CodEmpresa")).set("9");
		se.inputs().unique(i -> i.isId("requiredusuario")).set("045376");
		se.inputs().unique(i -> i.isId("requiredsenha")).set("resource123");
		
		Lst<Swe> oks = new Lst<>();
		se.divs().filter(i -> i.isClass("quadroLogin_Campo")).each(i -> i.byClass("BotaoAchatado").filter(ii -> ii.isValue("  OK  ")).each(ii -> oks.add(ii)));
		oks.each(i -> i.click());
		
		se.get("https://webponto.resource.com.br/WebPonto/just_user/IncluirMarcacaoOnLine.asp");
		
		se.inputs().filter(i -> i.isClass("BotaoAchatado")).unique(i -> i.isId("Button1")).click();
		
		
	}
	
}