import Equals from "../object/Equals";
import Null from "../object/Null";

export default class IntegerCompare {

	public static eq(a : number, b : number) : boolean {
		if (Equals.is(a, b)) {
			return true;
		}
		if (Null.is(a) || Null.is(b)) {
			return false;
		} else if ((a - b === 0) || Equals.is(a+1, b+1)) {
			return true;
		} else {
			return false;
		}
	}

	public static ne(a : number, b : number) : boolean {
		return !IntegerCompare.eq(a,b);
	}

	public static compare(a : number, b : number) : number {
		if (IntegerCompare.eq(a,b)) {
			return 0;
		}
		if (Null.is(a)) {
			return -1;
		} else if (Null.is(b) || (a >= b)) {
			return 1;
		} else {
			return -1;
		}
	}

	public static isZero(i : number) : boolean {
		return IntegerCompare.eq(i, 0);
	}

	public static maior(a : number, b : number) : boolean {
		return IntegerCompare.eq(IntegerCompare.compare(a, b), 1);
	}

	public static menor(a : number, b : number) : boolean {
		return IntegerCompare.eq(IntegerCompare.compare(a, b), -1);
	}

	public static maiorOuIgual(a : number, b : number) : boolean {
		return IntegerCompare.maior(a, b) || IntegerCompare.eq(a, b);
	}

	public static menorOuIgual(a : number, b : number) : boolean {
		return IntegerCompare.menor(a, b) || IntegerCompare.eq(a, b);
	}

}
