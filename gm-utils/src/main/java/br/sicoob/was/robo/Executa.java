package br.sicoob.was.robo;

import java.awt.Point;
import java.awt.datatransfer.Clipboard;

import gm.utils.comum.Sleep;
import gm.utils.robo.Mouse;
import gm.utils.robo.Teclado;
import gm.utils.string.StringClipboard;

public class Executa {

	public static void main(String[] args) {
		
//		Mouse.moveGraciosamente(760, 1058);
//		Sleep.sleepSegundos(1);
//		Mouse.clickMeio();
		
		Point p = Mouse.getPoint();
		Double x = p.getX();
		Double y = p.getY();
		System.out.println("click(" + x.intValue() + ", " + y.intValue() + ");");
		
//		efetuaLogin();
//		confirmaDerrubarSessao();
//		expandeMenuAplicativos();
//		expandeMenuTipoDeAplicativos();
//		clicaEmAplicativosCorporativosWebsphere();
		
		atualizaApi();
		
		click(1038, 345);
		
	}
	
	private static void atualizaApi() {
		Mouse.moveGraciosamente(1048, -364);
		Mouse.segura();
		Mouse.moveGraciosamente(726, -364);
		Mouse.solta();
		StringClipboard.set("");
		Teclado.ctrl_c();
		String s = StringClipboard.get();
		System.out.println(s);
//		Mouse.segura();
	}

	private static void clicaEmAplicativosCorporativosWebsphere() {
		click(454, -429);
	}

	private static void expandeMenuTipoDeAplicativos() {
		click(377, -446);
	}

	private static void expandeMenuAplicativos() {
		click(330, -484);
	}

	private static void click(int x, int y) {
		Mouse.moveGraciosamente(x, y);
		Mouse.click(x, y);
		Sleep.mills(200);
	}

	private static void confirmaDerrubarSessao() {
		click(927, -208);
		Sleep.segundos(2);
	}

	private static void efetuaLogin() {
		Mouse.doubleClick(980, -365);
		Teclado.digita("wasadmin");
		Mouse.doubleClick(980, -315);
		Teclado.digita("wasadmin");
		click(955, -281);
		Sleep.segundos(2);
	}
	
}
