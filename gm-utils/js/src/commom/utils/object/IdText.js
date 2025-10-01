import IntegerCompare from '../integer/IntegerCompare';
import Null from './Null';
import ObjJs from './ObjJs';

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
		} else {
			return this.eqId(o.id);
		}
	}

	toJsonImpl() {
		return "{"+IdText.itemInteger("id", this.id)+IdText.itemString("text", this.text)+IdText.itemString("icon", this.icon)+"}";
	}

}
IdText.VAZIO = new IdText(0, "");
