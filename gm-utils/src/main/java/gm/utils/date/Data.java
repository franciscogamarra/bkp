package gm.utils.date;

import java.io.Serializable;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Map;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import gm.utils.comum.Tripa;
import gm.utils.comum.UConstantes;
import gm.utils.comum.ULog;
import gm.utils.comum.UObject;
import gm.utils.exception.UException;
import gm.utils.lambda.F1;
import gm.utils.map.MapSO;
import gm.utils.map.MapSoFromObject;
import gm.utils.number.ULong;
import gm.utils.string.ListString;
import lombok.Getter;
import lombok.Setter;
import src.commom.utils.integer.IntegerFormat;
import src.commom.utils.integer.IntegerParse;
import src.commom.utils.string.StringBeforeLast;
import src.commom.utils.string.StringEmpty;
import src.commom.utils.string.StringEmptyPlus;
import src.commom.utils.string.StringExtraiNumeros;
import src.commom.utils.string.StringParse;
import src.commom.utils.string.StringPrimeiraMaiuscula;
import src.commom.utils.string.StringTrim;

@Getter @Setter
public class Data implements Serializable {

	private static final long serialVersionUID = -1L;

	private String toStringFormat = "[dd]/[mm]/[yyyy] [hh]:[nn]";

	private int ano;
	private int mes;
	private int dia;
	private int hora;
	private int minuto;
	private int segundo;
	private SimpleDateFormat format;

	public Data setCalendar(Calendar calendar) {
		refreshData(calendar);
		hora = calendar.get(Calendar.HOUR_OF_DAY);
		minuto = calendar.get(Calendar.MINUTE);
		segundo = calendar.get(Calendar.SECOND);
		return this;
	}

	private Data refreshData(Calendar calendar) {
		ano = calendar.get(Calendar.YEAR);
		mes = calendar.get(Calendar.MONTH) + 1;
		dia = calendar.get(Calendar.DATE);
		return this;
	}

	public static Calendar getCalendar(Data date) {
		return date == null ? null : date.getCalendar();
	}
	public Calendar getCalendar() {
		return MyCalendar.build(ano, mes - 1, dia, hora, minuto, segundo);
	}
	public static Data newDataZeroTime(Calendar c) {
		Data data = newData(c);
		if (data != null) {
			data.zeraTime();
		}
		return data;
	}
	public static Data newData(Date c) {
		if (c == null) {
			return null;
		}
		return new Data(c);
	}
	public static Data newData(Calendar c) {
		if (c == null) {
			return null;
		}
		return new Data(c);
	}
	public static Data newData(LocalDate c) {
		if (c == null) {
			return null;
		}
		return new Data(c);
	}
	public static Data newData(LocalDateTime c) {
		if (c == null) {
			return null;
		}
		return new Data(c);
	}
	public static Data newData(Long c) {
		if (c == null) {
			return null;
		}
		return new Data(c);
	}
	public Data(DateTime time) {
		this(time.toGregorianCalendar());
	}
	public Data(Calendar c) {
		setCalendar(c);
	}
	public Data() {
		this(nowCalendar());
	}
	public Data(long time) {
		this(new Date(time));
	}
	public Data(int ano, int mes, int dia) {
		this(ano, mes, dia, 0, 0);
	}
	public Data(int ano, int mes, int dia, int hora, int minuto) {
		this(ano, mes, dia, hora, minuto, 0);
	}
	
	public static void main(String[] args) {
		Data data = new Data(1901, 1, 1);
		while (data.menorQueHoje()) {
			data.add();
		}
	}
	
	public Data(int ano, int mes, int dia, int hora, int minuto, int segundo) {
		this(new GregorianCalendar());
		if (mes < 1 || mes > 12 || dia < 1 || dia > 31) {
			throw UException.runtime("Data inválida!");
		}
		// calendar.setTimeZone(null);
		// calendar.set(0, 0, 0, 0, 0, 0);
		setHora(0);
		setMinuto(0);
		setSegundo(0);
		setDia(1);
		setMes(1);
		setAno(ano);
		setMes(mes);
		setDia(dia);
		setHora(hora);
		setMinuto(minuto);
		setSegundo(segundo);
		if (getDia() != dia) {
			throw UException.runtime("Data inválida: "+ano+","+mes+","+dia+","+hora+","+minuto+","+segundo);
		}
	}

	@SuppressWarnings("deprecation")
	public Data(Timestamp d) {
		this(d.getYear() + 1900, d.getMonth() + 1, d.getDate(), d.getHours(), d.getMinutes(), d.getSeconds());
	}
	public Data(Data data) {
		this(data.getCalendar());
	}
	public Data(String s) {
		this(parse(s));
	}
	public Data(Date date) {
		this(new GregorianCalendar());
		set(date);
	}
	public Data(LocalDate date) {
		this(new GregorianCalendar());
		set(date);
		zeraTime();
	}
	public Data(LocalDateTime date) {
		this(new GregorianCalendar());
		set(date);
		zeraTime();
	}

	@SuppressWarnings("deprecation")
	public Data set(Date date) {
		setDia(1);
		setAno(date.getYear() + 1900);
		setMes(date.getMonth() + 1);
		setDia(date.getDate());
		try {
			setHora(date.getHours());
			setMinuto(date.getMinutes());
			setSegundo(date.getSeconds());
		} catch (Exception e) {
			zeraTime();
		}
		return this;
	}

	public Data set(LocalDate date) {
		setDia(1);
		setAno(date.getYear());
		setMes(date.getMonthValue());
		setDia(date.getDayOfMonth());
		return this;
	}

	public Data set(LocalDateTime date) {
		setDia(1);
		setAno(date.getYear());
		setMes(date.getMonthValue());
		setDia(date.getDayOfMonth());
		setHora(date.getHour());
		setMinuto(date.getMinute());
		setSegundo(date.getSecond());
		return this;
	}

