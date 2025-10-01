package br;

import java.awt.Point;
import java.util.Calendar;

public class EvitarBloqueio {

	private static int ultimo;
	private static final int SEGUNDOS = 30;

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
			
			String key = "ultimo = " + ultimo + " ; atual = " + i + " ; " + Calendar.getInstance(); 
			
			if (i != ultimo) {
				ultimo = i;
				SystemPrint.err(key);
				USystem.sleepSegundos(SEGUNDOS);
				continue;
			}
			
			SystemPrint.ln(key);
			
			try {
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