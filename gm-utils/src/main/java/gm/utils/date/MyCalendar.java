package gm.utils.date;

import java.util.Calendar;
import java.util.GregorianCalendar;

import gm.utils.exception.UException;

public class MyCalendar extends GregorianCalendar {
	private static final long serialVersionUID = 1L;
	public MyCalendar(){
	}
	public MyCalendar(int ano, int mes, int dia, int hora, int minuto, int segundo) {
		super(ano, mes, dia, hora, minuto, segundo);
		set(Calendar.MILLISECOND, 0);
	}
	@Override
	public boolean equals(Object obj) {
		if (obj == null) {
			return false;
		}
		if (super.equals(obj)) {
			return true;
		}
		try {
			return Data.to(obj).eq(this);
		} catch (Exception e) {
			UException.printTrace(e);
			return false;
		}

	}
	@Override
	public String toString() {
		return new Data(this).format("[dd]/[mm]/[yyyy] [hh]:[nn]:[ss]");
	}
	
	public static Calendar build(int ano, int mes, int dia, int hora, int minuto, int segundo) {
		GregorianCalendar o = new GregorianCalendar(ano, mes, dia, hora, minuto, segundo);
		o.set(Calendar.MILLISECOND, 0);
		return o;
	}
	
}
