package src.commom.utils.tempo;

import gm.languages.ts.javaToTs.annotacoes.PodeSerNull;
import js.Js;
import js.outros.Date;
import lombok.Getter;
import src.commom.utils.comum.Randomico;
import src.commom.utils.integer.IntegerCompare;
import src.commom.utils.integer.IntegerFormat;
import src.commom.utils.integer.IntegerParse;
import src.commom.utils.object.Null;
import src.commom.utils.string.StringContains;
import src.commom.utils.string.StringEmpty;
import src.commom.utils.string.StringExtraiNumeros;
import src.commom.utils.string.StringLength;

@Getter
public class Hora {
	
	public static final Hora ZERO = new Hora(0, 0, 0);
	public static final Hora ULTIMA = new Hora(23, 59, 59);
	
	private Data dataObserver;
	
	private int hora;
	private int minuto;
	private int segundo;
	
	public Hora(int hora, int minuto, int segundo) {
		this.hora = hora;
		this.minuto = minuto;
		this.segundo = segundo;
	}

	public static Hora now() {
		Date d = new Date();
		return new Hora(d.getHours(), d.getMinutes(), d.getSeconds());
	}
	
	public String format(String s) {
		
		if (StringEmpty.is(s)) {
			return "";
		}
		
		s = s.replace("[hh]", IntegerFormat.xx(getHora()));
		s = s.replace("[h]", "" + getHora());

		s = s.replace("[nn]", IntegerFormat.xx(getMinuto()));
		s = s.replace("[n]", "" + getMinuto());

		s = s.replace("[ss]", IntegerFormat.xx(getSegundo()));
		s = s.replace("[s]", "" + getSegundo());

		return s;
	}
	
	public String formatJs() {
		return format("[hh]:[nn]:[ss]");
	}
	
	public static Hora unformatJs(String s) {
		return unformat("[hh]:[nn]:[ss]", s);
	}

	@Override
	public String toString() {
		return formatJs();
	}
	
	private Error invalidError() {
		return new Error("Data Inválida");
	}
	
	public Hora setHora(int value) {
		if (value < 0 || value > 23) {
			throw invalidError();
		}
		hora = value;
		return this;
	}
	
	public Hora setMinuto(int value) {
		if (value < 0 || value > 59) {
			throw invalidError();
		}
		minuto = value;
		return this;
	}

	public Hora setSegundo(int value) {
		if (value < 0 || value > 59) {
			throw invalidError();
		}
		segundo = value;
		return this;
	}
	
	
	@PodeSerNull
	public static Hora unformat(String format, String value) {

		if (StringEmpty.is(value)) {
			return null;
		}
		if (StringEmpty.is(format)) {
			throw new Error("Não foi informado o formato");
		}

		String message = "error Data.unformat(format = '" + format + "', value = '" + value + "')";

		if (!StringContains.is(format, "]")) {
			throw new Error("O formato informado é inválido: " + message);
		}

		try {

			Hora data = new Hora(0, 0, 0);

			value = value.toLowerCase();

			while (!StringEmpty.is(format)) {

				if (format.toLowerCase().startsWith("[hh]")) {
					format = format.substring(4);
					int horaP = IntegerParse.toIntSafe(value.substring(0, 2));
					value = value.substring(2);
					data.setHora(horaP);
					continue;
				}
				if (format.toLowerCase().startsWith("[nn]")) {
					format = format.substring(4);
					int minutoP = IntegerParse.toIntSafe(value.substring(0, 2));
					value = value.substring(2);
					data.setMinuto(minutoP);
					continue;
				}
				if (format.toLowerCase().startsWith("[ss]")) {
					format = format.substring(4);
					int segundoP = IntegerParse.toIntSafe(value.substring(0, 2));
					value = value.substring(2);
					data.setSegundo(segundoP);
					continue;
				}

				format = format.substring(1);
				value = value.substring(1);

			}

			return data;

		} catch (Exception e) {
			throw new Error(message);
		}

	}
	
	public void addHora() {
		hora++;
		if (hora > 23) {
			hora = 0;
			if (!Null.is(dataObserver)) {
				dataObserver.addDia();
			}
		}
	}

	public void addMinuto() {
		minuto++;
		if (minuto > 59) {
			minuto = 0;
			addHora();
		}
	}

	public void addSegundo() {
		segundo++;
		if (segundo > 59) {
			segundo = 0;
			addMinuto();
		}
	}
	
