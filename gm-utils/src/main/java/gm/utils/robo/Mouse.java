package gm.utils.robo;

import java.awt.MouseInfo;
import java.awt.Point;

import gm.utils.comum.SystemPrint;
import gm.utils.comum.USystem;
import gm.utils.date.Data;
import gm.utils.image.Screen;

public class Mouse {
	
	public static boolean abortarPorMovimento = true;
	
	private static int ultimoX = Integer.MIN_VALUE;
	private static int ultimoY = Integer.MIN_VALUE;
	
	public static void clear() {
		ultimoX = Integer.MIN_VALUE;
		ultimoY = Integer.MIN_VALUE;
	}
	
	public static void main(String[] args) {
		for (int i = 0; i < 60; i++) {
			USystem.sleepSegundos(1);
			SystemPrint.ln(Mouse.getPoint());
		}
	}
	
	public static int getX() {
		try {
			Double x = getPoint().getX();
			return x.intValue();
		} catch (Exception e) {
			return 0;
		}
	}

	public static Point getPoint() {
		
		try {
			return MouseInfo.getPointerInfo().getLocation();
		} catch (Exception e) {
			return new Point(0, 0);
		}
		
	}

	public static int getY() {
		try {
			Double x = getPoint().getY();
			return x.intValue();
		} catch (Exception e) {
			return 0;
		}
	}
	
	private static int dif(int a, int b) {
		if (a > b) {
			return a - b;
		} else {
			return b - a;
		}
	}
	
	private static void checa(int atual, int velho) {
		int a = dif(atual, velho);
		if (a > 20) {
			throw new RuntimeException("abortado por movimento: " + atual + " / " + velho + " / " + a + " / " + Data.now());
		}
	}
	
	public static void checaAbort() {
		if (abortarPorMovimento && ultimoX != Integer.MIN_VALUE) {
			checa(getX(), ultimoX);
			checa(getY(), ultimoY);
		}
	}
	
	private static void movePrivate(int x, int y, int sleep) {

		checaAbort();
		
		Robo.move(x, y);
		
		ultimoX = x;
		ultimoY = y;
		
		USystem.sleepMiliSegundos(sleep);
		
	}
	
	public static void move(int x, int y) {
		movePrivate(x, y, 96);
	}
	
	public static int SALTO_PADRAO = 5;
	
	public static void moveGraciosamente(int x, int y) {
		moveGraciosamente(x, y, SALTO_PADRAO);
	}
	
	public static void moveGraciosamente(Point o) {
		moveGraciosamente(o, SALTO_PADRAO);
	}
	
	public static void moveGraciosamente(Point o, int salto) {
		moveGraciosamente(o.x, o.y, salto);
	}
	
	public static void moveGraciosamente(int x, int y, int salto) {
		
		int xx = getX();
		int yy = getY();
		
		while (xx != x || yy != y) {

			if (xx > x) {
				xx -= salto;
				if (xx < x) {
					xx = x;
				}
			} else if (xx < x) {
				xx += salto;
				if (xx > x) {
					xx = x;
				}
			}
			
			if (yy > y) {
				yy -= salto;
				if (yy < y) {
					yy = y;
				}
			} else if (yy < y) {
				yy += salto;
				if (yy > y) {
					yy = y;
				}
			}
			
			movePrivate(xx, yy, 1);
			
		}
		
	}

	public static void desloca(int x, int y) {
		x += getX();
		y += getY();
		move(x, y);
	}

	public static void click(int x, int y) {
		move(x, y);
		Robo.click();
	}
	
	public static void clickMeio(int x, int y) {
		move(x, y);
		Robo.clickMeio();
	}
	
	public static void click() {
		click(getX(), getY());
	}

	public static void clickMeio() {
		clickMeio(getX(), getY());
	}
	
	public static void doubleClick(int x, int y) {
		move(x, y);
		Robo.doubleClick();
	}
	
	public static void segura() {
		Robo.mousePress();
		USystem.sleepMiliSegundos(50);
	}

	public static void solta() {
		Robo.mouseRelease();
		USystem.sleepMiliSegundos(50);
	}

	public static void moveToLimit() {
		move(Screen.width-1, Screen.height-1);
	}

	public static void print() {
		System.out.println(getPoint());
	}

}