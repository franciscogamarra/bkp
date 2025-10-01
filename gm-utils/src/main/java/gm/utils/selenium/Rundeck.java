package gm.utils.selenium;

import gm.utils.comum.CredenciaisDeRede;
import gm.utils.comum.SystemPrint;
import gm.utils.comum.USystem;

public class Rundeck {
	
	public String id;
	public String ambiente;
	public String artefato;
	
	public void exec() {

		Selenium selenium = new Selenium();
		selenium.start();
		selenium.get("https://rundeck.cooperforte.coop/user/login");
		
		try {
			selenium.byId("details-button").click();
			selenium.byId("proceed-link").click();
		} catch (Exception e) {
			
		}
		
		selenium.byId("login").sendKeys(CredenciaisDeRede.getUser());
		selenium.byId("password").sendKeys(CredenciaisDeRede.getPass());
		selenium.byId("btn-login").click();
		
		selenium.get("https://rundeck.cooperforte.coop/project/"+ambiente+"/job/show/" + id);
		
		selenium.byName("extra.option.artefato").sendKeys(artefato);
		
		selenium.byId("execFormRunButton").click();
		
		while (true) {
			
			USystem.sleepSegundos(5);
			
			try {
				SystemPrint.ln(selenium.byId("progressBar").getText());
			} catch (Exception e) {
				SystemPrint.ln("Finalizou");
				break;
			}

		}
		
	}

	public static void main(String[] args) {
		Rundeck rundeck = new Rundeck();
		rundeck.id = "c36778e4-9e91-40cc-9a34-c522e24ad49d";
		rundeck.ambiente = "HOMOLOGACAO";
		rundeck.artefato = "bla.ear";
		rundeck.exec();
	}
	
}



//6210c4fd-a6b7-46ee-bac5-5f27234b313c