package gm.utils.robo;

import gm.utils.comum.SystemPrint;
import gm.utils.comum.USystem;

public class Assinar {
	
	private static int x;
	private static int y;

	public static void main(String[] args) {
		
		USystem.sleepSegundos(5);
		
		x = Mouse.getX();
		y = Mouse.getY();

//		Robo.move(x, y);
//		USystem.sleepSegundos(1);
//		Robo.move(x, y);
//		USystem.sleepSegundos(1);
		
		Robo.mousePress();
		USystem.sleepSegundos(1);
		
		x = Mouse.getX();
		y = Mouse.getY();
		
		m(30,-20);
		m(15,-15);
		m(10,-15);
		
		for (int i = 5; i > 0; i--) {
			m( i, -10);
		}

		for (int i = 0; i > -5; i -= 2) {
			m( i,-10);
		}
		
		m(-3, -1);
		m(-3, 1);
		m(-3, 2);
		m(-3, 3);
		m(-3, 4);
		
		for (int i = 0; i < 10; i++) {
			m(-3, 20);
		}

		for (int i = 0; i < 5; i++) {
			m( i, 5);
		}
		
		m( 5, 0);

//		for (int i = 0; i < 5; i++) {
//			m( i, -i);
//		}
		
		m( 8, -10);
		m( 6, -10);
		m( 4, -10);
		
		for (int i = 0; i < 10; i++) {
			m( 3, -10);
		}
		
		m(  2, -10);
		m(  1, -9);
		m(  0, -8);
		m( -1, -7);
		m( -2, -6);
		m( -3, -5);
		m( -4, -4);
		m( -5, -3);
		m( -6, -2);
		m( -7, -1);
		m( -8,  0);
		m(-10,  1);
		m(-12,  2);
		m(-14,  3);
		m(-16,  4);
		m(-14,  5);
		m(-13,  6);
		m(-12,  7);
		m(-11,  8);
		m(-10,  9);
		m( -5,  12);
		m(  0,  1);
		m(  0,  2);
		m(  0,  3);
		m(  0,  3);
		m(  1,  1);
		m(  2,  1);
		m(  3,  1);
		m(  4,  1);
		m(  10,  0);
		m(  1,  -1);
		m(  2,  -1);
		m(  3,  -2);
		m(  30, -4);
		m(  28,  -6);
		m(  26,  -8);
		m(  24,  -10);
		m(  22,  -12);
		m(  20,  -14);
		m(  18,  -12);
		m(  16,  -10);
		
		Robo.mouseRelease();
		USystem.sleepSegundos(1);
	}
	
	private static void m(int xx, int yy) {
		
		int xa = Mouse.getX();
		if (xa != x) {
			SystemPrint.ln("xa = " + xa + ", x = " + x);
			System.exit(0);
		}

		int ya = Mouse.getY();
		if (ya != y) {
			SystemPrint.ln("ya = " + ya + ", y = " + y);
			System.exit(0);
		}
		
		x += xx;
		y += yy;
		SystemPrint.ln("" + x + "," + y);
		Robo.move(x, y);
		USystem.sleepMiliSegundos(5);
		x = Mouse.getX();
		y = Mouse.getY();
		
//		USystem.sleepMiliSegundos(100);
		
	}
	
}