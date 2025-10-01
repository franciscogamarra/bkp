/* @Getter @Setter */
export default class Tempo {

	anos = 0;
	meses = 0;
	dias = 0;
	horas = 0;
	minutos = 0;
	segundos = 0;
	negativo = false;

	constructor(dias){

		while (dias >= 365) {
			dias -= 365;
			this.anos++;
		}

		while (dias >= 30) {
			dias -= 30;
			this.meses++;
		}

		this.dias = dias;

	}

	static buildDiferenca(a, b) {

		let o = new Tempo(0);

		if (a.menor(b)) {
			let c = a;
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

	decAnos() {
		this.anos--;
		this.meses += 12;
	}

	decMes() {
		this.meses--;
		this.dias += 30;
		if (this.meses === -1) {
			this.decAnos();
		}
	}
	decDias() {
		this.dias--;
		this.horas += 24;
		if (this.dias === -1) {
			this.decMes();
		}
	}
	decHoras() {
		this.horas--;
		this.minutos += 60;
		if (this.horas === -1) {
			this.decDias();
		}
	}
	decMinutos() {
		this.minutos--;
		this.segundos += 60;
		if (this.minutos === -1) {
			this.decHoras();
		}
	}

	emHoras() {
		return this.horas + this.dias*24*60;
	}
	emMinutos() {
		return this.minutos + this.emHoras() * 60;
	}
	emSegundos() {
		return this.segundos + this.emMinutos() * 60;
	}
	emDias(){
		return this.anos * 365 + this.meses * 30 + this.dias;
	}
	emMeses(){
		return this.meses;
	}
	emAnos() {
		return this.anos;
	}
	emDiasReais(){
		let x = this.emDias();
		if (this.negativo) {
			return -x;
		}
		return x;
	}
	toString() {

		let s = " " + this.anos + " anos e " + this.meses + " meses e " + this.dias + " dias";

		s = s.replace(" 0 anos e ", " ");
		s = s.replace(" 1 anos e ", " 1 ano e ");
		s = s.replace(" 0 meses e ", " ");
		s = s.replace(" 1 meses e ", " 1 m\u00eas e ");
		s = s.replace(" e 0 dias", " ");
		s = s.replace(" 1 dias", " 1 dia");
		return s.trim();
	}
	getAnos = () => this.anos;
	getDias = () => this.dias;
	getHoras = () => this.horas;
	getMeses = () => this.meses;
	getMinutos = () => this.minutos;
	getNegativo = () => this.negativo;
	getSegundos = () => this.segundos;
	setAnos = o => this.anos = o;
	setDias = o => this.dias = o;
	setHoras = o => this.horas = o;
	setMeses = o => this.meses = o;
	setMinutos = o => this.minutos = o;
	setNegativo = o => this.negativo = o;
	setSegundos = o => this.segundos = o;

}