	public void addMinutos(int qtd) {
		for (int i = 0; i < qtd; i++) {
			addMinuto();
		}
	}
	
	public Hora removeHora() {
		if (IntegerCompare.isZero(hora)) {
			hora = 23;
			if (!Null.is(dataObserver)) {
				dataObserver.removeDia();
			}
		} else {
			hora--;
		}
		return this;
	}

	public Hora removeMinuto() {
		if (IntegerCompare.isZero(minuto)) {
			minuto = 59;
			removeHora();
		} else {
			minuto--;
		}
		return this;
	}

	public Hora removeSegundo() {
		if (IntegerCompare.isZero(segundo)) {
			segundo = 59;
			removeMinuto();
		} else {
			segundo--;
		}
		return this;
	}

	public void setDataObserver(Data dataObserver) {
		this.dataObserver = dataObserver;
	}

	public int toInt() {
		return IntegerParse.toInt(format("[hh][nn][ss]"));
	}

	public boolean maior(Hora o) {
		return toInt() > o.toInt();
	}

	public boolean menor(Hora o) {
		return toInt() < o.toInt();
	}
	
	public static Hora getAleatorioEntre(Hora inicio, Hora fim) {
		int hora = Randomico.getInt(inicio.getHora(), fim.getHora());
		int minuto = Randomico.getInt(0, 59);
		int segundo = Randomico.getInt(0, 59);
		Hora o = new Hora(hora, minuto, segundo);
		
		if (o.menor(inicio) || o.maior(fim)) {
			return getAleatorioEntre(inicio, fim);
		} else {
			return o;
		}
	}
	
	public static Hora getAleatorioAteh(Hora fim) {
		return getAleatorioEntre(new Hora(0, 0, 0), fim);
	}
	
	public static Hora getAleatorio() {
		return getAleatorioAteh(new Hora(23, 59, 59));
	}

	public boolean eq(Hora o) {
		if (o == null) {
			return false;
		}
		return hora == o.hora && minuto == o.minuto && segundo == o.segundo;
	}
	
	public int toSegundosDia() {
		return (hora * 60 * 60) + (minuto * 60) + segundo; 
	}

	public boolean jaPassouSegundos(int segundos) {
		
		Hora o = now();
		
		if (maior(o)) {
			return false;
		}
		
		return o.toSegundosDia() - toSegundosDia() > segundos;
		
	}
	
	public static String formatParcial(String original, boolean horas, boolean minutos, boolean segundos) {

		String s = StringExtraiNumeros.exec(original);
		
		if (StringEmpty.is(s)) {
			return "";
		}
		
		int maxLen = 0;
		if (horas) {
			maxLen += 2;
		}
		if (minutos) {
			maxLen += 2;
		}
		if (segundos) {
			if (horas && !minutos) {
				throw new Error("Se tem horas e segundos, tem que ter minutos");
			}
			maxLen += 2;
		}
		if (maxLen == 0) {
			throw new Error("Pelo menos um item deve ser informado");
		}
		
		String result = "";
		
		s = StringLength.max(s, maxLen);
		
		if (horas) {

			if (Js.parseInt(s.substring(0, 1)) > 2) {
				s = StringLength.max("0" + s, maxLen);
			}
			
			if (StringLength.get(s) == 1) {
				return s;
			}
			
			result = s.substring(0, 2);
			
			if (Js.parseInt(result) > 23) {
				return "2";
			}
			
			s = s.substring(2);
			maxLen -= 2;
			
			if (StringEmpty.is(s)) {
				return result;
			} else {
				result += ":";
			}
			
		}
		
		for (int i = 0; i < 2; i++) {

			if (Js.parseInt(s.substring(0, 1)) > 5) {
				s = StringLength.max("0" + s, maxLen);
			}
			
			if (StringLength.get(s) == 1) {
				return result + s;
			}
			
			result += s.substring(0, 2);
			s = s.substring(2);
			maxLen -= 2;
			
			if (StringEmpty.is(s)) {
				return result;
			} else {
				result += ":";
			}
			
		}
		
		throw new Error("nao deveria chegar aqui");

	}

	public int compare(Hora o) {
		
		if (Null.is(o)) {
			return 1;
		}
		
		if (eq(o)) {
			return 0;
		}
		
		return IntegerCompare.compare(toInt(), o.toInt());
		
	}	
	
}