	public Data setDiaSemUltrapassarMes(int dia) {
		int mes = getMes();
		setDia(dia);
		while (getMes() > mes) {
			removeDia();
		}
		return this;
	}
	public Data setDia(int dia) {
		Calendar calendar = getCalendar();
		calendar.set(Calendar.DATE, dia);
		refreshData(calendar);
		return this;
	}
	public Data setMes(int mes) {
		Calendar calendar = getCalendar();
		calendar.set(Calendar.MONTH, mes - 1);
		refreshData(calendar);
		return this;
	}
	public Data setAno(int ano) {
		Calendar calendar = getCalendar();
		calendar.set(Calendar.YEAR, ano);
		refreshData(calendar);
		return this;
	}
	public Data setHora(int hora) {
		if (hora < 0) {
			throw UException.runtime("hora < 0");
		}
		while (hora > 23) {
			hora -= 24;
			//			throw Utils.exception("hora > 23");
		}
		this.hora = hora;
		return this;
	}
	public Data setMinuto(int minuto) {
		if (minuto < 0) {
			throw UException.runtime("minuto < 0");
		}
		if (minuto > 59) {
			throw UException.runtime("minuto > 59");
		}
		this.minuto = minuto;
		return this;
	}
	public Data setSegundo(int segundo) {
		if (segundo < 0) {
			throw UException.runtime("segundo < 0");
		}
		if (segundo > 59) {
			throw UException.runtime("segundo > 59");
		}
		this.segundo = segundo;
		return this;
	}
	public int getDiaSemana() {
		return getCalendar().get(Calendar.DAY_OF_WEEK);
	}
	public boolean sabado() {
		return getDiaSemana() == 7;
	}
	public boolean domingo() {
		return getDiaSemana() == 1;
	}
	public static Integer getAno(Calendar c) {
		if (c == null) {
			return null;
		}
		return c.get(Calendar.YEAR);
	}
	public int getAno() {
		return getAno(getCalendar());
	}
	public Data print() {
		ULog.debug(format_ddd_dd_mm_yyyy_hh_mm_ss());
		return this;
	}
	public Data mais(int dias) {
		return mais(0,0,dias);
	}
	public Data mais(int anos, int meses, int dias) {
		Data data = copy();
		data.addAnos(anos);
		data.addMeses(meses);
		data.add(dias);
		return data;
	}
	public Data removeDia() {
		setDia(getDia() - 1);
		return this;
	}
	public Data add() {
		setDia(getDia() + 1);
		return this;
	}
	public Data add(int i) {
		if (i == 0) {
			return this;
		}
		setDia(getDia() + i);
		return this;
	}
	public Data back() {
		back(1);
		return this;
	}
	public Data back(int i) {
		if (i != 0) {
			setDia(getDia() - i);
		}
		return this;
	}
	public Data backAnos(int i) {
		setAno(getAno() - i);
		return this;
	}
	public Data addMeses(int i) {
		int dia = getDia();
		setDia(1);
		setMes(getMes() + i);
		int mes = getMes();
		setDia(dia);
		while (getMes() > mes) {
			removeDia();
		}
		return this;
	}
	public Data addMes() {
		addMeses(1);
		return this;
	}
	public Data addAnos(int i) {
		setAno(getAno() + i);
		return this;
	}
	public Data addAno() {
		addAnos(1);
		return this;
	}
	public Data addMinuto() {
		addMinutos(1);
		return this;
	}
	public Data removeMinuto() {
		removeMinutos(1);
		return this;
	}
	public Data addMinutos(int minutos) {
		minutos = getMinuto() + minutos;
		while (minutos > 59) {
			minutos -= 60;
			addHora();
		}
		while (minutos < 0) {
			minutos += 60;
			addHoras(-1);
		}
		setMinuto(minutos);
		return this;
	}
	public Data removeMinutos(int minutos) {
		minutos = getMinuto() - minutos;
		while (minutos < 0) {
			minutos += 60;
			removeHora();
		}
		setMinuto(minutos);
		return this;
	}
	public Data addHora() {
		addHoras(1);
		return this;
	}
	public Data removeHora() {
		removeHoras(1);
		return this;
	}
	public Data addHoras(int horas) {
		horas = getHora() + horas;
		while (horas > 23) {
			horas -= 24;
			add();
		}
		while (horas < 0) {
			horas += 24;
			add(-1);
		}
		setHora(horas);
		return this;
	}
	public Data removeHoras(int horas) {
		horas = getHora() - horas;
		while (horas < 0) {
			horas += 24;
			removeDia();
		}
		setHora(horas);
		return this;
	}
	public Data removeSegundos(int segundos) {
		int soma = getSegundo()-segundos;
		while ( soma < 0) {
			soma += 60;
			removeMinuto();
		}
		setSegundo(soma);
		return this;
	}
	public Data addSegundos(int segundos) {
		int soma = getSegundo()+segundos;
		while ( soma > 59) {
			soma -= 60;
			addMinuto();
		}
		setSegundo(soma);
		return this;
	}
	public String format_dd_mm_yyyy_hh_mm() {
		return format_dd_mm_yyyy() + " " + format_hh_mm();
	}
	public String format_dd_mm_yyyy_hh_mm_ss() {
		return format_dd_mm_yyyy_hh_mm() + ":" + IntegerFormat.xx(getSegundo());
	}
	public String format_dd_mmm_yyyy() {
		return format_dd_mmm() + "/" + getAno();
	}
	public String format_dd_mmm() {
		return format_dd() + "/" + nomeMes().substring(0, 3);
	}
	public String format_ddd_dd_mm_yyyy_hh_mm_ss() {
		return format_ddd_dd_mm_yyyy_hh_mm() + ":" + IntegerFormat.xx(getSegundo());
	}
	public String format_ddd_dd_mm_yyyy_hh_mm() {
		return format_ddd() + " " + format_dd_mm_yyyy_hh_mm();
	}
	public String format_ddd_dd_mm_yyyy() {
		return format_ddd() + " " + format_dd_mm_yyyy();
	}
	public String format_ddd() {
		return nomeDiaSemana().substring(0, 3);
	}
	public String format_hh_mm() {
		return IntegerFormat.xx(getHora()) + ":" + IntegerFormat.xx(getMinuto());
	}
	public String format_hhmmss() {
		return format_hh_mm_ss().replace(":", "");
	}
	public String format_hh_mm_ss() {
		return format_hh_mm() + ":" + IntegerFormat.xx(getSegundo());
	}
	public String format_dd_mm() {
		return format_dd() + "/" + format_mm();
	}
	public String format_dd_mm_yyyy() {
		return format_dd_mm() + "/" + getAno();
	}
	public String format_yyyy_mm_dd_hh_mm_ss() {
		return format_yyyy_mm()+ "/"+ format_dd() + " " + format_hh_mm_ss();
	}
	public String format() {
		return format_dd_mm_yyyy() + " - " + nomeDiaSemana();
	}
	public int bimestre(){
		int mes = getMes();
		if (mes < 3) {
			return 1;
		}
		if (mes < 5) {
			return 2;
		}
		if (mes < 7) {
			return 3;
		}
		if (mes < 9) {
			return 4;
		}
		if (mes < 11) {
			return 5;
		}
		return 6;
	}
	public int trimestre(){
		int mes = getMes();
		if (mes < 4) {
			return 1;
		}
		if (mes < 7) {
			return 2;
		}
		if (mes < 10) {
			return 3;
		}
		return 4;
	}
	public int quadrimestre(){
		int mes = getMes();
		if (mes < 5) {
			return 1;
		}
		if (mes < 9) {
			return 2;
		}
		return 3;
	}
	public int semestre(){
		int mes = getMes();
		if (mes < 7) {
			return 1;
		}
		return 2;
	}
	public String format(String s){
		s = s.replace("[ddddd]", nomeDiaSemana());//quinta-feira
		s = s.replace("[dddd]", nomeDiaSemana().split("-")[0]);//quinta
		s = s.replace("[ddd]", nomeDiaSemana().substring(0, 3));
		s = s.replace("[dd]", IntegerFormat.xx(getDia()));
		s = s.replace("[d]", ""+getDia());
		s = s.replace("[mmmm]", nomeMes());
		s = s.replace("[mmm]", nomeMes().substring(0, 3));
		s = s.replace("[mm]", IntegerFormat.xx(getMes()));
		s = s.replace("[m]", ""+getMes());
		s = s.replace("[yyyy]", ""+getAno());
		s = s.replace("[yy]", (""+getAno()).substring(2));
		s = s.replace("[hh]", IntegerFormat.xx(getHora()));
		s = s.replace("[h]", ""+getHora());
		s = s.replace("[nn]", IntegerFormat.xx(getMinuto()));
		s = s.replace("[n]", ""+getMinuto());
		s = s.replace("[ss]", IntegerFormat.xx(getSegundo()));
		s = s.replace("[s]", ""+getSegundo());
		s = s.replace("[b]", ""+bimestre());
		s = s.replace("[t]", ""+trimestre());
		s = s.replace("[q]", ""+quadrimestre());
		return s.replace("[se]", ""+semestre());
	}

