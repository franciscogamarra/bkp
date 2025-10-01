package gm.utils.robo;

import java.awt.Robot;
import java.awt.event.InputEvent;

import gm.utils.comum.USystem;

public class Robo {
	
	public static final Robot robot;
	
	static {
		try {
			robot = new Robot();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected static void move(int x, int y) {
		try {
			robot.mouseMove(x, y);
//			SystemPrint.ln(x + "/" +  y);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	protected static void click() {
		mousePress();
		mouseRelease();
		USystem.sleepMiliSegundos(50);
	}
	
	protected static void clickMeio() {
		mousePressMeio();
		mouseReleaseMeio();
		USystem.sleepMiliSegundos(50);
	}

	protected static void doubleClick() {
		click();
		click();
	}
	
	@SuppressWarnings("deprecation")
	protected static void mousePress() {
		robot.mousePress(InputEvent.BUTTON1_MASK);
	}

	@SuppressWarnings("deprecation")
	protected static void mouseRelease() {
		robot.mouseRelease(InputEvent.BUTTON1_MASK);
	}
	
	protected static void mousePressMeio() {
		robot.mousePress(InputEvent.BUTTON2_DOWN_MASK);
	}
	
	protected static void mouseReleaseMeio() {
		robot.mouseRelease(InputEvent.BUTTON2_DOWN_MASK);
	}
	
}
