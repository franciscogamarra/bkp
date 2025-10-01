package gm.utils.robo;

import gm.utils.comum.USystem;

public class Chrome {
	
	public int segundosPadrao = 1;

	public void open() {
		
		for (int i = 0; i < 5; i++) {
			click(1635,-760);
		}

		click(518,1059);
		
	}
	
	public void click(int x, int y) {
		click(x, y, segundosPadrao);
	}
	
	public void click(int x, int y, int segundos) {
		Mouse.click(x, y);
		USystem.sleepSegundos(segundos);
	}
	
	public void tecleEnter() {
		Tecla.enter.press();
		sleep();
	}
	
	public void acessa(String url) {
		click(450,-718);
		sleep();
		digita(url);
		tecleEnter();
	}
	
	public void digita(String s) {
		Teclado.digita(s);
		sleep();
	}
	
	private void sleep() {
		USystem.sleepSegundos(segundosPadrao);
	}
	
}