	public static Data unString(String s) {
		s = StringTrim.plusNull(s);
		return s == null ? null : unformat("[yyyy]-[mm]-[dd] [hh]:[nn]:[ss]", s.substring(4));
	}

	public static Data unformatJs(String value) {
		return unformat("[yyyy]-[mm]-[dd]T[hh]:[nn]:[ss]", value);
	}
	
	public static Data unformat(String format, String value) {

		if (StringEmpty.is(value)) {
			return null;
		}

		if (StringEmpty.is(format)) {
			throw UException.runtime("Não foi informado o formato");
		}

		String message = "error Data.unformat(format = '" + format + "', value = '" + value + "')";

		if ( !format.contains("]") ) {
			throw UException.runtime("O formato informado é inválido: " + message);
		}

		if ("hoje".equalsIgnoreCase(value)) {
			return hoje();
		}

		if ("agora".equalsIgnoreCase(value) || "now".equalsIgnoreCase(value)) {
			return now();
		}

		if (format.startsWith("[d]/")) {
			
			for (int i = 1; i < 10; i++) {
				if (value.startsWith(i + "/")) {
					value = "0" + value;
					break;
				}
			}
			
			format = "[d" + format.substring(1);
			
		}
		
		if (format.contains("/[m]/")) {
			for (int i = 1; i < 10; i++) {
				value = value.replace("/"+i+"/", "/0"+i+"/");
			}
			format = format.replace("/[m]/", "/[mm]/");
		}

		if (format.startsWith("[m]/")) {
			
			for (int i = 1; i < 10; i++) {
				if (value.startsWith(i + "/")) {
					value = "0" + value;
					break;
				}
			}
			
			format = "[m" + format.substring(1);
			
		}
		
		if (format.contains("/[d]/")) {
			for (int i = 1; i < 10; i++) {
				value = value.replace("/"+i+"/", "/0"+i+"/");
			}
			format = format.replace("/[d]/", "/[dd]/");
		}
		
		try {

			Data data = new Data(2016,1,1);
			data.zeraTime();

			value = value.toLowerCase();

			while ( !format.isEmpty() ) {

				if ( format.toLowerCase().startsWith("[yyyy]") ) {
					format = format.substring(6);
					int ano = IntegerParse.toInt( value.substring(0,4) );
					if (ano < 1900 || ano > 2030) {
						throw UException.runtime("ano < 1900 || ano > 2030: " + ano);
					}
					value = value.substring(4);
					data.setAno(ano);
					continue;
				}
				if ( format.toLowerCase().startsWith("[yy]") ) {
					format = format.substring(4);
					int ano = IntegerParse.toInt( value.substring(0,2) ) + 1900;
					value = value.substring(2);
					data.setAno(ano);
					continue;
				}
				if ( format.toLowerCase().startsWith("[m]") ) {
					format = format.substring(3);
					int mes = IntegerParse.toInt( value.substring(0,1) );
					value = value.substring(1);
					data.setMes(mes);
					continue;
				}
				if ( format.toLowerCase().startsWith("[mm]") ) {
					format = format.substring(4);
					int mes = IntegerParse.toInt( value.substring(0,2) );
					if (mes < 1 || mes > 12) {
						throw UException.runtime("mes < 1 || mes > 12: " + mes);
					}
					value = value.substring(2);
					data.setMes(mes);
					continue;
				}
				if ( format.toLowerCase().startsWith("[mmm]") ) {

					format = format.substring(5);

					boolean conseguiu = false;

					for (int i = 1; i <= 12; i++) {
						String nomeMes = nomeMes(i).toLowerCase();
						nomeMes = nomeMes.substring(0,3);
						if (value.startsWith(nomeMes)) {
							value = value.substring(nomeMes.length());
							data.setMes(i);
							conseguiu = true;
							break;
						}
						nomeMes = nomeMesIngles(i).toLowerCase();
						nomeMes = nomeMes.substring(0,3);
						if (value.startsWith(nomeMes)) {
							value = value.substring(nomeMes.length());
							data.setMes(i);
							conseguiu = true;
							break;
						}
					}
					if (!conseguiu) {
						throw new RuntimeException("!conseguiu");
					}
					continue;
				}
				if ( format.toLowerCase().startsWith("[mmmm]") ) {
					format = format.substring(6);

					boolean conseguiu = false;

					for (int i = 1; i <= 12; i++) {
						String nomeMes = nomeMes(i).toLowerCase();
						if (value.startsWith(nomeMes)) {
							value = value.substring(nomeMes.length());
							data.setMes(i);
							conseguiu = true;
							break;
						}
						nomeMes = nomeMesIngles(i).toLowerCase();
						if (value.startsWith(nomeMes)) {
							value = value.substring(nomeMes.length());
							data.setMes(i);
							conseguiu = true;
							break;
						}
					}
					if (!conseguiu) {
						throw new RuntimeException("!conseguiu");
					}
					continue;
				}
				if ( format.toLowerCase().startsWith("[dd]") ) {
					format = format.substring(4);
					int dia = IntegerParse.toInt( value.substring(0,2) );
					if (dia < 0 || dia > 31) {
						throw UException.runtime("dia < 0 || dia > 31: " + dia);
					}
					value = value.substring(2);
					data.setDia(dia);
					continue;
				}
				if ( format.toLowerCase().startsWith("[d]") ) {
					format = format.substring(3);
					int dia = IntegerParse.toInt( value.substring(0,1) );
					value = value.substring(1);
					data.setDia(dia);
					continue;
				}
				if ( format.toLowerCase().startsWith("[hh]") ) {
					format = format.substring(4);
					int hora = IntegerParse.toInt( value.substring(0,2) );
					value = value.substring(2);
					data.setHora(hora);
					continue;
				}
				if ( format.toLowerCase().startsWith("[nn]") ) {
					format = format.substring(4);
					int minuto = IntegerParse.toInt( value.substring(0,2) );
					value = value.substring(2);
					data.setMinuto(minuto);
					continue;
				}
				if ( format.toLowerCase().startsWith("[ss]") ) {
					format = format.substring(4);
					int segundo = IntegerParse.toInt( value.substring(0,2) );
					value = value.substring(2);
					data.setSegundo(segundo);
					continue;
				}

				format = format.substring(1);
				value = value.substring(1);

			}

			return data;

		} catch (Exception e) {
			throw UException.runtime(message + " >> " + e.getMessage());
		}

	}
	public String format_dd() {
		return IntegerFormat.xx(getDia());
	}
	public String format_mm() {
		return IntegerFormat.xx(getMes());
	}
	public String format_yyyymmdd() {
		return "" + getAno() + format_mm() + format_dd();
	}
	public String format_yyyymmddhhnnss() {
		return format_yyyymmdd() + format_hhmmss();
	}
	public static String format(Calendar c) {
		if (c == null) {
			return "";
		}
		return new Data(c).format();
	}
	public String format_mmmm_yyyy() {
		return nomeMes() + "/" + getAno();
	}
	public String toExtenso() {
		return nomeDiaSemana() + ", " + format_dd_de_mmmm_de_yyyy() + " "+UConstantes.a_crase+"s " + format_hh_mm();
	}
	public String format_ddmmyyyy() {
		return IntegerFormat.xx(getDia()) + IntegerFormat.xx(getMes()) + getAno();
	}
	public String format_mmmm_de_yyyy() {
		return nomeMes() + " de " + getAno();
	}
	public String format_dd_de_mmmm_de_yyyy() {
		return getDia() + " de " + format_mmmm_de_yyyy();
	}
	public String nomeMes() {
		return nomeMes(getMes());
	}
	public static String nomeMes(int i) {
		switch (i) {
		case 1:
			return "Janeiro";
		case 2:
			return "Fevereiro";
		case 3:
			return "Março";
		case 4:
			return "Abril";
		case 5:
			return "Maio";
		case 6:
			return "Junho";
		case 7:
			return "Julho";
		case 8:
			return "Agosto";
		case 9:
			return "Setembro";
		case 10:
			return "Outubro";
		case 11:
			return "Novembro";
		case 12:
			return "Dezembro";
		}
		throw new RuntimeException("Mês inválido: " + i);
	}
	public static String nomeMesIngles(int i) {
		switch (i) {
		case 1:
			return "January";
		case 2:
			return "February";
		case 3:
			return "March";
		case 4:
			return "April";
		case 5:
			return "May";
		case 6:
			return "June";
		case 7:
			return "July";
		case 8:
			return "August";
		case 9:
			return "September";
		case 10:
			return "October";
		case 11:
			return "November";
		case 12:
			return "December";
		}
		throw UException.runtime("M"+UConstantes.e_circunflexo+"s inválido: " + i);
	}

