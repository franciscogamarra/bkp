package gm.utils.robo;

public class PontoTangerino {

	public static void main(String[] args) {
		
		Chrome o = new Chrome();
		o.segundosPadrao = 2;
		o.open();
		o.acessa("https://app.tangerino.com.br/Tangerino/pages/LoginPage");

		o.click(1095,-510);
		o.click(1000,-375);
		o.digita("5990");
//		o.tecleEnter();
		
	}
	
	
	
}