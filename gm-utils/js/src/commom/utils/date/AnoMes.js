import IntegerFormat from '../integer/IntegerFormat';

export default class AnoMes {

	ano = 0;
	mes = 0;

	constructor(ano, mes) {
		this.ano = ano;
		this.mes = mes;
	}

	back() {
		this.mes--;
		if (this.mes === 0) {
			this.mes = 12;
			this.ano--;
		}
	}

	next() {
		this.mes++;
		if (this.mes === 13) {
			this.mes = 1;
			this.ano++;
		}
	}

	toString() {
		return IntegerFormat.xx(this.mes) + "/" + this.ano;
	}

	toInt() {
		return this.ano * 100 + this.mes;
	}

}