	@Override
	public String toString() {
		if (format != null) {
			return format.format(getCalendar().getTime());
		}
		return format(toStringFormat);
	}

	public String nomeDiaSemana() {
		return nomeDiaSemana(getDiaSemana());
	}
	public static String nomeDiaSemana(int i) {
		switch (i) {
		case 1:
			return "Domingo";
		case 2:
			return "Segunda-feira";
		case 3:
			return "Terça-feira";
		case 4:
			return "Quarta-feira";
		case 5:
			return "Quinta-feira";
		case 6:
			return "Sexta-feira";
		case 7:
			return "Sábado";
		}
		throw UException.runtime("Dia de semana inválido: " + i);
	}

	private static final int HASH0 = 60 * 60;
	private static final int HASH1 = 24 * 60 * 60;
	private static final int HASH2 = 365 * 24 * 60 * 60;

	@Override
	public int hashCode() {
		int dia = getCalendar().get(Calendar.DAY_OF_YEAR);
		int i = Integer.MIN_VALUE;
		i += segundo;
		i += minuto * 60;
		i += hora * HASH0;
		i += dia * HASH1;
		int ano = this.ano - 1900;
		i += ano * HASH2;
		return i;
	}
	public boolean maiorOuIgual(Calendar c) {
		return maiorOuIgual(new Data(c));
	}
	public boolean maiorOuIgual(Data d) {
		return maior(d) || eq(d);
	}
	public boolean maiorOuIgual(Date d) {
		return maior(d) || eq(d);
	}
	public boolean maior(Date d) {
		return maior(to(d));
	}
	public boolean maior(Data d) {
		if (d == null) {
			return true;
		}
		return hashCode() > d.hashCode();
	}
	public boolean maior(Long time) {
		if (time == null) {
			return true;
		}
		return maior(new Data(time));
	}
	public boolean maiorQueHoje() {
		return maior(hoje());
	}
	public boolean menorQueHoje() {
		return menor(hoje());
	}
	public boolean maior(Calendar d) {
		if (d == null) {
			return true;
		}
		return maior(new Data(d));
	}

