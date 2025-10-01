import Box from './Box';
import Equals from '../object/Equals';
import IntegerCompare from '../integer/IntegerCompare';
import IntegerParse from '../integer/IntegerParse';
import Null from '../object/Null';
import StringCompare from '../string/StringCompare';
import StringIs from '../string/StringIs';

export default class JsMap {

	constructor(map) {
		if (Null.is(map)) {
			this.map = new Map();
		} else {
			this.map = map;
		}
	}

	keys() {
		return this.map.keys();
	}

	set(key, value) {
		this.map.set(key, value);
		return this;
	}

	get(key) {
		return this.map.get(key);
	}

	forEach(func) {
		this.map.forEach((v, k) => func(k, v));
	}

	clear() {
		this.map.clear();
	}

	delete(key) {
		this.map.delete(key);
	}

	size() {
		return this.map.size;
	}

	isEmpty() {
		return IntegerCompare.eq(this.size(), 0);
	}

	reduce(func, initial) {
		let box = new Box(initial);
		this.map.forEach((v, k) => box.set(func(k, v, box.get())));
		return box.get();
	}

	asString(separador) {
		if (this.isEmpty()) {
			return "";
		}
		return this.reduce((k, v, atual) => atual + separador + k + "="+v, "").substring(separador.length);
	}

	containsValue(value) {
		if (this.isEmpty()) {
			return false;
		}
		let box = new Box(false);
		if (StringIs.is(value)) {
			let ss = value;
			this.map.forEach((v,k) => {
				let s = v;
				if (StringCompare.eq(s, ss)) {
					box.set(true);
				}
			});
		} else {
			this.map.forEach((v,k) => {
				if (Equals.is(k, value)) {
					box.set(true);
				}
			});
		}
		return box.get();
	}

	getInt(key) {
		return IntegerParse.toInt(this.get(key));
	}

	getIntDef(key, def) {
		let o = this.getInt(key);
		if (Null.is(o)) {
			return def;
		}
		return o;
	}

	toString() {

		if (this.isEmpty()) {
			return "{}";
		}
		return "{"+this.reduce((k, v, atual) => atual + ",\"" + k + "\": \""+v+"\"", "").substring(1)+"}";

	}

	getMap() {
		return this.map;
	}

}
