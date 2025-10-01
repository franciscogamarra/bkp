package src.commom.utils.date;

import gm.languages.ts.javaToTs.annotacoes.PodeSerNull;
import gm.utils.lambda.F0;
import js.outros.Date;
import lombok.Getter;
import src.commom.utils.array.Itens;
import src.commom.utils.integer.IntegerCompare;
import src.commom.utils.integer.IntegerFormat;
import src.commom.utils.integer.IntegerParse;
import src.commom.utils.object.Null;
import src.commom.utils.object.ObjJs;
import src.commom.utils.string.StringCompare;
import src.commom.utils.string.StringContains;
import src.commom.utils.string.StringEmpty;
import src.commom.utils.string.StringExtraiNumeros;
import src.commom.utils.string.StringTrim;

@Getter
public class BaseData extends ObjJs {

	private static final Itens<String> NOMES_DIA = new Itens<>();
	private static final Itens<String> NOMES_MESES = new Itens<>();
	private static final Itens<String> NOMES_MESES_INGLES = new Itens<>();
	
	static {
		NOMES_DIA.add("Domingo").add("Segunda-feira").add("Terça-feira").add("Quarta-feira").add("Quinta-feira").add("Sexta-feira").add("Sábado");
		NOMES_MESES.add("Janeiro").add("Fevereiro").add("Março").add("Abril").add("Maio").add("Junho").add("Julho").add("Agosto").add("Setembro").add("Outubro").add("Novembro").add("Dezembro");
		NOMES_MESES_INGLES.add("January").add("February").add("March").add("April").add("May").add("June").add("July").add("August").add("September").add("October").add("November").add("December");
	}
	
	private int ano = 1900;
	private int mes = 1;
	private int dia = 1;
	private int hora = 0;
	private int minuto = 0;
	private int segundo = 0;
	private int milesimo = 0;

	public BaseData(Integer ano, Integer mes, Integer dia) {
		setAno(ano);
		setMes(mes);
		setDia(dia);
	}

	public static BaseData build(Integer aano, Integer ames, Integer adia, Integer ahora, Integer aminuto, Integer asegundo, Integer amilesimo) {
		BaseData o = new BaseData(aano, ames, adia);
		o.setHora(safe(ahora));
		o.setMinuto(safe(aminuto));
		o.setSegundo(safe(asegundo));
		o.setMilesimo(safe(amilesimo));
		return o;
	}

	private static int safe(Integer value) {
		return Null.is(value) ? 0 : value;
	}

	public void setDiaSemUltrapassarMes(int diaP) {
		int m = getMes();
		setDia(diaP);
		while (getMes() > m) {
			removeDia();
		}
	}

	private void verificaSeODiaEhValido(int value) {
		if (value > 28) {
			if ((value > getUltimoDiaDoMes()) || (value < 1)) {
				throw erroDataInvalida();
			}
		}
	}

	public BaseData setDia(int value) {
		verificaSeODiaEhValido(value);
		dia = value;
		return this;
	}

	private RuntimeException erroDataInvalida() {
		throw new Error("Data Inválida");
	}

	public BaseData setMes(int value) {
		if (value < 1 || value > 12) {
			throw erroDataInvalida();
		}
		mes = value;
		verificaSeODiaEhValido(dia);
		return this;
	}

	public BaseData setAno(int value) {
		ano = value;
		verificaSeODiaEhValido(dia);
		return this;
	}

	public BaseData setHora(int value) {
		if (value < 0 || value > 23) {
			throw erroDataInvalida();
		}
		hora = value;
		return this;
	}

	public BaseData setMinuto(int value) {
		if (value < 0 || value > 59) {
			throw erroDataInvalida();
		}
		minuto = value;
		return this;
	}

	public BaseData setSegundo(int value) {
		if (value < 0 || value > 59) {
			throw erroDataInvalida();
		}
		segundo = value;
		return this;
	}

	public BaseData setMilesimo(int value) {
		if (value < 0 || value > 999) {
			throw erroDataInvalida();
		}
		milesimo = value;
		return this;
	}

	public int getSeculo() {
		return ano / 100;
	}

	public boolean ehBissexto() {
		return ehBissextoStatic(ano);
	}

	public static boolean ehBissextoStatic(int anoP) {
		return anoP % 4 == 0;
	}