	public boolean menor(Calendar d) {
		return menor(new Data(d));
	}
	public boolean menor(Date d) {
		return menor(new Data(d));
	}
	public boolean menorOuIgual(Calendar d) {
		return menorOuIgual(new Data(d));
	}
	public boolean menorOuIgual(Data d) {
		return menor(d) || eq(d);
	}
	public boolean menorOuIgual(Date d) {
		return menor(d) || eq(d);
	}
	public boolean menor(Data d) {
		return hashCode() < d.hashCode();
	}
	public static Data maior(Data a, Data b) {
		if (a == null) {
			return b;
		}
		if ((b == null) || a.maior(b)) {
			return a;
		}
		return b;
	}
	public static Data menor(Data a, Data b) {
		if (a == null) {
			return b;
		}
		if ((b == null) || a.menor(b)) {
			return a;
		}
		return b;
	}
	public int dias_uteis_no_mes() {
		Data data = copy();
		data.setDia(1);
		return data.dias_uteis_ate_o_fim_do_mes();
	}
	public int dias_uteis_ate_o_fim_do_mes() {
		Data data = copy();
		while (data.getMes() == getMes()) {
			data.add();
		}
		data.back();
		return dias_uteis_ate(data);
	}
	public int dias_uteis_ate(int ano, int mes, int dia) {
		Data data = new Data(ano, mes, dia);
		return dias_uteis_ate(data);
	}
	public int dias_uteis_ate(Data fim) {
		Data data = copy();
		int i = 0;
		while (data.menor(fim)) {
			if (data.diaUtil()) {
				i++;
			}
			data.add();
		}
		return i;
	}
	public Data menosDiasUteis(int dias) {
		Data data = copy();
		while (dias > 0) {
			if (data.diaUtil()) {
				dias--;
			}
			data.add(-1);
		}
		return data;
	}
	public Data menos(int dias) {
		Data copy = copy();
		copy.back(dias);
		return copy;
	}
	public int menos(Calendar d) {
		return menos(new Data(d));
	}
	public int subtraiDataEmSegundos(Calendar d) {
		long subtracao = this.getCalendar().getTimeInMillis() - d.getTimeInMillis();
		Double totalDeSegundos = subtracao*0.001;
		return totalDeSegundos.intValue();
	}
	public int dias_ate_hoje() {
		return ate(now());
	}
	public int ate(Data d) {
		return d.menos(this);
	}
	public int menos(Data d) {
		return diferenca(d).emDias();
	}

	private static Data NOW_FAKE;

	public static void setNOW_FAKE(Data nOW_FAKE) {
		NOW_FAKE = nOW_FAKE;
	}

	public static Data now() {

		if (NOW_FAKE == null) {
			return new Data();
		}
		return NOW_FAKE.copy();

	}

	public static Data nowServidor() {
		return new Data(Calendar.getInstance());
	}

	public static Data ontem() {
		Data data = hoje();
		data.add(-1);
		return data;
	}
	public static Data hoje() {
		Data data = now();
		data.zeraTime();
		return data;
	}
	public static Data amanha() {
		Data data = hoje();
		data.add();
		return data;
	}

	public boolean eq(int ano, int mes, int dia) {
		return this.ano == ano && this.mes == mes && this.dia == dia;
	}

	public boolean eq(Date x) {
		return eq(Data.to(x));
	}

	public boolean eq(Data x) {
		if (x == null) {
			return false;
		}
		return hashCode() == x.hashCode();
	}

	public boolean eq(Calendar c) {
		return eq(new Data(c));
	}

	public boolean eq(Long x) {

		if (x == null) {
			return false;
		}
		return eq(new Data(x));

	}

	public static String mensagemBomMomento() {
		Data now = now();
		if (now.getHora() < 12) {
			return "bom dia!";
		}
		if (now.getHora() < 18) {
			return "boa tarde!";
		}
		return "boa noite!";
	}
	public static java.util.Date nowDate() {
		return now().toDate();
	}

	public static Calendar nowCalendar() {

		if (NOW_FAKE == null) {
			return Calendar.getInstance();
		}

		return NOW_FAKE.getCalendar();

	}

