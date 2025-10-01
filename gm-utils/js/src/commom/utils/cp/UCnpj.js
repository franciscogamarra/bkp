import ArrayLst from '../array/ArrayLst';
import BaseData from '../date/BaseData';
import IntegerParse from '../integer/IntegerParse';
import Null from '../object/Null';
import StringCompare from '../string/StringCompare';
import StringEmpty from '../string/StringEmpty';
import StringExtraiNumeros from '../string/StringExtraiNumeros';
import StringLength from '../string/StringLength';
import StringRight from '../string/StringRight';

export default class UCnpj {

	static isValid(cnpj) {

		let s = UCnpj.calcDigitos(cnpj);

		if (Null.is(s)) {
			return false;
		}
		cnpj = StringExtraiNumeros.exec(cnpj);
		return StringCompare.eq(cnpj, s);

	}

	static calcDigitos(cnpj) {

		if (StringEmpty.is(cnpj)) {
			return null;
		}

		cnpj = StringExtraiNumeros.exec(cnpj);

		if (!StringLength.is(cnpj, 14)) {
			return null;
		}

		let semDigitos = cnpj.substring(0, 12);
		let digito1 = UCnpj.calc1(semDigitos);
		let digito2 = UCnpj.calc1(semDigitos + digito1);
		return semDigitos + digito1 + digito2;

	}

	static garante14caracteres(cnpj) {
		cnpj = StringExtraiNumeros.exec(cnpj);
		if (StringLength.get(cnpj) > 14) {
			cnpj = cnpj.substring(0, 14);
		} else {
			while (!StringLength.is(cnpj, 14)) {
				cnpj = "0" + cnpj;
			}
		}
		return cnpj;
	}

	static countAleatorios = 0;

	static aleatorio() {

		let value = BaseData.now().getTime() + (UCnpj.countAleatorios++);

		let s = "" + value;
		s = StringRight.get(s, 12);

		s = UCnpj.calcDigitos(s+"00");
		return UCnpj.format(s);

	}

	/* 61.495.858/0001-69 */
	static formatParcial(cnpj) {
		cnpj = StringExtraiNumeros.exec(cnpj);
		if (cnpj.length >= 14) {
			return UCnpj.format(cnpj);
		}
		if (cnpj.length < 3) {
			return cnpj;
		}
		let s = cnpj.substring(0, 2) + ".";
		cnpj = cnpj.substring(2);

		if (cnpj.length < 4) {
			return s + cnpj;
		}
		s += cnpj.substring(0, 3) + ".";
		cnpj = cnpj.substring(3);

		if (cnpj.length < 4) {
			return s + cnpj;
		}
		s += cnpj.substring(0, 3) + "/";
		cnpj = cnpj.substring(3);

		if (cnpj.length < 5) {
			return s + cnpj;
		}
		s += cnpj.substring(0, 4) + "-";
		cnpj = cnpj.substring(4);

		if (cnpj.length > 2) {
			cnpj = cnpj.substring(0, 2);
		}
		s += cnpj;
		return s;

	}

	static format(cnpj) {
		cnpj = StringExtraiNumeros.exec(cnpj);
		cnpj = UCnpj.garante14caracteres(cnpj);
		return cnpj.substring(0, 2) + "." + cnpj.substring(2, 5) + "." + cnpj.substring(5, 8) + "/" + cnpj.substring(8,12) + "-" + cnpj.substring(12);
	}

	static isComplete(cnpj) {
		return StringLength.is(StringExtraiNumeros.exec(cnpj), 14);
	}

	static calc1(str) {
		let soma = 0;
		for (let indice = str.length - 1; indice >= 0; indice--) {
			let digito = IntegerParse.toInt(str.substring(indice, indice + 1));
			soma += digito * UCnpj.peso.get(UCnpj.peso.size() - str.length + indice);
		}
		soma = 11 - soma % 11;
		return soma > 9 ? 0 : soma;
	}

}
UCnpj.peso = ArrayLst.build(
	6, 5, 4, 3, 2, 9, 8, 7, 6, 5, 4, 3, 2
);