	public int getDiaDoAno() {
		int value = dia;
		if (mes > 1) {
			value += 31;
			if (mes > 2) {
				if (ehBissexto()) {
					value += 29;
				} else {
					value += 28;
				}
				if (mes > 3) {
					value += 31;
					if (mes > 4) {
						value += 30;
						if (mes > 5) {
							value += 31;
							if (mes > 6) {
								value += 30;
								if (mes > 7) {
									value += 31;
									if (mes > 8) {
										value += 31;
										if (mes > 9) {
											value += 30;
											if (mes > 10) {
												value += 31;
												if (mes > 11) {
													value += 30;
												}
											}
										}
									}
								}
							}
						}
					}
				}
			}
		}
		return value;

	}

	public int getDiaDaSemanaDoPrimeiroDiaDoAno() {

		BaseData data = new BaseData(2020, 1, 1);

		int x = 4;

		if (data.ano > ano) {

			while (data.ano > ano) {
				data.removeAno();
				x--;
				if (data.ehBissexto()) {
					x--;
				}
				if (x < 0) {
					x = 7 + x;
				}
			}

		} else {

			while (data.ano < ano) {
				if (data.ehBissexto()) {
					x++;
				}
				data.addAno();
				x++;
				if (x > 7) {
					x = 7 - x;
				}
			}

		}

		return x;

	}

	/**
	 * https://pt.wikipedia.org/wiki/Algoritmo_Doomsday
	 */
	public int getDiaDaSemana() {

		int diaDaSemanaDoPrimeiroDiaDoAno = getDiaDaSemanaDoPrimeiroDiaDoAno();

		int x = getDiaDoAno() % 7 + diaDaSemanaDoPrimeiroDiaDoAno - 1;

		if (x > 7) {
			x -= 7;
		}

		return x;

	}

	public boolean sabado() {
		return getDiaDaSemana() == 7;
	}

	public boolean domingo() {
		return getDiaDaSemana() == 1;
	}

	public int getUltimoDiaDoMes() {
		return UAnoMes.getUltimoDiaDoMes(ano, mes);
	}

	public BaseData addDia() {
		if (IntegerCompare.eq(dia, getUltimoDiaDoMes())) {
			dia = 1;
			addMes();
		} else {
			dia++;
		}
		return this;
	}

	public BaseData addMes() {
		mes++;
		if (mes > 12) {
			mes = 1;
			if (dia > getUltimoDiaDoMes()) {
				throw erroDataInvalida();
			}
			addAno();
		}
		return this;
	}

	public void addAno() {
		ano++;
		if (IntegerCompare.eq(mes, 2) && IntegerCompare.eq(dia, 29)) {
			throw erroDataInvalida();
		}
	}

