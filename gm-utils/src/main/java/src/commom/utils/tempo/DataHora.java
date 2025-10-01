package src.commom.utils.tempo;

import js.support.console;
import lombok.Getter;
import src.commom.utils.array.Itens;
import src.commom.utils.object.Null;
import src.commom.utils.string.StringEmpty;
import src.commom.utils.string.StringExtraiNumeros;
import src.commom.utils.string.StringLength;
import src.commom.utils.string.StringSplit;

@Getter
public class DataHora {
	
	private Data data;
	private Hora hora;
	
	public void setData(Data data) {
		this.data = data;
		if (!Null.is(hora)) {
			hora.setDataObserver(data);
		}
	}
	
	public void setHora(Hora hora) {
		this.hora = hora;
		if (!Null.is(hora)) {
			hora.setDataObserver(data);
		}
	}
	
	public static DataHora now() {
		DataHora o = new DataHora();
		o.setData(Data.hoje());
		o.setHora(Hora.now());
		return o;
	}
	
	public static DataHora hoje() {
		DataHora o = new DataHora();
		o.setData(Data.hoje());
		o.setHora(Hora.ZERO);
		return o;
	}

	public static DataHora hojeFim() {
		DataHora o = new DataHora();
		o.setData(Data.hoje());
		o.setHora(Hora.ULTIMA);
		return o;
	}
	
	public static DataHora build2(int ano, int mes, int dia, int hora, int minuto, int segundo) {
		DataHora o = new DataHora();
		o.setData(new Data(ano, mes, dia));
		o.setHora(new Hora(hora, minuto, segundo));
		return o;
	}
	
	public static DataHora build(int ano, int mes, int dia, int hora, int minuto) {
		return build2(ano, mes, dia, hora, minuto, 0);
	}
	
	public String format(String s) {
		if (StringEmpty.is(s)) {
			return "";
		}
		s = data.format(s);
		s = hora.format(s);
		return s;
	}
	
	@Override
	public String toString() {
		return data.toString() + " " + hora.toString();
	}

	public static DataHora unformat(String format, String s) {
		
		if (StringEmpty.is(s)) {
			return null;
		}
		
		DataHora o = new DataHora();
		o.setData(Data.unformatProtected(format, s, true));
		format = format.replace("[yyyy]", "yyyy");
		format = format.replace("[yy]", "yy");
		format = format.replace("[mm]", "mm");
		format = format.replace("[dd]", "dd");
		o.setHora(Hora.unformat(format, s));
		return o;
		
	}
	
	private static final int LENGTH_FORMAT_1 = StringLength.get("yyyymmddhhnnss");
	private static final int LENGTH_FORMAT_2 = StringLength.get("yyyy-mm-ddThh:nn:ss");
	
	public static DataHora discover(String s) {
		
		String ss = StringExtraiNumeros.exec(s);
		
		if (ss.length() == LENGTH_FORMAT_1) {
			
			if (s.length() == LENGTH_FORMAT_2) {
				
				Itens<String> cs = StringSplit.exec(s, "");
				
				if (s.startsWith("19") || s.startsWith("20")) {
					if (cs.get(4) == "-" && cs.get(7) == "-" && cs.get(13) == ":" && cs.get(16) == ":") {
						
						try {
							return unformat("[yyyy][mm][dd][hh][nn][ss]", ss);
						} catch (Exception e) {
							// TODO: handle exception
						}
						
					}
				}
			}
			
		}
		
		return null;
		
	}
	
	public static DataHora unformatJs(String s) {
		return DataHora.unformat("[yyyy]-[mm]-[dd]T[hh]:[nn]:[ss]", s);
	}

	public String formatJs() {
		return format("[yyyy]-[mm]-[dd]T[hh]:[nn]:[ss]");
	}

	public String formatFileName() {
		return format("[yyyy]-[mm]-[dd]-[hh]-[nn]-[ss]");
	}

	public static String formatJsSafe(DataHora o) {
		return Null.is(o) ? null : o.formatJs();
	}

	public static DataHora getAleatorioEntre(DataHora inicio, DataHora fim) {
		
		if (inicio.maior(fim)) {
			DataHora dh = inicio;
			inicio = fim;
			fim = dh;
		}
		
		DataHora o = new DataHora();
		o.setData(Data.getAleatorioEntre(inicio.getData(), fim.getData()));
		
		if (o.getData().eq(inicio.getData())) {
			o.setHora(Hora.getAleatorioAteh(inicio.getHora()));
		} else if (o.getData().eq(fim.getData())) {
			o.setHora(Hora.getAleatorioAteh(fim.getHora()));
		} else {
			o.setHora(Hora.getAleatorio());
		}
		
		return o;
		
	}
	
	public boolean maior(DataHora o) {
		
		if (getData().maior(o.getData())) {
			return true;
		} else if (getData().menor(o.getData())) {
			return false;
		} else if (getHora().maior(o.getHora())) {
			return true;
		} else {
			return false;
		}
		
	}

	public boolean menor(DataHora o) {
		
		if (getData().menor(o.getData())) {
			return true;
		} else if (getData().maior(o.getData())) {
			return false;
		} else if (getHora().menor(o.getHora())) {
			return true;
		} else {
			return false;
		}
		
	}
	
	public static DataHora getAleatorio() {
		DataHora o = new DataHora();
		o.setData(Data.getAleatorio());
		o.setHora(Hora.getAleatorio());
		return o;
	}
	
	@Override
	public boolean equals(Object o) {

		if (Null.is(o)) {
			return false;
		}
		
		if (o == this) {
			return true;
		}

		if (o instanceof DataHora) {
			DataHora dh = (DataHora) o;
			return eq(dh);
		}
		
		return false;
		
	}

	public boolean eq(DataHora o) {
		
		if (Null.is(o)) {
			return false;
		}
		
		return data.eq(o.getData()) && hora.eq(o.getHora());
		
	}

	public boolean jaPassouSegundos(int segundos) {
		
		DataHora o = now();
		
		if (maior(o)) {
			return false;
		}
		
		if (data.menor(o.data)) {
			return true;
		}
		
		return hora.jaPassouSegundos(segundos);
		
	}
	
	public static String formatParcial(String original, boolean segundos) {

		String s = StringLength.max(StringExtraiNumeros.exec(original), 12);
		
		if (StringEmpty.is(s)) {
			return "";
		}
		
		if (StringLength.get(s) < 9) {
			return Data.formatParcial(s);
		}
		
		return Data.formatParcial(s.substring(0,8)) + " " + Hora.formatParcial(s.substring(8), true, true, segundos);

	}
	
	public void print() {
		console.log(toString());
	}
	
	public int compare(DataHora o) {
		
		if (Null.is(o)) {
			return 1;
		}
		
		if (eq(o)) {
			return 0;
		}
		
		int i = data.compare(o.data);
		
		if (i == 0) {
			return hora.compare(o.hora);
		} else {
			return i;
		}
		
	}	
	
}