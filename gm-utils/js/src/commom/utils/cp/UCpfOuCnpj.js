import UCnpj from './UCnpj';
import UCpf from './UCpf';

export default class UCpfOuCnpj {

	static format(s) {
		if (UCpf.isValid(s)) {
			return UCpf.format(s);
		}
		if (UCnpj.isValid(s)) {
			return UCnpj.format(s);
		} else {
			return s;
		}
	}

	static isValid(s) {
		return UCpf.isValid(s) || UCnpj.isValid(s);
	}

}