	public Data zeraTime() {
		setHora(0);
		setMinuto(0);
		setSegundo(0);
		return this;
	}
	public Data copy() {
		Data data = new Data(getAno(), getMes(), getDia(), getHora(), getMinuto(), getSegundo());
		data.setFormat(format);
		data.setToStringFormat(toStringFormat);
		return data;
	}
	public Data setUltimoDiaMes() {
		setDia(1);
		addMes();
		back();
		return this;
	}
	public boolean isUltimoDiaDoMes() {
		Data o = copy();
		o.setUltimoDiaMes();
		return eq(o);
	}
	public int getIdade() {
		Data hoje = now();
		if (maiorOuIgual(hoje) || (hoje.getAno() == getAno())) {
			return 0;
		}
		int idade = hoje.getAno() - getAno();
		if (getMes() > hoje.getMes()) {
			return idade - 1;
		}
		if (getMes() < hoje.getMes()) {
			return idade;
		}
		if (getDia() > hoje.getDia()) {
			return idade - 1;
		}
		return idade;
	}
	public Tempo diferenca(Date data) {
		return diferenca(newData(data));
	}
	public Tempo diferenca(Calendar data) {
		return diferenca(newData(data));
	}
	public Tempo diferenca(Data data) {
		return new Tempo(this, data);
	}
	public Tempo diferenca(LocalDate data) {
		return diferenca(newData(data));
	}
	public Tempo diferenca(LocalDateTime data) {
		return diferenca(newData(data));
	}
	public boolean feriadoRecessoNaoFuncionamento(){
		return UFeriadoRecessoNaoFuncionamento.test(this);
	}

	public boolean diaUtil(boolean funcionaAosSabados, boolean funcionaAosDomingos) {

		if (sabado()) {
			if (!funcionaAosSabados) {
				return false;
			}
		} else if (domingo() && !funcionaAosDomingos) {
			return false;
		}

		if (feriadoRecessoNaoFuncionamento()) {
			return false;
		}

		return true;

	}

	public boolean diaUtil() {
		return diaUtil(false, false);
	}

	public boolean isHoje() {
		Data hoje = hoje();
		if ((hoje.getAno() != getAno()) || (hoje.getMes() != getMes()) || (hoje.getDia() != getDia())) {
			return false;
		}
		return true;
	}
	public boolean equals(int ano, int mes, int dia) {
		if ((getAno() != ano) || (getMes() != mes) || (getDia() != dia)) {
			return false;
		}
		return true;
	}
	public String format_sqlDia() {
		return format_sqlDia(true);
	}
	public String format_sqlDia(boolean aspas) {
		String s = getAno() + "-" + format_mm() + "-" + format_dd();
		if (aspas) {
			s = "'" + s + "'";
		}
		return s;
	}
	public String format_sql(boolean aspas) {

		String s = format_sqlDia(false);

		if (hora > 0 || minuto > 0 || segundo > 0) {
			s += " " + format_hh_mm_ss();
		}

		if (aspas) {
			s = "'" + s + "'";
		}

		return s;

	}
	public String format_yyyy_mm() {
		return getAno() + "/" + format_mm();
	}
	public static Data fromExcel(int i) {
		// 39624 = 25/06/2008
		Data data = new Data(1900, 1, 1);
		data.add(i);
		data.add(-2);
		return data;
	}

	private static ListString diasSemanaAbrev = ListString.array("dom","seg","ter","qua","qui","sex","sáb");

	private static Data parse(String value) {

		if (value.contains(".")) {
			value = StringBeforeLast.get(value, ".");
		}

		for (String s : diasSemanaAbrev) {
			s = StringPrimeiraMaiuscula.exec(s);
			value = value.replace(s + " ", "");
		}

		value = value.replace("/jan/", "/01/");
		value = value.replace("/fev/", "/02/");
		value = value.replace("/feb/", "/02/");
		value = value.replace("/mar/", "/03/");
		value = value.replace("/apr/", "/04/");
		value = value.replace("/abr/", "/04/");
		value = value.replace("/mai/", "/05/");
		value = value.replace("/jun/", "/06/");
		value = value.replace("/jul/", "/07/");
		value = value.replace("/ago/", "/08/");
		value = value.replace("/aug/", "/08/");
		value = value.replace("/set/", "/09/");
		value = value.replace("/sep/", "/09/");
		value = value.replace("/out/", "/10/");
		value = value.replace("/oct/", "/10/");
		value = value.replace("/nov/", "/11/");
		value = value.replace("/dez/", "/12/");
		value = value.replace("/dec/", "/12/");

		try {
			return unformat("[dd]/[mm]/[yyyy]", value);
		} catch (Exception e) {}

		try {
			return unformat("[dd]/[mm]/[yy]", value);
		} catch (Exception e) {}

		try {
			return unformat("[d]/[mm]/[yyyy]", value);
		} catch (Exception e) {}

		try {
			return unformat("[d]/[mm]/[yy]", value);
		} catch (Exception e) {}

		try {
			if (value.contains(" GMT")) {
				//				Wed Dec 11 2019 23:56:21 GMT-0300 (Horario Padrao de Brasilia) {}
				String s = value.substring(4);
				s = StringBeforeLast.get(s, " GMT");
				//				Dec 11 2019 23:56:21
				return unformat("[mmm] [dd] [yyyy] [hh]:[nn]:[ss]", s);
			}
			String s = StringExtraiNumeros.exec(value);
			return unformat("[yyyy][mm][dd][hh][nn][ss]", s);
		} catch (Exception e) {}

		try {
			return parse(value, "dd/MM/yyyy");
		} catch (Exception e) {}
		try {
			return parse(value, "yyyy-MM-dd");
		} catch (Exception e) {}
		try {
			return parse(value, "yyyy-MM-dd HH:mm:ss");
		} catch (Exception e) {}
		try {
			return parse(value, "dd/MM/yyyy HH:mm:ss");
		} catch (Exception e) {}
		try {
			return parse(value, "dd/MM/yyyy HH:mm");
		} catch (Exception e) {}

		String s = value.trim().toLowerCase();
		s = s.replace(" jan ", " 01 ");
		s = s.replace(" fev ", " 02 ");
		s = s.replace(" feb ", " 02 ");
		s = s.replace(" mar ", " 03 ");
		s = s.replace(" apr ", " 04 ");
		s = s.replace(" abr ", " 04 ");
		s = s.replace(" mai ", " 05 ");
		s = s.replace(" jun ", " 06 ");
		s = s.replace(" jul ", " 07 ");
		s = s.replace(" ago ", " 08 ");
		s = s.replace(" aug ", " 08 ");
		s = s.replace(" set ", " 09 ");
		s = s.replace(" sep ", " 09 ");
		s = s.replace(" out ", " 10 ");
		s = s.replace(" oct ", " 10 ");
		s = s.replace(" nov ", " 11 ");
		s = s.replace(" dez ", " 12 ");
		s = s.replace(" dec ", " 12 ");
		try {
			String string = s.substring(4);
			string = StringBeforeLast.get(string, " GMT");
			return parse(string, "MM dd YYYY HH:mm:ss");
		} catch (Exception e) {
		}

		if (s.substring(2).startsWith("/") && s.substring(5).startsWith("/") && s.length() == 10) {
			return unformat("[dd]/[mm]/[yyyy]", s);
		}

		try {
			return fromSql(value);
		} catch (Exception e) {
			// TODO: handle exception
		}

		s = StringExtraiNumeros.exec(s);

		if (s.length() == 8) {

			if (s.startsWith("19") || s.startsWith("20")) {
				try {
					return unformat("[yyyy][mm][dd]", s);
				} catch (Exception e) {}
			}

			return unformat("[dd][mm][yyyy]", s);

		}

		throw UException.runtime("Invalid Date: " + value);
	}
	private static Data parse(String value, String pattern) {
		DateTimeFormatter format = DateTimeFormat.forPattern(pattern);
		DateTime time = format.parseDateTime(value);
		return new Data(time);
	}