	public void addHora() {
		hora++;
		if (hora > 23) {
			hora = 0;
			addDia();
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

	public void addMilesimo() {
		milesimo++;
		if (milesimo > 999) {
			milesimo = 0;
			addSegundo();
		}
	}

	/*-----------------------------*/
	public BaseData removeDia() {
		dia--;
		if (dia < 1) {
			dia = 1;
			removeMes();
			dia = getUltimoDiaDoMes();
		}
		return this;
	}

	public BaseData removeMes() {
		mes--;
		if (mes < 1) {
			mes = 12;
			removeAno();
		} else if (dia > getUltimoDiaDoMes()) {
			throw new Error("Data inválida!");
		}
		return this;
	}

	public BaseData removeAno() {
		ano--;
		if (IntegerCompare.eq(mes, 2) && IntegerCompare.eq(dia, 29)) {
			throw new Error("Data inválida!");
		}
		return this;
	}

	public BaseData removeHora() {
		if (IntegerCompare.isZero(hora)) {
			hora = 23;
			removeDia();
		} else {
			hora--;
		}
		return this;
	}

	public BaseData removeMinuto() {
		if (IntegerCompare.isZero(minuto)) {
			minuto = 59;
			removeHora();
		} else {
			minuto--;
		}
		return this;
	}

	public BaseData removeSegundo() {
		if (IntegerCompare.isZero(segundo)) {
			segundo = 59;
			removeMinuto();
		} else {
			segundo--;
		}
		return this;
	}

	public BaseData removeMilesimo() {
		if (IntegerCompare.isZero(milesimo)) {
			milesimo = 999;
			removeSegundo();
		} else {
			milesimo--;
		}
		return this;
	}

	public BaseData addDias(int count) {
		if (count < 0) {
			return removeDias(-count);
		}
		for (int i = 0; i < count; i++) {
			addDia();
		}
		return this;
	}

	public BaseData removeDias(int count) {
		if (count < 0) {
			return addDias(-count);
		}
		for (int i = 0; i < count; i++) {
			removeDia();
		}
		return this;
	}

	public BaseData addMeses(int count) {
		if (count < 0) {
			return removeMeses(-count);
		}
		for (int i = 0; i < count; i++) {
			addMes();
		}
		return this;
	}

	public BaseData removeMeses(int count) {
		if (count < 0) {
			return addMeses(-count);
		}
		for (int i = 0; i < count; i++) {
			removeMes();
		}
		return this;
	}

	public BaseData addAnos(int count) {
		if (count < 0) {
			return removeAnos(-count);
		}
		for (int i = 0; i < count; i++) {
			addMes();
		}
		return this;
	}

	public BaseData removeAnos(int count) {
		if (count < 0) {
			return addAnos(-count);
		}
		for (int i = 0; i < count; i++) {
			removeAno();
		}
		return this;
	}

	public BaseData addHoras(int count) {
		if (count < 0) {
			return removeHoras(-count);
		}
		for (int i = 0; i < count; i++) {
			addHora();
		}
		return this;
	}

	public BaseData removeHoras(int count) {
		if (count < 0) {
			return addHoras(-count);
		}
		for (int i = 0; i < count; i++) {
			removeHora();
		}
		return this;
	}

	public BaseData addMinutos(int count) {
		if (count < 0) {
			return removeMinutos(-count);
		}
		for (int i = 0; i < count; i++) {
			addMinuto();
		}
		return this;
	}

	public BaseData removeMinutos(int count) {
		if (count < 0) {
			return addMinutos(-count);
		}
		for (int i = 0; i < count; i++) {
			removeMinuto();
		}
		return this;
	}

	public BaseData addSegundos(int count) {
		if (count < 0) {
			return removeSegundos(-count);
		}
		for (int i = 0; i < count; i++) {
			addSegundo();
		}
		return this;
	}

	public BaseData removeSegundos(int count) {
		if (count < 0) {
			return addSegundos(-count);
		}
		for (int i = 0; i < count; i++) {
			removeSegundo();
		}
		return this;
	}

	public BaseData addMilesimos(int count) {
		if (count < 0) {
			return removeMilesimos(-count);
		}
		for (int i = 0; i < count; i++) {
			addMilesimo();
		}
		return this;
	}

	public BaseData removeMilesimos(int count) {
		if (count < 0) {
			return addMilesimos(-count);
		}
		for (int i = 0; i < count; i++) {
			removeMilesimo();
		}
		return this;
	}

	public int bimestre() {
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

	public int trimestre() {
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

	public int quadrimestre() {
		if (mes < 5) {
			return 1;
		}
		if (mes < 9) {
			return 2;
		}
		return 3;
	}

	public int semestre() {
		if (mes < 7) {
			return 1;
		}
		return 2;
	}

	public String formatSql() {
		return format("[yyyy]-[mm]-[dd]");
	}

	public String format(String s) {
		
		s = s.replace("[ddddd]", nomeDiaSemana());// quinta-feira
		s = s.replace("[dddd]", nomeDiaSemana().split("-")[0]);// quinta
		s = s.replace("[ddd]", nomeDiaSemana().substring(0, 3));
		s = s.replace("[dd]", IntegerFormat.xx(getDia()));
		s = s.replace("[d]", "" + getDia());

		s = s.replace("[mmmm]", nomeMes());
		s = s.replace("[mmm]", nomeMes().substring(0, 3));
		s = s.replace("[mm]", IntegerFormat.xx(getMes()));
		s = s.replace("[m]", "" + getMes());

		s = s.replace("[yyyy]", "" + getAno());
		s = s.replace("[yy]", ("" + getAno()).substring(2));
		s = s.replace("[y]", "" + getAno());

		s = s.replace("[hh]", IntegerFormat.xx(getHora()));
		s = s.replace("[h]", "" + getHora());

		s = s.replace("[nn]", IntegerFormat.xx(getMinuto()));
		s = s.replace("[n]", "" + getMinuto());

		s = s.replace("[ss]", IntegerFormat.xx(getSegundo()));
		s = s.replace("[s]", "" + getSegundo());

		s = s.replace("[lll]", IntegerFormat.xxx(getMilesimo()));
		s = s.replace("[ll]", IntegerFormat.xx(getMilesimo()));
		s = s.replace("[l]", "" + getMilesimo());

		s = s.replace("[b]", "" + bimestre());
		s = s.replace("[t]", "" + trimestre());
		s = s.replace("[q]", "" + quadrimestre());
		return s.replace("[se]", "" + semestre());
	}
	
	@PodeSerNull
	public static Date unformatDate(String format, String value) {
		@PodeSerNull BaseData o = unformat(format, value);
		return o == null ? null : o.toDate();
	}

	@PodeSerNull
	public static BaseData unformat(String format, String value) {

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

			BaseData data = BaseData.build(2016, 1, 1, 0, 0, 0, 0);
			data.zeraTime();

			value = value.toLowerCase();

			while (!StringEmpty.is(format)) {

				if (format.toLowerCase().startsWith("[yyyy]")) {
					format = format.substring(6);
					int anoP = IntegerParse.toIntSafe(value.substring(0, 4));
					if (anoP < 1900 || anoP > 2030) {
						throw new Error("ano < 1900 || ano > 2030: " + anoP);
					}
					value = value.substring(4);
					data.setAno(anoP);
					continue;
				}
				if (format.toLowerCase().startsWith("[yy]")) {
					format = format.substring(4);
					int anoP = IntegerParse.toIntSafe(value.substring(0, 2)) + 1900;
					value = value.substring(2);
					data.setAno(anoP);
					continue;
				}
				if (format.toLowerCase().startsWith("[m]")) {
					format = format.substring(3);
					int mesP = IntegerParse.toIntSafe(value.substring(0, 1));
					value = value.substring(1);
					data.setMes(mesP);
					continue;
				}
				if (format.toLowerCase().startsWith("[mm]")) {
					format = format.substring(4);
					int mesP = IntegerParse.toIntSafe(value.substring(0, 2));
					if (mesP < 1 || mesP > 12) {
						throw new Error("mes < 1 || mes > 12: " + mesP);
					}
					value = value.substring(2);
					data.setMes(mesP);
					continue;
				}
				if (format.toLowerCase().startsWith("[mmm]")) {

					format = format.substring(5);

					boolean conseguiu = false;

					for (int i = 1; i <= 12; i++) {
						String nomeMes = NOMES_MESES.get(i - 1).toLowerCase();
						nomeMes = nomeMes.substring(0, 3);
						if (value.startsWith(nomeMes)) {
							value = value.substring(nomeMes.length());
							data.setMes(i);
							conseguiu = true;
							break;
						}
						nomeMes = NOMES_MESES_INGLES.get(i - 1).toLowerCase();
						nomeMes = nomeMes.substring(0, 3);
						if (value.startsWith(nomeMes)) {
							value = value.substring(nomeMes.length());
							data.setMes(i);
							conseguiu = true;
							break;
						}
					}
					if (!conseguiu) {
						throw new Error("!conseguiu");
					}
					continue;
				}
				if (format.toLowerCase().startsWith("[mmmm]")) {
					format = format.substring(6);

					boolean conseguiu = false;

					for (int i = 1; i <= 12; i++) {
						String nomeMes = NOMES_MESES.get(i - 1).toLowerCase();
						if (value.startsWith(nomeMes)) {
							value = value.substring(nomeMes.length());
							data.setMes(i);
							conseguiu = true;
							break;
						}
						nomeMes = NOMES_MESES_INGLES.get(i - 1).toLowerCase();
						if (value.startsWith(nomeMes)) {
							value = value.substring(nomeMes.length());
							data.setMes(i);
							conseguiu = true;
							break;
						}
					}
					if (!conseguiu) {
						throw new Error("!conseguiu");
					}
					continue;
				}
				if (format.toLowerCase().startsWith("[dd]")) {
					format = format.substring(4);
					int diaP = IntegerParse.toIntSafe(value.substring(0, 2));
					if (diaP < 0 || diaP > 31) {
						throw new Error("dia < 0 || dia > 31: " + diaP);
					}
					value = value.substring(2);
					data.setDia(diaP);
					continue;
				}
				if (format.toLowerCase().startsWith("[d]")) {
					format = format.substring(3);
					int diaP = IntegerParse.toIntSafe(value.substring(0, 1));
					value = value.substring(1);
					data.setDia(diaP);
					continue;
				}
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
			throw new Error(message + " >> " + e.getMessage());
		}

	}

	public String nomeMes() {
		return NOMES_MESES.get(mes - 1);
	}

	public String nomeDiaSemana() {
		return NOMES_DIA.get(getDiaDaSemana() - 1);
	}

	public boolean maiorOuIgual(BaseData d) {
		return maior(d) || eq(d);
	}

	public boolean maior(BaseData data) {
		return IntegerCompare.eq(compare(data), 1);
	}

	public boolean maiorQueHoje() {
		return maior(hoje());
	}

	public boolean menorQueHoje() {
		return menor(hoje());
	}

	public boolean menorOuIgual(BaseData d) {
		return menor(d) || eq(d);
	}

	public boolean menor(BaseData data) {
		return IntegerCompare.eq(compare(data), -1);
	}

	public static BaseData getMaior(BaseData a, BaseData b) {
		if (a == null) {
			return b;
		}
		if ((b == null) || a.maior(b)) {
			return a;
		}
		return b;
	}

	public static BaseData getMenor(BaseData a, BaseData b) {
		if (a == null) {
			return b;
		}
		if ((b == null) || a.menor(b)) {
			return a;
		}
		return b;
	}

	public int dias_uteis_no_mes() {
		BaseData data = copy();
		data.setDia(1);
		return data.dias_uteis_ate_o_fim_do_mes();
	}

	public int dias_uteis_ate_o_fim_do_mes() {
		BaseData data = copy();
		data.setDia(getUltimoDiaDoMes());
		return dias_uteis_ate(data);
	}

	public int dias_uteis_ate(BaseData fim) {
		BaseData data = copy();
		int i = 0;
		while (data.menor(fim)) {
			if (data.ehDiaUtil()) {
				i++;
			}
			data.addDia();
		}
		return i;
	}

	public BaseData menosDiasUteis(int dias) {
		BaseData data = copy();
		while (dias > 0) {
			if (data.ehDiaUtil()) {
				dias--;
			}
			data.removeDia();
		}
		return data;
	}

	public BaseData menosDias(int dias) {
		BaseData copy = copy();
		copy.removeDias(dias);
		return copy;
	}

	public int dias_ate_hoje() {
		return ate(now());
	}

	public int ate(BaseData d) {
		return d.menos(this);
	}

	public int menos(BaseData d) {
		return diferenca(d).emDias();
	}

	public static BaseData ontem() {
		BaseData data = hoje();
		data.removeDia();
		return data;
	}

	public static BaseData hoje() {
		BaseData data = now();
		data.zeraTime();
		return data;
	}

	public static BaseData amanha() {
		BaseData data = hoje();
		data.addDia();
		return data;
	}

	public boolean eq(BaseData x) {
		if (Null.is(x) || IntegerCompare.ne(ano, x.ano) || IntegerCompare.ne(mes, x.mes) || IntegerCompare.ne(dia, x.dia)) {
			return false;
		}
		if (IntegerCompare.ne(hora, x.hora)) {
			return false;
		}
		if (IntegerCompare.ne(minuto, x.minuto)) {
			return false;
		}
		if (IntegerCompare.ne(segundo, x.segundo)) {
			return false;
		}
		if (IntegerCompare.ne(milesimo, x.milesimo)) {
			return false;
		}
		return true;
	}

	public static String mensagemBomMomento() {
		BaseData now = now();
		if (now.getHora() < 12) {
			return "bom dia!";
		}
		if (now.getHora() < 18) {
			return "boa tarde!";
		}
		return "boa noite!";
	}

	public BaseData zeraTime() {
		setHora(0);
		setMinuto(0);
		setSegundo(0);
		setMilesimo(0);
		return this;
	}

	public BaseData copy() {
		return BaseData.build(ano, mes, dia, hora, minuto, segundo, milesimo);
	}

	public BaseData setUltimoDiaMes() {
		setDia(getUltimoDiaDoMes());
		return this;
	}

	public boolean isUltimoDiaDoMes() {
		return IntegerCompare.eq(dia, getUltimoDiaDoMes());
	}

	public int getIdade() {
		BaseData hoje = now();
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

	public Tempo diferenca(BaseData data) {
		return Tempo.buildDiferenca(this, data);
	}

	public boolean feriadoRecessoNaoFuncionamento() {
		return FeriadoRecessoNaoFuncionamento.test(this);
	}

	public boolean ehDiaUtil() {
		return ehDiaUtil2(false);
	}
	
	public boolean ehDiaUtil2(boolean funcionaAosSabados) {
		if (funcionaAosSabados) {
			return (!domingo() && !feriadoRecessoNaoFuncionamento());
		}
		return (!sabado() && !domingo() && !feriadoRecessoNaoFuncionamento());
	}

	public boolean isHoje() {
		BaseData hoje = hoje();
		if ((hoje.getAno() != getAno()) || (hoje.getMes() != getMes()) || (hoje.getDia() != getDia())) {
			return false;
		}
		return true;
	}

	public boolean equals(int anoP, int mesP, int diaP) {
		if ((getAno() != anoP) || (getMes() != mesP) || (getDia() != diaP)) {
			return false;
		}
		return true;
	}

	public static BaseData getProximoDiaUtil(BaseData data) {
		data = data.copy();
		while (!data.ehDiaUtil()) {
			data.addDia();
		}
		return data;
	}

	public BaseData proximoDiaUtil() {
		return copy().addDia().proximoDiaUtilContandoComEsta();
	}

	public BaseData proximoDiaUtilContandoComEsta() {
		return getProximoDiaUtil(this);
	}

	public boolean jaPassou() {
		return hoje().maior(this);
	}

	public boolean mesmoDiaQue(BaseData data) {
		return getDia() == data.getDia() && getMes() == data.getMes() && getAno() == data.getAno();
	}

	public void addMinutosUteis(int x) {

		if (getHora() > 18) {
			zeraTime();
			setHora(8);
			addDia();
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
			addDia();
			return;
		}

		if (getHora() == 13) {
			setHora(14);
			return;
		}

		if (getHora() == 12 && getMinuto() > 0) {
			setHora(14);
		}

	}

	public int diferencaEmDiasUteis(BaseData data) {

		BaseData maior;
		BaseData menor;

		if (maior(data)) {
			menor = data;
			maior = copy();
		} else {
			menor = copy();
			maior = data;
		}

		int x = 0;

		while (!menor.mesmoDiaQue(maior)) {
			if (menor.ehDiaUtil()) {
				x++;
			}
			menor.addDia();
		}

		return x;

	}

	public void setUltimoMomentoDoDia() {
		setHora(23);
		setMinuto(59);
		setSegundo(59);
	}

	public boolean jaPassouSegundos(int x) {
		return diferenca(now()).emSegundos() > x;
	}

	public boolean jaPassouMinutos(int x) {
		return diferenca(now()).emMinutos() > x;
	}

	public boolean jaPassouHoras(int x) {
		return diferenca(now()).emHoras() > x;
	}

	public Date toDate() {
		return new Date(ano, mes-1, dia, hora, minuto, segundo, milesimo);
	}

	@PodeSerNull
	public static BaseData toData(Date date) {
		if (Null.is(date)) {
			return null;
		}
		return toDataNotNull(date);
	}
	
	public static BaseData toDataNotNull(Date date) {
		return BaseData.build(date.getFullYear(), date.getMonth() + 1, date.getDate(), date.getHours(), date.getMinutes(), date.getSeconds(), 0);
	}

	public static F0<BaseData> comoBuscarNow;

	public static BaseData now() {
		if (Null.is(comoBuscarNow)) {
			return nowDispositivo();
		}
		return comoBuscarNow.call();
	}

	public static BaseData nowDispositivo() {
		return toDataNotNull(new Date());
	}

	public int getAnoMes() {
		return getAno() * 100 + getMes();
	}

	public int compare(BaseData data) {
		if (data == null) {
			return 1;
		}
		return StringCompare.compare(format("[yyyy][mm][dd][hh][nn][ss][lll]"), data.format("[yyyy][mm][dd][hh][nn][ss][lll]"));
	}

	public long getTime() {
		return toDate().getTime();
	}

	@PodeSerNull
	public static BaseData unJson(String s) {
		if (StringEmpty.is(s)) {
			return null;
		}
		BaseData o = jsonParse(s, BaseData.class);
		return BaseData.build(o.ano, o.mes, o.dia, o.hora, o.minuto, o.segundo, o.milesimo);
	}

	@Override
	protected String toJsonImpl() {
		return format("{\"ano\": [y], \"mes\": [m], \"dia\": [d], \"hora\": [h], \"minuto\": [n], \"segundo\": [s], \"milesimo\": [l]}");
	}
	
	@PodeSerNull
	public static BaseData desvenda(@PodeSerNull String s) {
		
		if (isNull_(s)) {
			return null;
		}
		
		s = StringTrim.plus(s);
		
		if (StringEmpty.is(s)) {
			return null;
		}
		
		s = StringExtraiNumeros.exec(s);
		
		if (s.startsWith("19") || s.startsWith("20")) {
			return unformat("[yyyy][mm][dd]", s);
		}
		
		return unformat("[dd][mm][yyyy]", s);
		
	}

}
