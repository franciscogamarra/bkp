import BaseData from '../date/BaseData';
import IntegerCompare from '../integer/IntegerCompare';
import IntegerFormat from '../integer/IntegerFormat';
import IntegerParse from '../integer/IntegerParse';
import StringCompare from '../string/StringCompare';
import StringEmpty from '../string/StringEmpty';
import StringExtraiNumeros from '../string/StringExtraiNumeros';
import StringLength from '../string/StringLength';
import StringReplace from '../string/StringReplace';
import StringRight from '../string/StringRight';

export default class UCpf {

	static format(s) {
		if (StringEmpty.is(s)) {
			return null;
		}
		s = StringExtraiNumeros.exec(s);
		let len = StringLength.get(s);
		if (len > 11) {
			s = s.substring(0, 11);
		} else {
			while (len < 11) {
				s = "0" + s;
			}
		}
		return s.substring(0, 3) + "." + s.substring(3, 6) + "." + s.substring(6, 9) + "-" + s.substring(9);
	}

	static formatParcial(cpf) {
		cpf = StringExtraiNumeros.exec(cpf);
		if (cpf.length >= 11) {
			return UCpf.format(cpf);
		}
		if (cpf.length < 4) {
			return cpf;
		}
		let s = cpf.substring(0, 3) + ".";
		cpf = cpf.substring(3);

		if (cpf.length < 4) {
			return s + cpf;
		}
		s += cpf.substring(0, 3) + ".";
		cpf = cpf.substring(3);

		if (cpf.length < 4) {
			return s + cpf;
		}
		s += cpf.substring(0, 3) + "-";
		cpf = cpf.substring(3);
		if (cpf.length > 2) {
			cpf = cpf.substring(0, 2);
		}
		s += cpf;
		return s;

	}

	static isValid(cpf) {

		if (StringEmpty.is(cpf)) {
			return false;
		}

		cpf = StringExtraiNumeros.exec(cpf);

		if (!StringLength.is(cpf, 11)) {
			return false;
		}

		for (let i = 0; i < 10; i++) {
			let s = i + "";
			s = StringReplace.exec(cpf, s, "");
			if (StringEmpty.is(s)) {
				return false;
			}
		}

		let numDig = cpf.substring(0, 9);
		let digitoInformado = cpf.substring(9, 11);
		let digitoCalculado = UCpf.calculaDigitoVerificador(numDig);

		if (!StringCompare.eq(digitoCalculado, digitoInformado)) {
			return false;
		}

		return true;
	}

	static calculaDigitoVerificador(num) {

		let soma = 0;
		let peso = 10;
		for (let i = 0; i < num.length; i++) {
			soma += IntegerParse.toInt(num.substring(i, i + 1)) * peso;
			peso--;
		}

		let primDig = 0;
		let resto = soma % 11;

		if (IntegerCompare.isZero(resto) || IntegerCompare.eq(resto, 1)) {
			primDig = 0;
		} else {
			primDig = IntegerParse.toInt(11 - resto);
		}

		soma = 0;
		peso = 11;

		for (let i = 0; i < num.length; i++) {
			soma += IntegerParse.toInt(num.substring(i, i + 1)) * peso;
			peso--;
		}

		soma += primDig * 2;
		resto = soma % 11;

		let segDig = 0;
		if (IntegerCompare.isZero(resto) || IntegerCompare.eq(resto, 1)) {
			segDig = 0;
		} else {
			segDig = IntegerParse.toInt(11 - resto);
		}
		return "" + primDig + segDig;
	}

	static mock(i) {

		if (i === 0) {
			throw new Error();
		}

		let s = IntegerFormat.zerosEsquerda(i, 9);
		let digito = 0;
		while (!UCpf.isValid(s + IntegerFormat.xx(digito))) {
			digito++;
		}
		return UCpf.format(s + IntegerFormat.xx(digito));

	}

	static countAleatorios = 0;

	static aleatorio() {

		let value = BaseData.now().getTime() + (UCpf.countAleatorios++);

		let s = "" + value;
		s = StringRight.get(s, 9);
		s += UCpf.calculaDigitoVerificador(s);
		return UCpf.format(s);

	}

}
