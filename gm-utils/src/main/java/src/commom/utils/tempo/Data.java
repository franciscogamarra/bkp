package src.commom.utils.tempo;

import gm.languages.ts.javaToTs.JS;
import gm.languages.ts.javaToTs.annotacoes.PodeSerNull;
import gm.utils.anotacoes.IgnorarDaquiPraBaixo;
import gm.utils.lambda.F1;
import jakarta.validation.constraints.NotNull;
import js.Js;
import js.outros.Date;
import js.support.console;
import lombok.Getter;
import src.commom.utils.array.Itens;
import src.commom.utils.comum.Randomico;
import src.commom.utils.integer.IntegerCompare;
import src.commom.utils.integer.IntegerFormat;
import src.commom.utils.integer.IntegerParse;
import src.commom.utils.object.Equals;
import src.commom.utils.object.Null;
import src.commom.utils.string.StringAfterLast;
import src.commom.utils.string.StringCompare;
import src.commom.utils.string.StringContains;
import src.commom.utils.string.StringEmpty;
import src.commom.utils.string.StringExtraiNumeros;
import src.commom.utils.string.StringLength;
import src.commom.utils.string.StringReplace;
import src.commom.utils.string.StringRight;
import src.commom.utils.string.StringSplit;
import src.commom.utils.string.StringTrim;

@Getter
public class Data extends JS {
	
	private static final Itens<@NotNull String> NOMES_DIA = new Itens<>();
	private static final Itens<@NotNull String> NOMES_DIA_INGLES = new Itens<>();
	private static final Itens<@NotNull String> NOMES_MESES = new Itens<>();
	private static final Itens<@NotNull String> NOMES_MESES_INGLES = new Itens<>();
	private static final Itens<@NotNull Integer> DIAS_NO_MES = new Itens<>();
	private static final Itens<@NotNull Integer> DIAS_ATE_O_MES = new Itens<>();
	private static final Itens<@NotNull Integer> MESES31 = Itens.build(1, 3, 5, 7, 8, 10, 12);
	
	private static final Itens<@NotNull String> FERIADOS_NACIONAIS_FIXOS = new Itens<>();
	private static final Itens<@NotNull String> FERIADOS_NACIONAIS_MOVEIS = new Itens<>();
	private static final Itens<F1<Data, Boolean>> FUNCOES_DE_TESTE_DE_FERIADO = new Itens<>();
	
	static {
		NOMES_DIA.add("Domingo").add("Segunda-feira").add("Terça-feira").add("Quarta-feira").add("Quinta-feira").add("Sexta-feira").add("Sábado");
		NOMES_DIA_INGLES.add("Sunday").add("Monday").add("Tuesday").add("Wednesday").add("Thursday").add("Friday").add("Saturday");
		NOMES_MESES.add("Janeiro").add("Fevereiro").add("Março").add("Abril").add("Maio").add("Junho").add("Julho").add("Agosto").add("Setembro").add("Outubro").add("Novembro").add("Dezembro");
		NOMES_MESES_INGLES.add("January").add("February").add("March").add("April").add("May").add("June").add("July").add("August").add("September").add("October").add("November").add("December");
		DIAS_NO_MES.add(31).add(28).add(31).add(30).add(31).add(30).add(31).add(31).add(30).add(31).add(30).add(31);
		DIAS_ATE_O_MES.add(0);
		DIAS_NO_MES.forEach(i -> DIAS_ATE_O_MES.add(DIAS_ATE_O_MES.getLast() + i));
		FERIADOS_NACIONAIS_FIXOS.add("01/01").add("21/04").add("01/05").add("07/09").add("12/10").add("02/11").add("15/11").add("25/12");
		FERIADOS_NACIONAIS_MOVEIS.add("10/04/2020").add("11/06/2020").add("15/02/2021").add("16/02/2021").add("02/04/2021").add("03/06/2021").add("15/04/2022");
	}

	@NotNull private int dia = 1;
	@NotNull private int mes = 1;
	@NotNull private int ano = 2000;
	
	public Data(int ano, int mes, int dia) {
		this.ano = ano;
		this.mes = mes;
		this.dia = dia;
		ajustaDia();
	}
	
