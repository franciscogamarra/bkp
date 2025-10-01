package gm.utils.robo;

import java.awt.Robot;

import gm.utils.comum.Lst;
import gm.utils.comum.USystem;
import gm.utils.string.StringClipboard;

public class Teclado {
	
	public static final Robot robot = Robo.robot;

	public static void ctrl_c() {
		press(Tecla.ctrl, Tecla.c);
	}
	
	public static void ctrl_v() {
		press(Tecla.ctrl, Tecla.v);
	}

	public static void maximizarJanelaAtual() {
		press(Tecla.win, Tecla.up);
//		press(Tecla.alt, Tecla.space, Tecla.x);
	}

	public static void press(Tecla... teclas) {
		Lst<Tecla> itens = new Lst<>(teclas);
//		Tecla o = itens.removeLast();
		itens.each(i -> i.down());
//		o.press();
		itens.inverteOrdem().each(i -> i.upDireto());
		USystem.sleepMiliSegundos(51);
	}

	public static void digita(String s) {
		StringClipboard.set(s);
		USystem.sleepMiliSegundos(10);
		ctrl_v();
	}

	public static void minimizarTudo() {
		press(Tecla.win, Tecla.d);		
	}

}
