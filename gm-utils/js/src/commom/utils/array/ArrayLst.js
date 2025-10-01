import ArrayEmpty from './ArrayEmpty';
import IntegerCompare from '../integer/IntegerCompare';
import Null from '../object/Null';
import ObjJs from '../object/ObjJs';
import StringBox from '../string/StringBox';
import StringIs from '../string/StringIs';
import StringParse from '../string/StringParse';
import StringReplace from '../string/StringReplace';

export default class ArrayLst extends ObjJs {

	valueArray = [];

	add(o) {
		return this.addi(o, null);
	}

	addi(o, index) {
		if (Null.is(index)) {
			this.valueArray.push(o);
		} else {
			this.valueArray.splice(index, 0, o);
		}
		return this;
	}

	addAll(list) {
		if (!ArrayEmpty.is(list)) {
			list.forEach(o => this.push(o));
		}
		return this;
	}

	addArray(list) {
		list.forEach(i => this.add(i));
		return this;
	}

	filter(predicate) {
		let list = new ArrayLst();
		list.addArray(this.valueArray.filter(predicate));
		return list;
	}

	reduce(func, startValue) {
		return this.valueArray.reduce(func, startValue);
	}

	anyMatch(predicate) {
		return !this.filter(predicate).isEmpty();
	}

	somaInt(func) {
		return this.reduce((o,t) => {
			let x = func(t);
			if (!Null.is(x)) {
				o += x;
			}
			return o;
		}, 0);
	}

	some(predicate) {
		return this.anyMatch(predicate);
	}

	isEmpty() {
		return IntegerCompare.isZero(this.valueArray.length);
	}

	clear() {
		while (!this.isEmpty()) {
			this.removeLast();
		}
		return this;
	}

	removeLast() {
		return this.valueArray.pop();
	}

	removeFirst() {
		return this.valueArray.shift();
	}

	removeIf(predicate) {
		let filter = this.filter(predicate);
		while (!filter.isEmpty()) {
			let o = filter.removeFirst();
			this.removeObj(o);
		}
		return this;
	}

	removeAll(list) {
		return this.removeIf(o => list.contains(o));
	}

	removeObj(o) {
		let index = this.indexOf(o);
		if (index === -1) {
			return false;
		}
		this.remove(index);
		return true;
	}

	remove(index) {
		let o = this.get(index);
		this.valueArray.splice(index, 1);
		return o;
	}

	get(index) {
		return this.valueArray[index];
	}

	indexOf(o) {
		return this.valueArray.indexOf(o);
	}

	concat(other) {
		let va = this.valueArray.length;
		let vb = other.valueArray.length;
		let a = new ArrayLst();
		a.addArray(this.valueArray.concat(other.valueArray));
		let vc = a.size();
		if (IntegerCompare.ne(va+vb, vc)) {
			throw new Error("O concat nao funcionou");
		}
		return a;
	}

	concat2(os) {
		return this.concat(ArrayLst.build(os));
	}

	forEach(action) {
		this.valueArray.forEach(action);
		return this;
	}

	forEachI(action) {
		this.valueArray.forEach(action);
		return this;
	}

	copy() {
		let list = new ArrayLst();
		this.forEach(o => list.add(o));
		return list;
	}

	inverteOrdem() {
		let copy = this.copy();
		this.clear();
		while (!copy.isEmpty()) {
			this.add(copy.removeLast());
		}
	}

	contains(o) {
		return this.indexOf(o) > -1;
	}

	join(separator) {
		return this.valueArray.join(separator);
	}

	size() {
		return this.valueArray.length;
	}

	uniqueObrig(predicate) {
		let o = this.unique(predicate);
		if (Null.is(o)) {
			throw new Error("O filtro não retornou resultados");
		}
		return o;
	}

	unique(predicate) {
		let itens = this.filter(predicate);
		if (itens.isEmpty()) {
			return null;
		}
		if (itens.size() > 1) {
			throw new Error("O filtro retornou + de 1 resultado");
		} else {
			return itens.get(0);
		}
	}

	sort(comparator) {
		this.valueArray.sort(comparator);
		return this;
	}

	pop() {
		return this.removeLast();
	}
	shift() {
		return this.removeFirst();
	}

	toJsonImpl() {

		/* um array nao possui o metodo toJSON em js */
		if (this.isEmpty()) {
			return "[]";
		}
		if (StringIs.is(this.get(0))) {
			return "[" + this.map(ii => "\"" + StringReplace.exec(ii, "\"", "\\\"") + "\"").join(", ") + "]";
		} else {
			return "[" + this.map(i => StringParse.get(i)).join(", ") + "]";
		}

	}

	byId(id,getId) {
		return this.unique(o => IntegerCompare.eq(getId(o), id));
	}

	existsId(id,getId) {
		return !Null.is(this.byId(id, getId));
	}

	push(o) {
		return this.add(o);
	}

	map(func) {
		let array = this.valueArray.map(func);
		let list = new ArrayLst();
		list.addArray(array);
		return list;
	}

	mapi(func) {
		let array = this.valueArray.map(func);
		let list = new ArrayLst();
		list.addArray(array);
		return list;
	}

	addIfNotContains(o) {
		if (Null.is(o) || this.contains(o)) {
			return false;
		}
		this.add(o);
		return true;
	}

	getArray() {
		return this.copy().valueArray;
	}

	getSafe(index) {
		if (this.isEmpty()) {
			return null;
		}
		if (this.size() < index +1) {
			return null;
		} else {
			return this.get(index);
		}
	}

	getLast() {
		return this.get(this.size()-1);
	}

	distinct(func) {
		let list = new ArrayLst();
		this.valueArray.forEach(o => list.addIfNotContains(func(o)));
		return list;
	}

	static build(...array) {
		let lst = new ArrayLst();
		let ar = array;
		ar.forEach(a => lst.add(a));
		return lst;
	}

	concatStrings(func, separador) {
		let box = new StringBox("");
		this.forEach(o => box.add(separador + func(o)));
		return box.get().substring(separador.length);
	}

	befores(o) {

		let index = this.indexOf(o);

		let i = 0;

		let lst = new ArrayLst();

		while (i < index) {
			lst.add(this.get(i));
			i++;
		}

		return lst;

	}

	afters(o) {

		let i = this.indexOf(o) + 1;

		let lst = new ArrayLst();

		while (i < this.size()) {
			lst.add(this.get(i));
			i++;
		}

		return lst;

	}

	after(o) {

		let i = this.indexOfObrig(o) + 1;

		if (i === this.size()) {
			return null;
		}
		return this.get(i);

	}

	before(o) {

		let i = this.indexOfObrig(o) - 1;

		if (i === -1) {
			return null;
		}
		return this.get(i);

	}

	indexOfObrig(o) {

		let i = this.indexOf(o);

		if (i === -1) {
			throw new Error("Objeto nao consta na lista");
		}
		return i;

	}

	addBefore(existente, novo) {
		let i = this.indexOfObrig(existente);
		this.addi(novo, i);
	}

	addAfter(existente, novo) {
		let i = this.indexOfObrig(existente);
		this.addi(novo, i+1);
	}

}
