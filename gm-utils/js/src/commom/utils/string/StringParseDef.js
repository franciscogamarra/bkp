import StringParse from './StringParse';

export default class StringParseDef {

	static get(o, def) {
		let s = StringParse.get(o);
		return s === null ? def : s;
	}

}
