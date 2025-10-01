package gm.utils.robo;

import java.awt.Robot;
import java.util.HashMap;
import java.util.Map;

import javax.swing.event.MenuKeyEvent;

import gm.utils.comum.USystem;

public class Tecla {
	
	private static final Map<Integer, Tecla> list = new HashMap<>();
	
	private static synchronized Tecla getPrivate(int key) {
		Tecla o = list.get(key);
		if (o == null) {
			o = new Tecla(key);
			list.put(key, o);
		}
		return o;
	}
	
	public static final Tecla get(int key) {
		Tecla o = list.get(key);
		if (o == null) {
			o = getPrivate(key);
		}
		return o;
	}
	
	public static final Tecla ctrl = getPrivate(MenuKeyEvent.VK_CONTROL);
	public static final Tecla alt = getPrivate(MenuKeyEvent.VK_ALT);
	public static final Tecla space = getPrivate(MenuKeyEvent.VK_SPACE);
	public static final Tecla up = getPrivate(MenuKeyEvent.VK_UP);
	public static final Tecla left = getPrivate(MenuKeyEvent.VK_LEFT);
	public static final Tecla win = getPrivate(MenuKeyEvent.VK_WINDOWS);
	public static final Tecla shift = getPrivate(MenuKeyEvent.VK_SHIFT);
	public static final Tecla pageDown = getPrivate(MenuKeyEvent.VK_PAGE_DOWN);
	public static final Tecla pageUp = getPrivate(MenuKeyEvent.VK_PAGE_UP);
	public static final Tecla f11 = getPrivate(MenuKeyEvent.VK_F11);
	public static final Tecla enter = getPrivate(MenuKeyEvent.VK_ENTER);
	public static final Tecla c = getPrivate(MenuKeyEvent.VK_C);
	public static final Tecla d = getPrivate(MenuKeyEvent.VK_D);
	public static final Tecla v = getPrivate(MenuKeyEvent.VK_V);
	public static final Tecla x = getPrivate(MenuKeyEvent.VK_X);
	
	public static final Robot robot = Robo.robot;

	private int key;

	private Tecla(int key) {
		this.key = key;
	}
	
	public void down() {
		Mouse.checaAbort();
		robot.keyPress(key);
	}

	public void upDireto() {
		robot.keyRelease(key);
	}
	
	public void up() {
		upDireto();
		Mouse.checaAbort();
	}
	
	public void press() {
		down();
		USystem.sleepMiliSegundos(1);
		up();
		USystem.sleepMiliSegundos(10);
	}
	
}
