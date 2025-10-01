package gm.utils.date;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.TimeZone;

import lombok.Getter;
import lombok.Setter;
import src.commom.utils.string.StringEmpty;

@Setter @Getter
public class DataComHora {

	public static final ZoneId ZONEIDBRASIL = ZoneId.of("America/Sao_Paulo");
	public static final TimeZone TIMEZONE = TimeZone.getTimeZone(ZONEIDBRASIL);
	
	private String comentario;
	private DataSemHora data;
	private HoraMinutoSegundo horaMinutoSegundo;
	
	@Getter @Setter
	private static DataComHora fixaParaTestes = null; 
	
	public static Calendar getCalendarInstance() {
		if (fixaParaTestes == null) {
			return Calendar.getInstance(TIMEZONE);
		} else {
			return fixaParaTestes.toCalendar();
		}
	}
	
	/* construtores */
	
	public DataComHora() {
		set(getCalendarInstance());
	}
	
	public DataComHora(Calendar value) {
		set(value);
	}
	public DataComHora(LocalDate value) {
		set(value);
	}
	
	public DataComHora(int ano, int mes, int dia) {
		set(ano, mes, dia);
	}
	
	public DataComHora(int ano, int mes, int dia, int hora, int minuto, int segundo) {
		set(ano, mes, dia, hora, minuto, segundo);
	}

	/* setters */

	public void set(DataSemHora data, HoraMinutoSegundo horaMinutoSegundo) {
		setData(data);
		setHoraMinutoSegundo(horaMinutoSegundo);
	}
	
	public void set(Calendar value) {
		set(new DataSemHora(value), new HoraMinutoSegundo(value));
	}
	public void set(LocalDate value) {
		set(new DataSemHora(value.getYear(), value.getMonthValue()+1, value.getDayOfMonth()), new HoraMinutoSegundo(0,0,0));
	}

	public void set(Date value) {
		set(new DataSemHora(value), new HoraMinutoSegundo(value));
	}
	
	public void set(int ano, int mes, int dia) {
		set(ano, mes, dia, 0, 0, 0);
	}
	
	public void set(int ano, int mes, int dia, int hora, int minuto, int segundo) {
		set(new DataSemHora(ano, mes, dia), new HoraMinutoSegundo(hora, minuto, segundo));
	}
	
	/* getters */
	
	public int getAno() {
		return getData().getAno();
	}

	public int getMes() {
		return getData().getMes();
	}

	public int getDia() {
		return getData().getDia();
	}

	public int getHora() {
		return getHoraMinutoSegundo().getHora();
	}

	public int getMinuto() {
		return getHoraMinutoSegundo().getMinuto();
	}

	public int getSegundo() {
		return getHoraMinutoSegundo().getSegundo();
	}
	
	/* converters */

	public Calendar toCalendar() {
		Calendar o = new GregorianCalendar(getAno(), getMes()-1, getDia(), getHora(), getMinuto(), getSegundo());
		o.set(Calendar.MILLISECOND, 0);
		return o;
	}

	public String format(String s) {
		if (StringEmpty.is(s)) {
			return "";
		}
		s = horaMinutoSegundo.format(s);
		return data.format(s);
	}
	
	public String formatJs() {
		return format("[yyyy]-[mm]-[dd]T[hh]:[nn]:[ss].000");
	}

	public String formatSql() {
		return format("'[yyyy]-[mm]-[dd] [hh]:[nn]:[ss].000'");
	}
	
	public static DataComHora unformatJs(String dataHora) {
		if (StringEmpty.is(dataHora)) {
			return null;
		} else {
			return new DataComHora(Data.unformatJs(dataHora).getCalendar());
		}
	}

	public static DataComHora unformat(String s, String format) {
		if (StringEmpty.is(s)) {
			return null;
		} else {
			return new DataComHora(Data.unformat(format, s).getCalendar());
		}
	}
	
	public static DataComHora now() {
		return new DataComHora();
	}
	
	@Override
	public String toString() {
		String s = getData() + " " + getHoraMinutoSegundo();
		if (comentario != null) {
			s += " /* "+comentario+" */";
		}
		return s;
	}

	public static boolean eq(DataComHora a, DataComHora b) {
		
		if (a == b) {
			return true;
		} else if (a == null || b == null) {
			return false;
		} else {
			return a.eq(b);
		}
		
	}
	
	private boolean eq(DataComHora o) {
		return o != null && getData().eq(o.getData()) && getHoraMinutoSegundo().eq(o.getHoraMinutoSegundo());
	}

	public static boolean ne(DataComHora a, DataComHora b) {
		return !eq(a,b);
	}

	public DataComHora menosMinutos(int minutos) {
		HoraMinutoSegundo h = getHoraMinutoSegundo().copy();
		DataSemHora data = getData().menosDias(h.removeMinutos(minutos));
		DataComHora o = new DataComHora();
		o.set(data, h);
		return o;
	}

	public static DataComHora hoje() {
		return new DataComHora(DataSemHora.hoje().toCalendar());
	}
	public static DataComHora amanha() {
		return new DataComHora(DataSemHora.amanha().toCalendar());
	}

	public static DataComHora from(Calendar de) {
		if (de == null) {
			return null;
		} else {
			return new DataComHora(de);
		}
	}
	public static DataComHora from(LocalDate de) {
		if (de == null) {
			return null;
		} else {
			return new DataComHora(de);
		}
	}
	public static DataComHora from(Date de) {
		if (de == null) {
			return null;
		} else {
			return new DataComHora(Data.to(de).getCalendar());
		}
	}

	public static DataComHora from(Object de) {
		if (de == null) {
			return null;
		} else {
			return new DataComHora(Data.to(de).getCalendar());
		}
	}

}
