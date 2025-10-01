package gm.utils.date;

import java.time.Instant;
import java.time.LocalTime;
import java.util.Calendar;
import java.util.Date;

import src.commom.utils.integer.IntegerFormat;
import src.commom.utils.string.StringEmpty;

public class HoraMinutoSegundo {
	
	private final Range horav = new Range(0, 23);
	private final Range minuto = new Range(0, 59);
	private final Range segundo = new Range(0, 59);

	/* constructors */
	
	public HoraMinutoSegundo() {
		set(DataComHora.getCalendarInstance());
	}
	
	public HoraMinutoSegundo(Calendar value) {
		set(value);
	}

	public HoraMinutoSegundo(int hora, int minuto, int segundo) {
		set(hora, minuto, segundo);
	}

	public HoraMinutoSegundo(Date value) {
		set(value);
	}

	public HoraMinutoSegundo(LocalTime value) {
		set(value);
	}
	
	/* set's */

	public HoraMinutoSegundo set(Date value) {
		return set(value.toInstant());
	}
	
	public HoraMinutoSegundo set(Calendar o) {
		return set(o.get(Calendar.HOUR_OF_DAY), o.get(Calendar.MINUTE), o.get(Calendar.SECOND));
	}
	
	public HoraMinutoSegundo set(LocalTime value) {
		return set(value.getHour(), value.getMinute(), value.getSecond());
	}

	public HoraMinutoSegundo set(Instant value) {
		return set(value.atZone(DataComHora.ZONEIDBRASIL).toLocalTime());
	}
	
	public HoraMinutoSegundo set(int hora, int minuto, int segundo) {
		this.horav.set(hora);
		this.segundo.set(segundo);
		this.minuto.set(minuto);
		return this;
	}
	
	/* setters */
	
	public HoraMinutoSegundo setHora(int value) {
		horav.set(value);
		return this;
	}

	public HoraMinutoSegundo setMinuto(int value) {
		minuto.set(value);
		return this;
	}

	public HoraMinutoSegundo setSegundo(int value) {
		segundo.set(value);
		return this;
	}
	
	/* getters */
	
	public int getHora() {
		return horav.get();
	}

	public int getMinuto() {
		return minuto.get();
	}

	public int getSegundo() {
		return segundo.get();
	}

	public String format(String s) {
		
		if (StringEmpty.is(s)) {
			return "";
		}
		
		s = s.replace("[hh]", IntegerFormat.xx(horav.get()));
		s = s.replace("[h]", "" + horav.get());

		s = s.replace("[nn]", IntegerFormat.xx(minuto.get()));
		s = s.replace("[n]", "" + minuto.get());

		s = s.replace("[ss]", IntegerFormat.xx(segundo.get()));
		return s.replace("[s]", "" + segundo.get());
		
	}
	
	@Override
	public String toString() {
		return format("[hh]:[nn]:[ss]");
	}

	public boolean eq(HoraMinutoSegundo o) {
		return o != null && toInt() == o.toInt();
	}

	private int toInt() {
		return getHora() * 10000 + getMinuto() * 100 + getSegundo();
	}
	
	public int removeMinutos(int minutos) {
		return horav.remove(minuto.remove(minutos));
	}

	public HoraMinutoSegundo copy() {
		return new HoraMinutoSegundo(getHora(), getMinuto(), getSegundo());
	}
	
}
