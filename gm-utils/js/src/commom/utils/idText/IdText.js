import IntegerCompare from '../integer/IntegerCompare';
import Null from '../object/Null';
import ObjJs from '../object/ObjJs';

export default class IdText extends ObjJs {

	constructor(id, text) {
		super();
		this.id = id;
		this.text = text;
	}

	getId() {
		return this.id;
	}

	getText() {
		return this.text;
	}

	eqId(value) {
		return IntegerCompare.eq(this.id, value);
	}

	eq(o) {
		if (Null.is(o)) {
			return false;
		}
		return this.eqId(o.id);
	}

	toJsonImpl() {
		return "{"+IdText.itemInteger("id", this.id)+IdText.itemString("text", this.text)+IdText.itemString("icon", this.icon)+"}";
	}

	static fromJson(s) {
		return JSON.parse(s);
	}

}
IdText.VAZIO = new IdText(0, "");