	public Date toDate() {
		return getCalendar().getTime();
	}

	public java.sql.Date toSqlDate() {
		return new java.sql.Date(getTimeInMillis());
	}

	public static Data to(Object o) {

		try {

			if (UObject.isEmpty(o)) {
				return null;
			}

			if (o instanceof Data) {
				return (Data) o;
			}

			if (o instanceof Calendar) {
				Calendar x = (Calendar) o;
				return new Data(x);
			}

			if (o instanceof java.util.Date) {
				java.util.Date x = (java.util.Date) o;
				return new Data(x);
			}

			if (o instanceof java.sql.Date) {
				java.sql.Date x = (java.sql.Date) o;
				return new Data(x);
			}

			if (o instanceof java.sql.Timestamp) {
				java.sql.Timestamp x = (java.sql.Timestamp) o;
				return new Data(x);
			}

			if (o instanceof LocalDate) {
				LocalDate x = (LocalDate) o;
				return new Data(x);
			}

			if ((o instanceof Long) || (o instanceof Integer)) {
				return new Data(ULong.toLong(o));
			}

			if (o instanceof String) {
				String s = (String) o;
				if ("@now".equalsIgnoreCase(s) || "now".equalsIgnoreCase(s)) {
					return Data.now();
				}
				if ("@hoje".equalsIgnoreCase(s) || "hoje".equalsIgnoreCase(s)) {
					return Data.hoje();
				}
				return new Data(s);
			}

			if (o instanceof Map) {

				MapSO map = MapSoFromObject.get(o);

				Integer x = map.getInt("ano");

				if (x != null) {

					Data data = new Data(x, 1, 1);

					x = map.getInt("mes");
					if (x != null) {
						data.setMes(x);
					}

					x = map.getInt("dia");
					if (x != null) {
						data.setDia(x);
					}

					x = map.getInt("hora");
					if (x != null) {
						data.setHora(x);
					}

					x = map.getInt("minuto");
					if (x != null) {
						data.setMinuto(x);
					}

					x = map.getInt("segundo");
					if (x != null) {
						data.setSegundo(x);
					}

					return data;

				}

			}

		} catch (Throwable e) {
			throw UException.runtime("Erro ao tentar converter a o objeto em data: " + o);
		}

		if ( UDate.isData(o) ) {
			throw UException.runtime("Não tratado!");
		}

		String s = o.toString();

		if (StringEmptyPlus.is(s)) {
			return null;
		}

		return new Data(s);

	}
	public static Data fromSql(String s) {

		if (StringEmpty.is(s)) {
			return null;
		}

		try {

			Tripa tripa = new Tripa(s);

			int ano = tripa.getInt(4);
			tripa.get(1);
			int mes = tripa.getInt(2);
			tripa.get(1);
			int dia = tripa.getInt(2);

			if (tripa.isEmpty()) {
				return new Data(ano, mes, dia);
			}

			tripa.get(1);

			int hora = tripa.getInt(2);
			tripa.get(1);
			int minuto = tripa.getInt(2);

			if (tripa.isEmpty()) {
				return new Data(ano, mes, dia, hora, minuto);
			}

			tripa.get(1);
			int segundo = tripa.getInt(2);
			return new Data(ano, mes, dia, hora, minuto, segundo);

		} catch (Exception e) {
			throw UException.runtime("Não foi possível converter em data: " + s, e);
		}

	}
	public static String fromSqlToString(String s) {

		if ( StringEmpty.is(s) ) {
			return "";
		}

		return Data.fromSql(s).format();

	}

	public Data proximoDiaUtil(Data data){
		while (!data.diaUtil()) {
			data.add(+1);
		}
		return data;
	}

	public Data proximoDiaUtil(){
		return copy().add().proximoDiaUtilContandoComEsta();
	}

	public Data proximoDiaUtilPraTras(){
		return copy().back().proximoDiaUtilPraTrasContandoComEsta();
	}
	
