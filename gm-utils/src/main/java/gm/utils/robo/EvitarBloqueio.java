package gm.utils.robo;

import java.awt.Point;

import gm.utils.comum.SystemPrint;
import gm.utils.comum.USystem;
import gm.utils.date.Data;
import gm.utils.image.Screen;

public class EvitarBloqueio {

	private static int ultimo;
	private static final int SEGUNDOS = 30;
	
//	private static Lst<Point> points = new Lst<>();
//	static {
//		int i = 250;
//		int w = Screen.width;
//		int v = Screen.center_vertical;
//		points.add(new Point(w - i , v     ));
//		points.add(new Point(w     , v + i ));
//		points.add(new Point(w - i , v     ));
//		points.add(new Point(w     , v - i ));
//	}

	private static int getPosition() {
		Point o = Mouse.getPoint();
		return o.x + o.y;
	}
	
	public static void main(String[] args) {
		
		USystem.sleepSegundos(10);
		
		ultimo = getPosition();
		
//		USystem.sleepMinutos(2);
		
		while (true) {
			
			int i = getPosition();
			
			String key = "ultimo = " + ultimo + " ; atual = " + i + " ; " + Data.now(); 
			
			if (i != ultimo) {
				ultimo = i;
				SystemPrint.err(key);
				USystem.sleepSegundos(SEGUNDOS);
				continue;
			}
			
			SystemPrint.ln(key);
			
			try {
				Tecla.shift.press();
				Mouse.moveGraciosamente(Screen.width, Screen.center_vertical + 100);
				Mouse.moveGraciosamente(Screen.width, Screen.center_vertical - 100);
				Mouse.moveGraciosamente(Screen.width, Screen.center_vertical);
//				Point o = points.remove(0);
//				points.add(o);
//				Mouse.moveGraciosamente(o, 1);
			} catch (Exception e) {
				Mouse.clear();
				SystemPrint.err(e.getMessage());
			}
				
			ultimo = getPosition();
			
			USystem.sleepSegundos(SEGUNDOS);
			
		}
		
	}
	
}