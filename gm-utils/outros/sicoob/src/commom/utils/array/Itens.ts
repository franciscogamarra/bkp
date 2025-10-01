import { F1, P1, Boolean, int, str } from "../comum/OurTypes";
import Null from "../object/Null";

export default class Itens<T> {

	private valueArray : Array<T> = [];

	public add(o : T) : Itens<T> {
		this.valueArray.push(o);
		return this;
	}

	public removeIf(predicate : F1<T,Boolean>) : Itens<T> {
		let filter : Itens<T> = this.filter(predicate);
		while (!filter.isEmpty()) {
			let o : T = filter.removeFirstNotNull();
			this.removeObj(o);
		}
		return this;
	}

	public filter(predicate : F1<T,Boolean>) : Itens<T> {
		let list : Itens<T> = new Itens<T>();
		list.addArray(this.valueArray.filter(predicate));
		return list;
	}

	public addArray(list : Array<T>) : Itens<T> {
		list.forEach((i : T) => this.add(i));
		return this;
	}

	public contains(o : T) : boolean {
		return this.indexOf(o) > -1;
	}

	public anyMatch(predicate : F1<T,Boolean>) : boolean {
		return !this.filter(predicate).isEmpty();
	}

	public isEmpty() : boolean {
		return this.size() === 0;
	}

	public removeFirstNotNull() : T {
		let o : T|null|undefined = this.removeFirst();
		if (o === null) {
			throw new Error("o == null");
		} else {
			return o;
		}
	}

	public removeObj(o : T) : boolean {
		let index : int = this.indexOf(o);
		if (index === -1) {
			return false;
		}
		this.remove(index);
		return true;
	}

	public removeFirst() : T | null {
		return this.valueArray.shift() || null;
	}

	public indexOf(o : T) : int {
		return this.valueArray.indexOf(o);
	}

	public remove(index : int) : T {
		let o : T = this.get(index);
		this.valueArray.splice(index, 1);
		return o;
	}

	public getFirst() : T | null {
		if (this.isEmpty()) {
			return null;
		} else {
			return this.get(0);
		}
	}

	public get(index : int) : T {
		while (index < 0) {
			index = index + this.size();
		}
		return this.valueArray[index];
	}

	public forEach(action : P1<T>) : Itens<T> {
		this.valueArray.forEach(action);
		return this;
	}

	public map<TT>(func : F1<T,TT>) : Itens<TT> {
		let array : Array<TT> = this.valueArray.map(func);
		let list : Itens<TT> = new Itens<TT>();
		list.addArray(array);
		return list;
	}

	public size() : int {
		return this.valueArray.length;
	}

	public getLast() : T {
		return this.get(this.size() - 1);
	}

	public getBefore(o : T) : T {

		if (!this.contains(o)) {
			throw new Error("Não localizado na lista: " + o);
		}

		if (this.get(0) === o) {
			return this.getLast();
		}

		return this.get(this.indexOf(o) - 1);

	}

	public getAfter(o : T) : T {

		if (!this.contains(o)) {
			throw new Error("Não localizado na lista: " + o);
		}

		if (this.getLast() === o) {
			return this.get(0);
		}

		return this.get(this.indexOf(o) + 1);

	}

	public joinString(separator : str) : string {

		if (!separator) {
			separator = "";
		}

		return this.valueArray.join(separator);
	}

	public clone() : Itens<T> {
		let list : Itens<T> = new Itens<T>();
		this.forEach((i : T) => list.add(i));
		return list;
	}

	public concat(outro : Itens<T>) : Itens<T> {
		let list : Itens<T> = this.clone();
		outro.forEach((i : T) => list.add(i));
		return list;
	}

	public getArray() : Array<T> {
		return this.valueArray.concat([]);
	}

}