	public Data proximoDiaUtilContandoComEsta(){
		while (!this.diaUtil()) {
			this.add(1);
		}

		return this;
	}

	public Data proximoDiaUtilPraTrasContandoComEsta(){
		while (!this.diaUtil()) {
			back();
		}
		return this;
	}
	
	public Date date(){
		return this.getCalendar().getTime();
	}
	//	nao remover
	public Date getDate(){
		return date();
	}
	public java.sql.Date getSqlDate(){
		return new java.sql.Date(getTimeInMillis());
	}
	public boolean jaPassou() {
		return hoje().maior(this);
	}

	public int semanaDoAno() {
		return getCalendar().get(Calendar.WEEK_OF_YEAR);
	}

	public static boolean isHoje(Calendar o) {
		if (o == null) {
			return false;
		}
		return new Data(o).isHoje();
	}

	public boolean mesmoDiaQue(Data data) {
		return getDia() == data.getDia() && getMes() == data.getMes() && getAno() == data.getAno();
	}

	public Data addMinutosUteis(int x){

		if (getHora() > 18) {
			zeraTime();
			setHora(8);
			add();
		} else if (getHora() < 8) {
			zeraTime();
			setHora(8);
		} else if (getHora() == 13) {
			zeraTime();
			setHora(14);
		}

		addMinutos(x);

		if (getHora() > 18) {
			setHora(8);
			add();
			return this;
		}

		if (getHora() == 13) {
			setHora(14);
			return this;
		}

		if (getHora() == 12 && getMinuto() > 0) {
			setHora(14);
		}

		return this;

	}

	public int diferencaEmDiasUteis(Data data) {

		Data maior, menor;

		if (maior(data)) {
			menor = data;
			maior = copy();
		} else {
			menor = copy();
			maior = data;
		}

		int x = 0;

		while ( !menor.mesmoDiaQue(maior) ) {
			if (menor.diaUtil()) {
				x++;
			}
			menor.add();
		}

		return x;

	}

	public Data setUltimoMomentoDoDia() {
		setHora(23);
		setMinuto(59);
		setSegundo(59);
		return this;
	}

	private static final F1<Tempo, Integer> EM_HORAS = o -> o.emHoras();
	private static final F1<Tempo, Integer> EM_MINUTOS = o -> o.emMinutos();
	private static final F1<Tempo, Integer> EM_SEGUNDOS = o -> o.emSegundos();

	private boolean jaPassou(int x, F1<Tempo, Integer> func) {

		Data now = Data.now();

		if (maiorOuIgual(now)) {
			return false;
		}

		return func.call(diferenca(now)) >= x;

	}

	public boolean jaPassouSegundos(int x) {
		return jaPassou(x, EM_SEGUNDOS);
	}

	public boolean jaPassouMinutos(int x) {
		return jaPassou(x, EM_MINUTOS);
	}

	public boolean jaPassouHoras(int x) {
		return jaPassou(x, EM_HORAS);
	}

	public String formatTela() {
		return format_ddd_dd_mm_yyyy_hh_mm_ss();
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if ((obj == null) || (getClass() != obj.getClass())) {
			return false;
		}
		Data other = (Data) obj;
		if ((ano != other.ano) || (dia != other.dia)) {
			return false;
		}
		if ((hora != other.hora) || (mes != other.mes) || (minuto != other.minuto) || (segundo != other.segundo)) {
			return false;
		}
		return true;
	}

	public Data save(String filename) {
		ListString list = new ListString();
		list.add(StringParse.get(getDate().getTime()));
		list.save(filename);
		return this;
	}

	public static Data load(String filename) {
		return new Data(ULong.toLong(new ListString().load(filename).get(0)));
	}

	public int getAnoMes() {
		return getAno() * 100 + getMes();
	}

	public static int compare(Data a, Data b) {
		if (a == b) {
			return 0;
		}
		if (a == null) {
			return -1;
		}
		return a.compare(b);

	}

	public int compare(Data data) {
		if (data == null) {
			return 1;
		}
		if (eq(data)) {
			return 0;
		}
		if (maior(data)) {
			return 1;
		}
		if (menor(data)) {
			return -1;
		}
		throw new RuntimeException("???");
	}

	public LocalDate toLocalDate() {
		Calendar calendar = getCalendar();
		return LocalDateTime.ofInstant(calendar.toInstant(), calendar.getTimeZone().toZoneId()).toLocalDate();
	}

	public long getTimeInMillis() {
		return getCalendar().getTimeInMillis();
	}

	public Data removeMes() {
		int dia = getDia();
		setDia(1);
		removeDia();
		if (dia < getDia()) {
			setDia(dia);
		}
		return this;
	}

	public static Date toDate(Data data) {
		return data == null ? null : data.date();
	}

	public static String sqlDia(Date data, boolean aspas) {
		return sqlDia(to(data), aspas);
	}

	public static String sqlDia(Data data, boolean aspas) {
		return data == null ? null : data.format_sqlDia(aspas);
	}

	public Timestamp getTimestamp() {
		return new Timestamp(getTimeInMillis());
	}

	public String getFormatoBanco() {
		return format_sql(false);
	}

	public String getFormat1() {
		return format("[dd]/[mm]/[yyyy] [hh]:[nn]:[ss]");
	}

	public String getFormat2() {
		return format("[dd]/[mm]/[yyyy] [hh]:[nn]");
	}

	public String getFormat3() {
		return format("[dd]/[mm]/[yyyy]");
	}

	public boolean isSomenteData() {
		return getHora() == 0 && getMinuto() == 0 && getSegundo() == 0;
	}

	public int getHoraMinuto() {
		return getHora() * 100 + getMinuto();
	}

	public String toSqlOracle() {
		if (copy().zeraTime().eq(this)) {
			return format("to_date('[yyyy]-[mm]-[dd]', 'YYYY-MM-DD')");
		} else {
			return format("to_date('[yyyy]-[mm]-[dd] [hh]:[nn]:[ss]', 'YYYY-MM-DD HH24:MI:SS')");
		}
	}

}