	public boolean isAno(int value) {
		return IntegerCompare.eq(value, ano);
	}

	public boolean isMes(int value) {
		return IntegerCompare.eq(value, mes);
	}

	public boolean isDia(int value) {
		return IntegerCompare.eq(value, dia);
	}

	public boolean isJaneiro() {
		return isMes(1);
	}

	public boolean isFevereiro() {
		return isMes(2);
	}

	public boolean isMarco() {
		return isMes(3);
	}
	
	public boolean isAbril() {
		return isMes(4);
	}

	public boolean isMaio() {
		return isMes(5);
	}
	
	public boolean isJunho() {
		return isMes(6);
	}

	public Data addDia() {
		if (IntegerCompare.eq(dia, getUltimoDiaDoMes())) {
			dia = 1;
			addMes();
		} else {
			dia++;
		}
		return this;
	}
	
	public Data addDias(int qtd) {
		for (int i = 0; i < qtd; i++) {
			addDia();
		}
		return this;
	}
	
	public Data removeDias(int qtd) {
		for (int i = 0; i < qtd; i++) {
			removeDia();
		}
		return this;
	}

	public Data addMes() {
		mes++;
		if (mes > 12) {
			mes = 1;
			ajustaDia();
			addAno();
		}
		return this;
	}
	
	private void ajustaDia() {
		if (isDia(31)) {
			dia = getUltimoDiaDoMes();
		} else if (isFevereiro() && (isDia(29) || isDia(30))) {
			dia = getUltimoDiaDoMes();
		}
	}

	public Data addAno() {
		ano++;
		ajustaDia();
		return this;
	}
	
	public Data removeDia() {
		dia--;
		if (dia < 1) {
			dia = 1;
			removeMes();
			dia = getUltimoDiaDoMes();
		}
		return this;
	}

	public Data removeMes() {
		mes--;
		if (mes < 1) {
			mes = 12;
			removeAno();
		} else {
			ajustaDia();
		}
		return this;
	}

	public Data removeAno() {
		ano--;
		ajustaDia();
		return this;
	}
	
	public int getDiaDoAno() {
		
		int value = DIAS_ATE_O_MES.get(mes-1) + dia;
		
		if (mes > 2 && ehBissexto()) {
			value++;
		}
		
		return value;

	}	

	public static boolean ehBissextoStatic(int anoP) {
		return anoP % 4 == 0;
	}	

	public boolean ehBissexto() {
		return ehBissextoStatic(ano);
	}

