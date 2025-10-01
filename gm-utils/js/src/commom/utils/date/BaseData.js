import ArrayLst from '../array/ArrayLst';
import FeriadoRecessoNaoFuncionamento from './FeriadoRecessoNaoFuncionamento';
import IntegerCompare from '../integer/IntegerCompare';
import IntegerFormat from '../integer/IntegerFormat';
import IntegerParse from '../integer/IntegerParse';
import Null from '../object/Null';
import ObjJs from '../object/ObjJs';
import StringCompare from '../string/StringCompare';
import StringContains from '../string/StringContains';
import StringEmpty from '../string/StringEmpty';
import Tempo from './Tempo';
import UAnoMes from './UAnoMes';

export default class BaseData extends ObjJs {

	ano = 1900;
	mes = 1;
	dia = 1;
	hora = 0;
	minuto = 0;
	segundo = 0;
	milesimo = 0;

	constructor(ano, mes, dia) {
		super();
		this.setAno(ano);
		this.setMes(mes);
		this.setDia(dia);
	}

	static build(aano, ames, adia, ahora, aminuto, asegundo, amilesimo) {
		let o = new BaseData(aano, ames, adia);
		o.setHora(BaseData.safe(ahora));
		o.setMinuto(BaseData.safe(aminuto));
		o.setSegundo(BaseData.safe(asegundo));
		o.setMilesimo(BaseData.safe(amilesimo));
		return o;
	}

	static safe(value) {
		return Null.is(value) ? 0 : value;
	}

	setDiaSemUltrapassarMes(diaP) {
		let m = this.getMes();
		this.setDia(diaP);
		while (this.getMes() > m) {
			this.removeDia();
		}
	}

	verificaSeODiaEhValido(value) {
		if (value > 28) {
			if ((value > this.getUltimoDiaDoMes()) || (value < 1)) {
				throw this.erroDataInvalida();
			}
		}
	}

	setDia(value) {
		this.verificaSeODiaEhValido(value);
		this.dia = value;
		return this;
	}

	erroDataInvalida() {
		throw new Error("Data Inválida");
	}

	setMes(value) {
		if (value < 1 || value > 12) {
			throw this.erroDataInvalida();
		}
		this.mes = value;
		this.verificaSeODiaEhValido(this.dia);
		return this;
	}

	setAno(value) {
		this.ano = value;
		this.verificaSeODiaEhValido(this.dia);
		return this;
	}

	setHora(value) {
		if (value < 0 || value > 23) {
			throw this.erroDataInvalida();
		}
		this.hora = value;
		return this;
	}

	setMinuto(value) {
		if (value < 0 || value > 59) {
			throw this.erroDataInvalida();
		}
		this.minuto = value;
		return this;
	}

	setSegundo(value) {
		if (value < 0 || value > 59) {
			throw this.erroDataInvalida();
		}
		this.segundo = value;
		return this;
	}

	setMilesimo(value) {
		if (value < 0 || value > 999) {
			throw this.erroDataInvalida();
		}
		this.milesimo = value;
		return this;
	}

	getSeculo() {
		return this.ano / 100;
	}

	ehBissexto() {
		return BaseData.ehBissextoStatic(this.ano);
	}

	static ehBissextoStatic(anoP) {
		return anoP % 4 === 0;
	}

