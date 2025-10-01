package src.commom.utils.date;

//@Getter @Setter
public class Tempo {

	private int anos;
	private int meses;
	private int dias;
	private int horas;
	private int minutos;
	private int segundos;
	private boolean negativo;

	public Tempo(int dias){

		while (dias >= 365) {
			dias -= 365;
			anos++;
		}

		while (dias >= 30) {
			dias -= 30;
			meses++;
		}

		this.dias = dias;

	}

	public static Tempo buildDiferenca(BaseData a, BaseData b) {

		Tempo o = new Tempo(0);

		if (a.menor(b)) {
			BaseData c = a;
			a = b;
			b = c;
			o.negativo = true;
		}

		o.anos = a.getAno() - b.getAno();

		o.meses = a.getMes() - b.getMes();
		if (o.meses < 0) {
			o.decAnos();
		}

		o.dias = a.getDia() - b.getDia();
		if (o.dias < 0) {
			o.decMes();
		}

		o.horas = a.getHora() - b.getHora();
		if (o.horas < 0) {
			o.decDias();
		}

		o.minutos = a.getMinuto() - b.getMinuto();
		if (o.minutos < 0) {
			o.decHoras();
		}

		o.segundos = a.getSegundo() - b.getSegundo();
		if (o.segundos < 0) {
			o.decMinutos();
		}

		return o;

	}

	private void decAnos() {
		anos--;
		meses += 12;
	}

	private void decMes() {
		meses--;
		dias += 30;
		if (meses == -1) {
			decAnos();
		}
	}
	private void decDias() {
		dias--;
		horas += 24;
		if (dias == -1) {
			decMes();
		}
	}
	private void decHoras() {
		horas--;
		minutos += 60;
		if (horas == -1) {
			decDias();
		}
	}
	private void decMinutos() {
		minutos--;
		segundos += 60;
		if (minutos == -1) {
			decHoras();
		}
	}

	public int emHoras() {
		return horas + dias*24*60;
	}
	public int emMinutos() {
		return minutos + emHoras() * 60;
	}
	public int emSegundos() {
		return segundos + emMinutos() * 60;
	}
	public int emDias(){
		return anos * 365 + meses * 30 + dias;
	}
	public int emMeses(){
		return meses;
	}
	public int emAnos() {
		return anos;
	}
	public int emDiasReais(){
		int x = emDias();
		if (negativo) {
			return -x;
		}
		return x;
	}
	@Override
	public String toString() {

		String s = " " + anos + " anos e " + meses + " meses e " + dias + " dias";

		s = s.replace(" 0 anos e ", " ");
		s = s.replace(" 1 anos e ", " 1 ano e ");
		s = s.replace(" 0 meses e ", " ");
		s = s.replace(" 1 meses e ", " 1 m\u00eas e ");
		s = s.replace(" e 0 dias", " ");
		s = s.replace(" 1 dias", " 1 dia");
		return s.trim();
	}

}
