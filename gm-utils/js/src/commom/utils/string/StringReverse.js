import StringSplit from './StringSplit';

export default class StringReverse {

	static get(s) {
		let list = StringSplit.exec(s, "");
		list.inverteOrdem();
		return list.concatStrings(i => i, "");
	}

}