	getDiaDoAno() {
		let value = this.dia;
		if (this.mes > 1) {
			value += 31;
			if (this.mes > 2) {
				if (this.ehBissexto()) {
					value += 29;
				} else {
					value += 28;
				}
				if (this.mes > 3) {
					value += 31;
					if (this.mes > 4) {
						value += 30;
						if (this.mes > 5) {
							value += 31;
							if (this.mes > 6) {
								value += 30;
								if (this.mes > 7) {
									value += 31;
									if (this.mes > 8) {
										value += 31;
										if (this.mes > 9) {
											value += 30;
											if (this.mes > 10) {
												value += 31;
												if (this.mes > 11) {
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

	getDiaDaSemanaDoPrimeiroDiaDoAno() {

		let data = new BaseData(2020, 1, 1);

		let x = 4;

		if (data.ano > this.ano) {

			while (data.ano > this.ano) {
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

			while (data.ano < this.ano) {
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

	/* *
	* https://pt.wikipedia.org/wiki/Algoritmo_Doomsday
	*/
	getDiaDaSemana() {

		let diaDaSemanaDoPrimeiroDiaDoAno = this.getDiaDaSemanaDoPrimeiroDiaDoAno();

		let x = this.getDiaDoAno() % 7 + diaDaSemanaDoPrimeiroDiaDoAno - 1;

		if (x > 7) {
			x -= 7;
		}

		return x;

	}

	sabado() {
		return this.getDiaDaSemana() === 7;
	}

	domingo() {
		return this.getDiaDaSemana() === 1;
	}

	getUltimoDiaDoMes() {
		return UAnoMes.getUltimoDiaDoMes(this.ano, this.mes);
	}

	addDia() {
		if (IntegerCompare.eq(this.dia, this.getUltimoDiaDoMes())) {
			this.dia = 1;
			this.addMes();
		} else {
			this.dia++;
		}
		return this;
	}

	addMes() {
		this.mes++;
		if (this.mes > 12) {
			this.mes = 1;
			if (this.dia > this.getUltimoDiaDoMes()) {
				throw this.erroDataInvalida();
			}
			this.addAno();
		}
		return this;
	}

	addAno() {
		this.ano++;
		if (IntegerCompare.eq(this.mes, 2) && IntegerCompare.eq(this.dia, 29)) {
			throw this.erroDataInvalida();
		}
	}

	addHora() {
		this.hora++;
		if (this.hora > 23) {
			this.hora = 0;
			this.addDia();
		}
	}

	addMinuto() {
		this.minuto++;
		if (this.minuto > 59) {
			this.minuto = 0;
			this.addHora();
		}
	}

	addSegundo() {
		this.segundo++;
		if (this.segundo > 59) {
			this.segundo = 0;
			this.addMinuto();
		}
	}

	addMilesimo() {
		this.milesimo++;
		if (this.milesimo > 999) {
			this.milesimo = 0;
			this.addSegundo();
		}
	}

	/* ----------------------------- */
	removeDia() {
		this.dia--;
		if (this.dia < 1) {
			this.dia = 1;
			this.removeMes();
			this.dia = this.getUltimoDiaDoMes();
		}
		return this;
	}

	removeMes() {
		this.mes--;
		if (this.mes < 1) {
			this.mes = 12;
			this.removeAno();
		} else if (this.dia > this.getUltimoDiaDoMes()) {
			throw new Error("Data inválida!");
		}
		return this;
	}

	removeAno() {
		this.ano--;
		if (IntegerCompare.eq(this.mes, 2) && IntegerCompare.eq(this.dia, 29)) {
			throw new Error("Data inválida!");
		}
		return this;
	}

	removeHora() {
		if (IntegerCompare.isZero(this.hora)) {
			this.hora = 23;
			this.removeDia();
		} else {
			this.hora--;
		}
		return this;
	}

	removeMinuto() {
		if (IntegerCompare.isZero(this.minuto)) {
			this.minuto = 59;
			this.removeHora();
		} else {
			this.minuto--;
		}
		return this;
	}

	removeSegundo() {
		if (IntegerCompare.isZero(this.segundo)) {
			this.segundo = 59;
			this.removeMinuto();
		} else {
			this.segundo--;
		}
		return this;
	}

	removeMilesimo() {
		if (IntegerCompare.isZero(this.milesimo)) {
			this.milesimo = 999;
			this.removeSegundo();
		} else {
			this.milesimo--;
		}
		return this;
	}

	addDias(count) {
		if (count < 0) {
			return this.removeDias(-count);
		}
		for (let i = 0; i < count; i++) {
			this.addDia();
		}
		return this;
	}

	removeDias(count) {
		if (count < 0) {
			return this.addDias(-count);
		}
		for (let i = 0; i < count; i++) {
			this.removeDia();
		}
		return this;
	}

	addMeses(count) {
		if (count < 0) {
			return this.removeMeses(-count);
		}
		for (let i = 0; i < count; i++) {
			this.addMes();
		}
		return this;
	}

	removeMeses(count) {
		if (count < 0) {
			return this.addMeses(-count);
		}
		for (let i = 0; i < count; i++) {
			this.removeMes();
		}
		return this;
	}

	addAnos(count) {
		if (count < 0) {
			return this.removeAnos(-count);
		}
		for (let i = 0; i < count; i++) {
			this.addMes();
		}
		return this;
	}

	removeAnos(count) {
		if (count < 0) {
			return this.addAnos(-count);
		}
		for (let i = 0; i < count; i++) {
			this.removeAno();
		}
		return this;
	}

	addHoras(count) {
		if (count < 0) {
			return this.removeHoras(-count);
		}
		for (let i = 0; i < count; i++) {
			this.addHora();
		}
		return this;
	}

	removeHoras(count) {
		if (count < 0) {
			return this.addHoras(-count);
		}
		for (let i = 0; i < count; i++) {
			this.removeHora();
		}
		return this;
	}

	addMinutos(count) {
		if (count < 0) {
			return this.removeMinutos(-count);
		}
		for (let i = 0; i < count; i++) {
			this.addMinuto();
		}
		return this;
	}

	removeMinutos(count) {
		if (count < 0) {
			return this.addMinutos(-count);
		}
		for (let i = 0; i < count; i++) {
			this.removeMinuto();
		}
		return this;
	}

	addSegundos(count) {
		if (count < 0) {
			return this.removeSegundos(-count);
		}
		for (let i = 0; i < count; i++) {
			this.addSegundo();
		}
		return this;
	}

	removeSegundos(count) {
		if (count < 0) {
			return this.addSegundos(-count);
		}
		for (let i = 0; i < count; i++) {
			this.removeSegundo();
		}
		return this;
	}

	addMilesimos(count) {
		if (count < 0) {
			return this.removeMilesimos(-count);
		}
		for (let i = 0; i < count; i++) {
			this.addMilesimo();
		}
		return this;
	}

	removeMilesimos(count) {
		if (count < 0) {
			return this.addMilesimos(-count);
		}
		for (let i = 0; i < count; i++) {
			this.removeMilesimo();
		}
		return this;
	}

	bimestre() {
		if (this.mes < 3) {
			return 1;
		}
		if (this.mes < 5) {
			return 2;
		}
		if (this.mes < 7) {
			return 3;
		}
		if (this.mes < 9) {
			return 4;
		}
		if (this.mes < 11) {
			return 5;
		}
		return 6;
	}

	trimestre() {
		if (this.mes < 4) {
			return 1;
		}
		if (this.mes < 7) {
			return 2;
		}
		if (this.mes < 10) {
			return 3;
		}
		return 4;
	}

	quadrimestre() {
		if (this.mes < 5) {
			return 1;
		}
		if (this.mes < 9) {
			return 2;
		}
		return 3;
	}

	semestre() {
		if (this.mes < 7) {
			return 1;
		}
		return 2;
	}

	formatSql() {
		return this.format("[yyyy]-[mm]-[dd]");
	}

	format(s) {
		s = s.replace("[ddddd]", this.nomeDiaSemana()); /* quinta-feira */
		s = s.replace("[dddd]", this.nomeDiaSemana().split("-")[0]); /* quinta */
		s = s.replace("[ddd]", this.nomeDiaSemana().substring(0, 3));
		s = s.replace("[dd]", IntegerFormat.xx(this.getDia()));
		s = s.replace("[d]", "" + this.getDia());

		s = s.replace("[mmmm]", this.nomeMes());
		s = s.replace("[mmm]", this.nomeMes().substring(0, 3));
		s = s.replace("[mm]", IntegerFormat.xx(this.getMes()));
		s = s.replace("[m]", "" + this.getMes());

		s = s.replace("[yyyy]", "" + this.getAno());
		s = s.replace("[yy]", ("" + this.getAno()).substring(2));
		s = s.replace("[y]", "" + this.getAno());

		s = s.replace("[hh]", IntegerFormat.xx(this.getHora()));
		s = s.replace("[h]", "" + this.getHora());

		s = s.replace("[nn]", IntegerFormat.xx(this.getMinuto()));
		s = s.replace("[n]", "" + this.getMinuto());

		s = s.replace("[ss]", IntegerFormat.xx(this.getSegundo()));
		s = s.replace("[s]", "" + this.getSegundo());

		s = s.replace("[lll]", IntegerFormat.xxx(this.getMilesimo()));
		s = s.replace("[ll]", IntegerFormat.xx(this.getMilesimo()));
		s = s.replace("[l]", "" + this.getMilesimo());

		s = s.replace("[b]", "" + this.bimestre());
		s = s.replace("[t]", "" + this.trimestre());
		s = s.replace("[q]", "" + this.quadrimestre());
		return s.replace("[se]", "" + this.semestre());
	}

	static unformatDate(format, value) {
		let o = BaseData.unformat(format, value);
		return o === null ? null : o.toDate();
	}

	static unformat(format, value) {

		if (StringEmpty.is(value)) {
			return null;
		}
		if (StringEmpty.is(format)) {
			throw new Error("Não foi informado o formato");
		}

		let message = "error Data.unformat(format = '" + format + "', value = '" + value + "')";

		if (!StringContains.is(format, "]")) {
			throw new Error("O formato informado é inválido: " + message);
		}

		try {

			let data = BaseData.build(2016, 1, 1, 0, 0, 0, 0);
			data.zeraTime();

			value = value.toLowerCase();

			while (!StringEmpty.is(format)) {

				if (format.toLowerCase().startsWith("[yyyy]")) {
					format = format.substring(6);
					let anoP = IntegerParse.toInt(value.substring(0, 4));
					if (anoP < 1900 || anoP > 2030) {
						throw new Error("ano < 1900 || ano > 2030: " + anoP);
					}
					value = value.substring(4);
					data.setAno(anoP);
					continue;
				}
				if (format.toLowerCase().startsWith("[yy]")) {
					format = format.substring(4);
					let anoP = IntegerParse.toInt(value.substring(0, 2)) + 1900;
					value = value.substring(2);
					data.setAno(anoP);
					continue;
				}
				if (format.toLowerCase().startsWith("[m]")) {
					format = format.substring(3);
					let mesP = IntegerParse.toInt(value.substring(0, 1));
					value = value.substring(1);
					data.setMes(mesP);
					continue;
				}
				if (format.toLowerCase().startsWith("[mm]")) {
					format = format.substring(4);
					let mesP = IntegerParse.toInt(value.substring(0, 2));
					if (mesP < 1 || mesP > 12) {
						throw new Error("mes < 1 || mes > 12: " + mesP);
					}
					value = value.substring(2);
					data.setMes(mesP);
					continue;
				}
				if (format.toLowerCase().startsWith("[mmm]")) {

					format = format.substring(5);

					let conseguiu = false;

					for (let i = 1; i <= 12; i++) {
						let nomeMes = BaseData.NOMES_MESES.get(i - 1).toLowerCase();
						nomeMes = nomeMes.substring(0, 3);
						if (value.startsWith(nomeMes)) {
							value = value.substring(nomeMes.length);
							data.setMes(i);
							conseguiu = true;
							break;
						}
						nomeMes = BaseData.NOMES_MESES_INGLES.get(i - 1).toLowerCase();
						nomeMes = nomeMes.substring(0, 3);
						if (value.startsWith(nomeMes)) {
							value = value.substring(nomeMes.length);
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

					let conseguiu = false;

					for (let i = 1; i <= 12; i++) {
						let nomeMes = BaseData.NOMES_MESES.get(i - 1).toLowerCase();
						if (value.startsWith(nomeMes)) {
							value = value.substring(nomeMes.length);
							data.setMes(i);
							conseguiu = true;
							break;
						}
						nomeMes = BaseData.NOMES_MESES_INGLES.get(i - 1).toLowerCase();
						if (value.startsWith(nomeMes)) {
							value = value.substring(nomeMes.length);
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
					let diaP = IntegerParse.toInt(value.substring(0, 2));
					if (diaP < 0 || diaP > 31) {
						throw new Error("dia < 0 || dia > 31: " + diaP);
					}
					value = value.substring(2);
					data.setDia(diaP);
					continue;
				}
				if (format.toLowerCase().startsWith("[d]")) {
					format = format.substring(3);
					let diaP = IntegerParse.toInt(value.substring(0, 1));
					value = value.substring(1);
					data.setDia(diaP);
					continue;
				}
				if (format.toLowerCase().startsWith("[hh]")) {
					format = format.substring(4);
					let horaP = IntegerParse.toInt(value.substring(0, 2));
					value = value.substring(2);
					data.setHora(horaP);
					continue;
				}
				if (format.toLowerCase().startsWith("[nn]")) {
					format = format.substring(4);
					let minutoP = IntegerParse.toInt(value.substring(0, 2));
					value = value.substring(2);
					data.setMinuto(minutoP);
					continue;
				}
				if (format.toLowerCase().startsWith("[ss]")) {
					format = format.substring(4);
					let segundoP = IntegerParse.toInt(value.substring(0, 2));
					value = value.substring(2);
					data.setSegundo(segundoP);
					continue;
				}

				format = format.substring(1);
				value = value.substring(1);

			}

			return data;

		} catch (e) {
			throw new Error(message + " >> " + e.message);
		}

	}

	nomeMes() {
		return BaseData.NOMES_MESES.get(this.mes - 1);
	}

	static NOMES_DIA = ArrayLst.build("Domingo", "Segunda-feira", "Terça-feira",
			"Quarta-feira", "Quinta-feira", "Sexta-feira", "Sábado");
	static NOMES_MESES = ArrayLst.build("Janeiro", "Fevereiro", "Março", "Abril", "Maio",
			"Junho", "Julho", "Agosto", "Setembro", "Outubro", "Novembro", "Dezembro");
	static NOMES_MESES_INGLES = ArrayLst.build("January", "February", "March", "April", "May",
			"June", "July", "August", "September", "October", "November", "December");

	nomeDiaSemana() {
		return BaseData.NOMES_DIA.get(this.getDiaDaSemana() - 1);
	}

	maiorOuIgual(d) {
		return this.maior(d) || this.eq(d);
	}

	maior(data) {
		return IntegerCompare.eq(this.compare(data), 1);
	}

	maiorQueHoje() {
		return this.maior(BaseData.hoje());
	}

	menorQueHoje() {
		return this.menor(BaseData.hoje());
	}

	menorOuIgual(d) {
		return this.menor(d) || this.eq(d);
	}

	menor(data) {
		return IntegerCompare.eq(this.compare(data), -1);
	}

	static getMaior(a, b) {
		if (a === null) {
			return b;
		}
		if ((b === null) || a.maior(b)) {
			return a;
		}
		return b;
	}

	static getMenor(a, b) {
		if (a === null) {
			return b;
		}
		if ((b === null) || a.menor(b)) {
			return a;
		}
		return b;
	}

	dias_uteis_no_mes() {
		let data = this.copy();
		data.setDia(1);
		return data.dias_uteis_ate_o_fim_do_mes();
	}

	dias_uteis_ate_o_fim_do_mes() {
		let data = this.copy();
		data.setDia(this.getUltimoDiaDoMes());
		return this.dias_uteis_ate(data);
	}

	dias_uteis_ate(fim) {
		let data = this.copy();
		let i = 0;
		while (data.menor(fim)) {
			if (data.ehDiaUtil()) {
				i++;
			}
			data.addDia();
		}
		return i;
	}

	menosDiasUteis(dias) {
		let data = this.copy();
		while (dias > 0) {
			if (data.ehDiaUtil()) {
				dias--;
			}
			data.removeDia();
		}
		return data;
	}

	menosDias(dias) {
		let copy = this.copy();
		copy.removeDias(dias);
		return copy;
	}

	dias_ate_hoje() {
		return this.ate(BaseData.now());
	}

	ate(d) {
		return d.menos(this);
	}

	menos(d) {
		return this.diferenca(d).emDias();
	}

	static ontem() {
		let data = BaseData.hoje();
		data.removeDia();
		return data;
	}

	static hoje() {
		let data = BaseData.now();
		data.zeraTime();
		return data;
	}

	static amanha() {
		let data = BaseData.hoje();
		data.addDia();
		return data;
	}

	eq(x) {
		if (Null.is(x) || IntegerCompare.ne(this.ano, x.ano) || IntegerCompare.ne(this.mes, x.mes) || IntegerCompare.ne(this.dia, x.dia)) {
			return false;
		}
		if (IntegerCompare.ne(this.hora, x.hora)) {
			return false;
		}
		if (IntegerCompare.ne(this.minuto, x.minuto)) {
			return false;
		}
		if (IntegerCompare.ne(this.segundo, x.segundo)) {
			return false;
		}
		if (IntegerCompare.ne(this.milesimo, x.milesimo)) {
			return false;
		}
		return true;
	}

	static mensagemBomMomento() {
		let now = BaseData.now();
		if (now.getHora() < 12) {
			return "bom dia!";
		}
		if (now.getHora() < 18) {
			return "boa tarde!";
		}
		return "boa noite!";
	}

	zeraTime() {
		this.setHora(0);
		this.setMinuto(0);
		this.setSegundo(0);
		this.setMilesimo(0);
		return this;
	}

	copy() {
		return BaseData.build(this.ano, this.mes, this.dia, this.hora, this.minuto, this.segundo, this.milesimo);
	}

	setUltimoDiaMes() {
		this.setDia(this.getUltimoDiaDoMes());
		return this;
	}

	isUltimoDiaDoMes() {
		return IntegerCompare.eq(this.dia, this.getUltimoDiaDoMes());
	}

	getIdade() {
		let hoje = BaseData.now();
		if (this.maiorOuIgual(hoje) || (hoje.getAno() === this.getAno())) {
			return 0;
		}
		let idade = hoje.getAno() - this.getAno();
		if (this.getMes() > hoje.getMes()) {
			return idade - 1;
		}
		if (this.getMes() < hoje.getMes()) {
			return idade;
		}
		if (this.getDia() > hoje.getDia()) {
			return idade - 1;
		}
		return idade;
	}

	diferenca(data) {
		return Tempo.buildDiferenca(this, data);
	}

	feriadoRecessoNaoFuncionamento() {
		return FeriadoRecessoNaoFuncionamento.test(this);
	}

	ehDiaUtil(funcionaAosSabados) {
		if (funcionaAosSabados) {
			return (!this.domingo() && !this.feriadoRecessoNaoFuncionamento());
		}
		return (!this.sabado() && !this.domingo() && !this.feriadoRecessoNaoFuncionamento());
	}

	isHoje() {
		let hoje = BaseData.hoje();
		if ((hoje.getAno() !== this.getAno()) || (hoje.getMes() !== this.getMes()) || (hoje.getDia() !== this.getDia())) {
			return false;
		}
		return true;
	}

	equals(anoP, mesP, diaP) {
		if ((this.getAno() !== anoP) || (this.getMes() !== mesP) || (this.getDia() !== diaP)) {
			return false;
		}
		return true;
	}

	/* private static ListString diasSemanaAbrev = ListString.array("dom","seg","ter","qua","qui","sex","sáb"); */

	static getProximoDiaUtil(data) {
		data = data.copy();
		while (!data.ehDiaUtil()) {
			data.addDia();
		}
		return data;
	}

	proximoDiaUtil() {
		return this.copy().addDia().proximoDiaUtilContandoComEsta();
	}

	proximoDiaUtilContandoComEsta() {
		return BaseData.getProximoDiaUtil(this);
	}

	jaPassou() {
		return BaseData.hoje().maior(this);
	}

	mesmoDiaQue(data) {
		return this.getDia() === data.getDia() && this.getMes() === data.getMes() && this.getAno() === data.getAno();
	}

	addMinutosUteis(x) {

		if (this.getHora() > 18) {
			this.zeraTime();
			this.setHora(8);
			this.addDia();
		} else if (this.getHora() < 8) {
			this.zeraTime();
			this.setHora(8);
		} else if (this.getHora() === 13) {
			this.zeraTime();
			this.setHora(14);
		}

		this.addMinutos(x);

		if (this.getHora() > 18) {
			this.setHora(8);
			this.addDia();
			return;
		}

		if (this.getHora() === 13) {
			this.setHora(14);
			return;
		}

		if (this.getHora() === 12 && this.getMinuto() > 0) {
			this.setHora(14);
		}

	}

	diferencaEmDiasUteis(data) {

		let maior, menor;

		if (this.maior(data)) {
			menor = data;
			maior = this.copy();
		} else {
			menor = this.copy();
			maior = data;
		}

		let x = 0;

		while (!menor.mesmoDiaQue(maior)) {
			if (menor.ehDiaUtil()) {
				x++;
			}
			menor.addDia();
		}

		return x;

	}

	setUltimoMomentoDoDia() {
		this.setHora(23);
		this.setMinuto(59);
		this.setSegundo(59);
	}

	jaPassouSegundos(x) {
		return this.diferenca(BaseData.now()).emSegundos() > x;
	}

	jaPassouMinutos(x) {
		return this.diferenca(BaseData.now()).emMinutos() > x;
	}

	jaPassouHoras(x) {
		return this.diferenca(BaseData.now()).emHoras() > x;
	}

	toDate() {
		return new Date(this.ano, this.mes-1, this.dia, this.hora, this.minuto, this.segundo, this.milesimo);
	}

	static toData(date) {
		if (Null.is(date)) {
			return null;
		}
		return BaseData.build(date.getYear() + 1900, date.getMonth() + 1, date.getDate(), date.getHours(), date.getMinutes(), date.getSeconds(), 0);
	}

	static comoBuscarNow;

	static now() {

		if (Null.is(BaseData.comoBuscarNow)) {
			return BaseData.nowDispositivo();
		}
		return BaseData.comoBuscarNow();

	}

	static nowDispositivo() {
		return BaseData.toData(new Date());
	}

	getAnoMes() {
		return this.getAno() * 100 + this.getMes();
	}

	compare(data) {
		if (data === null) {
			return 1;
		}
		return StringCompare.compare(this.format("[yyyy][mm][dd][hh][nn][ss][lll]"),
				data.format("[yyyy][mm][dd][hh][nn][ss][lll]"));
	}

	getDia() {
		return this.dia;
	}

	getMes() {
		return this.mes;
	}

	getAno() {
		return this.ano;
	}

	getHora() {
		return this.hora;
	}

	getMinuto() {
		return this.minuto;
	}

	getSegundo() {
		return this.segundo;
	}

	getMilesimo() {
		return this.milesimo;
	}

	getTime() {
		return this.toDate().getTime();
	}

	static unJson(s) {
		if (StringEmpty.is(s)) {
			return null;
		}
		let o = JSON.parse(s);
		return BaseData.build(o.ano, o.mes, o.dia, o.hora, o.minuto, o.segundo, o.milesimo);
	}

	toJsonImpl() {
		return this.format("{\"ano\": [y], \"mes\": [m], \"dia\": [d], \"hora\": [h], \"minuto\": [n], \"segundo\": [s], \"milesimo\": [l]}");
	}

}