	public int getDiaDaSemanaDoPrimeiroDiaDoAno() {

		Data data = hoje();

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

	public int getDiaDaSemana() {
		return toDate().getDay() + 1;
	}
	
	@Override
	public String toString() {
		return format("[dd]/[mm]/[yyyy]");
	}
	
	public String toUTCString() {
		return formatIngles("[ddd], [dd] [mmm] [yyyy] 00:00:00 GMT");
	}
	
	public Data setAno(int ano) {
		
		if (ano < 1850) {
			/* 
				coloquei estas validacoes só pra evitar erros, pois nao acho que trabalharemos com anos menores que esses
				se for o caso, ajustar no futuro
			*/
			throw new Error("ano < 1850");
		}

		if (ano > 2099) {
			/* 
				coloquei estas validacoes só pra evitar erros, pois nao acho que trabalharemos com anos maiores que esses
				se for o caso, ajustar no futuro
			*/
			throw new Error("ano > 2099");
		}
		
		this.ano = ano;
		
		return this;
		
	}
	
	private Error invalidError() {
		return new Error("Data Inválida");
	}
	
	private static Itens<Integer> MESES_31 = new Itens<>();
	static {
		MESES_31.add(1).add(3).add(5).add(7).add(8).add(10).add(12);
	}

	public static int getUltimoDiaDoMesStatic(int ano, int mes) {
		if (IntegerCompare.eq(mes, 2)) {
			if (IntegerCompare.isZero(ano % 4)) {
				return 29;
			} else {
				return 28;
			}
		}
		if (Data.MESES_31.anyMatch(o -> IntegerCompare.eq(o, mes))) {
			return 31;
		} else {
			return 30;
		}
	}
	
	
	public int getUltimoDiaDoMes() {
		return getUltimoDiaDoMesStatic(ano, mes);
	}
	
	public Data getUltimaDoMes() {
		return new Data(ano, mes, getUltimoDiaDoMes());
	}
	
	public Data setMes(int value) {
		if (value < 1 || value > 12) {
			throw invalidError();
		}
		mes = value;
		ajustaDia();
		return this;
	}
	
	public Data setDia(int value) {
		if (value < 1 || value > 31) {
			throw new Error("Dia inválido: " + value);
		}
		dia = value;
		ajustaDia();
		return this;
	}
	
	public boolean eq(Data o) {
		
		if (Null.is(o)) {
			return false;
		}
		
		return isDia(o.dia) && isMes(o.mes) && isAno(o.ano);
		
	}
	
	public String getNomeMes() {
		return or(NOMES_MESES.get(mes - 1), "");
	}

	public String getNomeMesIngles() {
		return or(NOMES_MESES_INGLES.get(mes - 1), "");
	}
	
	public String getNomeDiaSemana() {
		return or(NOMES_DIA.get(getDiaDaSemana() - 1), "");
	}

	public String getNomeDiaSemanaIngles() {
		return or(NOMES_DIA_INGLES.get(getDiaDaSemana() - 1), "");
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

	public boolean maiorOuIgual(Data d) {
		return maior(d) || eq(d);
	}
	
	public int anoMes() {
		return ano * 100 + mes;
	}
	
	public int intDate() {
		return anoMes() * 100 + dia;
	}
	
	public int compareInt(int intDate) {
		return IntegerCompare.compare(intDate(), intDate);
	}
	
	public int compare(Data o) {
		
		if (Null.is(o)) {
			return 1;
		}
		
		return compareInt(o.intDate());
		
	}	

	public boolean maior(Data data) {
		return IntegerCompare.eq(compare(data), 1);
	}
	
	public boolean maiorInt(int intDate) {
		return compareInt(intDate) > 0;
	}

	public boolean eqInt(int intDate) {
		return compareInt(intDate) == 0;
	}
	
	public boolean maiorQueHoje() {
		return maior(hoje());
	}

	public boolean menorQueHoje() {
		return menor(hoje());
	}

	public boolean menorOuIgual(Data d) {
		return menor(d) || eq(d);
	}

	public boolean menor(Data data) {
		return IntegerCompare.eq(compare(data), -1);
	}
	
	public boolean menorBuild(int ano, int mes, int dia) {
		return menor(new Data(ano, mes, dia));
	}
	
	public boolean menorInt(int intDate) {
		return compareInt(intDate) < 0;
	}

	public static Data getMaior(Data a, Data b) {
		if (Null.is(a)) {
			return b;
		}
		if (Null.is(b) || a.maior(b)) {
			return a;
		}
		return b;
	}
	
	public static Data getMenor(Data a, Data b) {
		if (Null.is(a)) {
			return b;
		}
		if (Null.is(b) || a.menor(b)) {
			return a;
		}
		return b;
	}	

	private String formatPrivate(String s, @NotNull String ds, @NotNull String nm) {
		
		if (StringEmpty.is(s)) {
			return "";
		}
		
		s = StringReplace.exec(s, "[ddddd]", ds);// quinta-feira
		s = StringReplace.exec(s, "[dddd]", StringSplit.exec(ds, "-").get(0));// quinta
		s = StringReplace.exec(s, "[ddd]", ds.substring(0, 3));
		s = StringReplace.exec(s, "[dd]", IntegerFormat.xx(dia));
		s = StringReplace.exec(s, "[d]", "" + dia);

		s = StringReplace.exec(s, "[mmmm]", nm);
		s = StringReplace.exec(s, "[mmm]", nm.substring(0, 3));
		s = StringReplace.exec(s, "[mm]", IntegerFormat.xx(mes));
		s = StringReplace.exec(s, "[m]", "" + mes);
		
		@NotNull String sAno = "" + ano;
		s = StringReplace.exec(s, "[yyyy]", sAno);
		s = StringReplace.exec(s, "[yy]", sAno.substring(2));
		s = StringReplace.exec(s, "[y]", sAno);

		s = StringReplace.exec(s, "[b]", "" + bimestre());
		s = StringReplace.exec(s, "[t]", "" + trimestre());
		s = StringReplace.exec(s, "[q]", "" + quadrimestre());
		s = StringReplace.exec(s, "[se]", "" + semestre());
		
		if (StringContains.is(s, "[age]")) {
			
			int idade = getIdade();
			
			String ss;
			
			if (idade < 0) {
				ss = "";
			} else if (idade == 1) {
				ss = "1 ano";
			} else {
				ss = idade + " anos";
			}
			
			s = StringReplace.exec(s, "[age]", ss);
			
		}
		
		return s;
		
	}
	
	public String format(String s) {
		return formatPrivate(s, getNomeDiaSemana(), getNomeMes());
	}

	public String formatIngles(String s) {
		return formatPrivate(s, getNomeDiaSemanaIngles(), getNomeMesIngles());
	}
	
	@PodeSerNull
	public static Data unformat(String format, String value) {
		return unformatProtected(format, value, false);
	}
	
	@PodeSerNull
	public static Data unformatProtected(String format, String value, boolean safe) {

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
			
			Data data = new Data(2010,1,1);

			value = value.toLowerCase();

			while (!StringEmpty.is(format)) {
				
				if (StringEmpty.is(value)) {
					if (safe) {
						return data;
					} else {
						throw new Error("O valor não contempla o formato: " + message);
					}
				}

				if (format.toLowerCase().startsWith("[yyyy]")) {
					format = format.substring(6);
					int anoP = IntegerParse.toIntSafe(value.substring(0, 4));
					if (anoP < 1850 || anoP > 2099) {
						throw new Error("ano < 1850 || ano > 2099: " + anoP);
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

				format = format.substring(1);
				value = value.substring(1);

			}

			return data;

		} catch (Exception e) {
			throw new Error(message + " >> " + e.getMessage());
		}

	}
	
	@Override
	public Data clone() {
		return new Data(ano, mes, dia);
	}
	
	public void print() {
		console.log(toString());
	}

	public boolean isSabado() {
		return getDiaDaSemana() == 7;
	}

	public boolean isDomingo() {
		return getDiaDaSemana() == 1;
	}

	public static Data fromDate(Date d) {
		if (Null.is(d)) {
			return null;
		}
		return new Data(d.getFullYear(), d.getMonth() + 1, d.getDate());
	}

	public static Data hoje() {
		return fromDate(new Date());
	}
	
	public static Data fromString(String s) {
		
		if (!StringLength.is(s, 10)) {
			return null;
		}

		s = StringExtraiNumeros.exec(s);

		if (!StringLength.is(s, 8)) {
			return null;
		}

		return Data.unformat("[dd][mm][yyyy]", s);		
		
	}
	
	private static boolean ehNum(String s, int casas) {

		if (StringEmpty.is(s)) {
			return false;
		}

		if (s.length() != casas) {
			return false;
		}
		
		s = StringExtraiNumeros.exec(s);
		
		if (s.length() != casas) {
			return false;
		}
		
		return true;
		
	}	
	
	public static String formatParcial(String s) {
		
		if (StringEmpty.is(s)) {
			return "";
		}
		
		if (s.length() >= 10) {
			Itens<String> partes = StringSplit.exec(s.substring(0, 10), "-");
			if (partes.size() >= 3) {
				if (ehNum(partes.get(0),4) && ehNum(partes.get(1),2) && ehNum(partes.get(2),2)) {
					return partes.get(2) + "/" + partes.get(1) + "/" + partes.get(0);
				}
			}
		}
		
		s = StringExtraiNumeros.exec(s);
		if (StringEmpty.is(s)) {
			return "";
		}
		while (s.startsWith("00")) {
			s = s.substring(1);
		}
		if (StringCompare.eq(s, "0")) {
			return "0";
		}
		if (Js.parseInt(s.substring(0, 1)) > 3) {
			s = "0" + s;
		}
		if (StringLength.is(s, 1)) {
			return s;
		}
		int dia = Js.parseInt(s.substring(0,2));

		if (dia > 31) {
			s = "0" + s;
			dia = Js.parseInt(s.substring(0,2));
		}

		String sdia = IntegerFormat.xx(dia);
		s = s.substring(2);

		if (StringEmpty.is(s)) {
			return sdia;
		}

		/* mes */

		while (s.startsWith("00")) {
			s = s.substring(1);
		}
		if (StringCompare.eq(s, "0")) {
			return sdia + "/0";
		}
		if (Js.parseInt(s.substring(0, 1)) > 1) {
			s = "0" + s;
		}
		if (StringLength.is(s, 1)) {
			return sdia + "/" + s;
		}
		int mes = Js.parseInt(s.substring(0,2));
		if (mes > 12) {
			s = "0" + s;
			mes = Js.parseInt(s.substring(0,2));
//			return sdia + "/1";
		}

		String smes = IntegerFormat.xx(mes);

		if (dia > 29 && IntegerCompare.eq(mes, 2)) {
			return sdia + "/0";
		}
		
		if (IntegerCompare.eq(dia, 31)) {
			if (!MESES31.contains(mes)) {
				return sdia + "/" + smes.substring(0, 1);
			}
		}

		s = s.substring(2);
		String result = sdia + "/" + smes;

		if (StringEmpty.is(s)) {
			return result;
		}

		/* ano */
		
		if (StringCompare.eq(s, "1") || StringCompare.eq(s, "19") || StringCompare.eq(s, "2") || StringCompare.eq(s, "20")) {
			return result + "/" + s;
		} else if (s.startsWith("20")) {
			result += "/" + StringLength.max(s, 4);
		} else if (s.startsWith("0") || s.startsWith("2")) {
			result += "/20" + StringLength.max(s, 2);
		} else if (s.startsWith("19")) {
			result += "/" + StringLength.max(s, 4);
		} else {
			result += "/19" + StringLength.max(s, 2);
		}

		if (IntegerCompare.eq(mes, 2) && IntegerCompare.eq(dia, 29) && StringLength.is(result, 10)) {
			int ano = Js.parseInt(StringAfterLast.get(result, "/"));
			if (!Equals.is(ano % 4, 0)) {
				result = StringRight.ignore1(result);
			}
		}

		return result;

	}

	public int getIdade() {
		Data h = hoje();
		if (maiorOuIgual(h) || (h.getAno() == getAno())) {
			return 0;
		}
		int idade = h.getAno() - getAno();
		if (getMes() > h.getMes()) {
			return idade - 1;
		}
		if (getMes() < h.getMes()) {
			return idade;
		}
		if (getDia() > h.getDia()) {
			return idade - 1;
		}
		return idade;
	}
	
	public int getAnoMes() {
		return getAno() * 100 + getMes();
	}
	
	public Date toDate() {
		return new Date(ano, mes-1, dia, 0, 0, 0, 0);
	}
	
	public String formatJs() {
		return format("[yyyy]-[mm]-[dd]");
	}
	
	public static Data unformatJs(String s) {
		
		if (StringEmpty.is(s)) {
			return null;
		}
		
		if (StringCompare.eq(s, "hoje")) {
			return Data.hoje();
		}
		
		if (s.startsWith("hoje")) {
			
			s = StringTrim.plus(s.substring(4));
			
			Data o = Data.hoje();
			
			if (s.startsWith("+")) {
				s = s.substring(1);
				int dias = IntegerParse.toInt(s);
				o.addDias(dias);
				return o;
			} else if (s.startsWith("-")) {
				s = s.substring(1);
				int dias = IntegerParse.toInt(s);
				o.removeDias(dias);
				return o;
			} else {
				throw new Error("Inválido: " + s);
			}
			
		}
		
		return unformat("[yyyy]-[mm]-[dd]", s);
	}
	
	public static Data getAleatorioEntre(Data inicio, Data fim) {
		
		int ano = Randomico.getInt(inicio.getAno(), fim.getAno());
		int mes = Randomico.getInt(1, 12);
		int dia = Data.getUltimoDiaDoMesStatic(ano, mes);
		Data data = new Data(ano, mes, dia);
		
		if (data.menor(inicio) || data.maior(fim)) {
			return getAleatorioEntre(inicio, fim);
		} else {
			return data;
		}
		
	}
	
	public static Data getAleatorio() {
		return getAleatorioEntre(new Data(1950, 1, 1), new Data(2030, 1, 1));
	}
	
	private static final int LENGTH_FORMAT_1 = StringLength.get("yyyymmdd");
	private static final int LENGTH_FORMAT_2 = StringLength.get("yyyy-mm-dd");
	
	public static Data discover(String s) {
		
		String ss = StringExtraiNumeros.exec(s);
		
		if (ss.length() == LENGTH_FORMAT_1) {
			
			if (s.length() == LENGTH_FORMAT_2) {
				
				Itens<String> cs = StringSplit.exec(s, "");
				
				if (s.startsWith("19") || s.startsWith("20")) {
					if (cs.get(4) == "-" && cs.get(7) == "-") {
						
						try {
							return unformat("[yyyy][mm][dd]", ss);
						} catch (Exception e) {
							// TODO: handle exception
						}
						
					}
				}
			}
			
		}
		
		return null;
		
	}	

	public static void sortValues(Data inicio, Data fim) {
		
		if (Null.is(inicio) || Null.is(fim)) {
			return;
		}
		
		if (inicio.menorOuIgual(fim)) {
			return;
		}
		
		Data x = inicio.clone();
		inicio.set(fim);
		fim.set(x);
		
	}

	
	private void set(Data o) {
		setDia(1);
		setAno(o.getAno());
		setMes(o.getMes());
		setDia(o.getDia());
	}

	public int getBimestre() {
		
		if (mes < 3) {
			return 1;
		} else if (mes < 5) {
			return 2;
		} else if (mes < 7) {
			return 3;
		} else if (mes < 9) {
			return 4;
		} else if (mes < 11) {
			return 5;
		} else {
			return 6;
		}
		
	}

	public int getTrimestre() {
		
		if (mes < 4) {
			return 1;
		} else if (mes < 7) {
			return 2;
		} else if (mes < 10) {
			return 3;
		} else {
			return 4;
		}
		
	}

	public int getQuadrimestre() {
		
		if (mes < 5) {
			return 1;
		} else if (mes < 9) {
			return 2;
		} else {
			return 3;
		}
		
	}

	public int getSemestre() {
		return mes < 7 ? 1 : 2;
	}
	
	public static void addFuncaoTestaFeriado(F1<Data, Boolean> test) {
		FUNCOES_DE_TESTE_DE_FERIADO.add(test);
	}
	
	public static void addFeriadoNacional(Data data) {
		FERIADOS_NACIONAIS_MOVEIS.add(data.format("[dd]/[mm]/[yyyy]"));
	}
	
	public boolean isFeriadoNacional() {
		
		String ddmm = format("[dd]/[mm]");
		if (FERIADOS_NACIONAIS_FIXOS.anyMatch(s -> StringCompare.eq(ddmm, s))) {
			return true;
		}

		String ddmmyyyy = format("[dd]/[mm]/[yyyy]");
		if (FERIADOS_NACIONAIS_MOVEIS.anyMatch(s -> StringCompare.eq(ddmmyyyy, s)) || FUNCOES_DE_TESTE_DE_FERIADO.anyMatch(o -> o.call(this))) {
			return true;
		}

		return false;		
		
	}
	
	public boolean isUtil() {
		return !isSabado() && !isDomingo() && !isFeriadoNacional();
	}
	
	@IgnorarDaquiPraBaixo
	
	public static void main(String[] args) {
//		System.out.println(formatParcial("5723"));
		System.out.println(formatParcial("99"));
		System.out.println(formatParcial("47"));
		System.out.println(formatParcial("37"));
		System.out.println(formatParcial("577"));
		System.out.println(formatParcial("5725"));
		System.out.println(formatParcial("5705"));
		Data.unformat("[dd]/[mm]/[yyyy]", "31/12/2023");
	}
	
}