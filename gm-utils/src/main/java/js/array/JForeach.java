package js.array;

import gm.utils.comum.Lst;
import gm.utils.lambda.F1;
import gm.utils.lambda.F2;
import gm.utils.string.ListString;
import js.support.console;
import src.commom.utils.array.ArrayEmpty;
import src.commom.utils.object.Null;
import src.commom.utils.string.StringEmpty;
import src.commom.utils.string.StringParse;

/*
 * necessário, pois para mapear a key do objeto eu utilizo o trace
import js.support.console;
 * */
public class JForeach<TT, T> {
	
	private Array<T> array_;
	private F1<T,TT> func_;
	private F2<T,Integer,TT> func2_;
	private F1<T,String> getKey_ = o -> StringParse.get(o);
	private Array<TT> list;
	private Lst<T> copy;
	private int vez;
	private int index;
	private final ListString keys = new ListString();

	public JForeach() {
		console.log("JForeach");
	}

	public Array<TT> getItens() {
		if (ArrayEmpty.is0(this.array_)) {
			return new Array<>();
		}
		keys.clear();
		list = new Array<>();

		copy = array_.list.copy();
		vez = 0;
		index = 0;
		monta();
		return list;
	}

	public boolean monta () {
		/*
		 * atencao!
		 * nao substituir por while e nem colocar ||
		 * pois o trace é utilizado como chave
		 * */

		if ((this.vez == 0) || (this.vez == 1) || (this.vez == 2) || (this.vez == 3)) {
			return this.next();
		}
		if (this.vez == 4) {
			return this.next();
		}
		if (this.vez == 5) {
			return this.next();
		}
		if (this.vez == 6) {
			return this.next();
		}
		if (this.vez == 7) {
			return this.next();
		}
		if (this.vez == 8) {
			return this.next();
		}
		if (this.vez == 9) {
			return this.next();
		}
		if (this.vez == 10) {
			return this.next();
		}
		if (this.vez == 11) {
			return this.next();
		}
		if (this.vez == 12) {
			return this.next();
		}
		if (this.vez == 13) {
			return this.next();
		}
		if (this.vez == 14) {
			return this.next();
		}
		if (this.vez == 15) {
			return this.next();
		}
		if (this.vez == 16) {
			return this.next();
		}
		if (this.vez == 17) {
			return this.next();
		}
		if (this.vez == 18) {
			return this.next();
		}
		if (this.vez == 19) {
			return this.next();
		}
		throw new Error("Colocar mais itens");
	}

	private boolean next() {

		/*
		 * atencao!
		 * nao substituir por while e nem colocar ||
		 * pois o trace é utilizado como chave de identificacao do objeto
		 * */

		if (this.finish() || this.finish() || this.finish() || this.finish()) {
			return true;
		}
		if (this.finish()) {
			return true;
		}
		if (this.finish()) {
			return true;
		}
		if (this.finish()) {
			return true;
		}
		if (this.finish()) {
			return true;
		}
		if (this.finish()) {
			return true;
		}
		if (this.finish()) {
			return true;
		}
		if (this.finish()) {
			return true;
		}
		if (this.finish()) {
			return true;
		}
		if (this.finish()) {
			return true;
		}
		if (this.finish()) {
			return true;
		}
		if (this.finish()) {
			return true;
		}
		if (this.finish()) {
			return true;
		}
		if (this.finish()) {
			return true;
		}
		if (this.finish()) {
			return true;
		}
		if (this.finish()) {
			return true;
		}
		if (this.finish()) {
			return true;
		}
		if (this.finish()) {
			return true;
		}
		if (this.finish()) {
			return true;
		}
		if (this.finish()) {
			return true;
		}
		if (this.finish()) {
			return true;
		}
		if (this.finish()) {
			return true;
		}
		if (this.finish()) {
			return true;
		}
		if (this.finish()) {
			return true;
		}
		if (this.finish()) {
			return true;
		}
		if (this.finish()) {
			return true;
		}
		if (this.finish()) {
			return true;
		}
		if (this.finish()) {
			return true;
		}
		if (this.finish()) {
			return true;
		}
		if (this.finish()) {
			return true;
		}
		if (this.finish()) {
			return true;
		}
		if (this.finish()) {
			return true;
		}
		if (this.finish()) {
			return true;
		}
		if (this.finish()) {
			return true;
		}
		if (this.finish()) {
			return true;
		}
		if (this.finish()) {
			return true;
		}
		if (this.finish()) {
			return true;
		}
		if (this.finish()) {
			return true;
		}
		if (this.finish()) {
			return true;
		}
		if (this.finish()) {
			return true;
		}
		if (this.finish()) {
			return true;
		}
		if (this.finish()) {
			return true;
		}
		if (this.finish()) {
			return true;
		}
		if (this.finish()) {
			return true;
		}
		if (this.finish()) {
			return true;
		}
		if (this.finish()) {
			return true;
		}
		if (this.finish()) {
			return true;
		}
		if (this.finish()) {
			return true;
		}
		if (this.finish()) {
			return true;
		}
		if (this.finish()) {
			return true;
		}
		if (this.finish()) {
			return true;
		}
		if (this.finish()) {
			return true;
		}
		if (this.finish()) {
			return true;
		}
		if (this.finish()) {
			return true;
		}
		if (this.finish()) {
			return true;
		}
		if (this.finish()) {
			return true;
		}
		if (this.finish()) {
			return true;
		}
		if (this.finish()) {
			return true;
		}
		if (this.finish()) {
			return true;
		}
		if (this.finish()) {
			return true;
		}
		if (this.finish()) {
			return true;
		}
		if (this.finish()) {
			return true;
		}
		if (this.finish()) {
			return true;
		}
		if (this.finish()) {
			return true;
		}
		if (this.finish()) {
			return true;
		}
		if (this.finish()) {
			return true;
		}
		if (this.finish()) {
			return true;
		}
		if (this.finish()) {
			return true;
		}
		if (this.finish()) {
			return true;
		}
		if (this.finish()) {
			return true;
		}
		if (this.finish()) {
			return true;
		}
		if (this.finish()) {
			return true;
		}
		if (this.finish()) {
			return true;
		}
		if (this.finish()) {
			return true;
		}
		if (this.finish()) {
			return true;
		}
		if (this.finish()) {
			return true;
		}
		if (this.finish()) {
			return true;
		}
		if (this.finish()) {
			return true;
		}
		if (this.finish()) {
			return true;
		}
		if (this.finish()) {
			return true;
		}
		if (this.finish()) {
			return true;
		}
		if (this.finish()) {
			return true;
		}
		if (this.finish()) {
			return true;
		}
		if (this.finish()) {
			return true;
		}
		if (this.finish()) {
			return true;
		}
		if (this.finish()) {
			return true;
		}
		if (this.finish()) {
			return true;
		}
		if (this.finish()) {
			return true;
		}
		if (this.finish()) {
			return true;
		}
		if (this.finish()) {
			return true;
		}
		if (this.finish()) {
			return true;
		}
		if (this.finish()) {
			return true;
		}
		if (this.finish()) {
			return true;
		}
		if (this.finish()) {
			return true;
		}
		if (this.finish()) {
			return true;
		}
		if (this.finish()) {
			return true;
		}
		if (this.finish()) {
			return true;
		}
		if (this.finish()) {
			return true;
		}
		if (this.finish()) {
			return true;
		}
		if (this.finish()) {
			return true;
		}
		if (this.finish()) {
			return true;
		}
		if (this.finish()) {
			return true;
		}
		if (this.finish()) {
			return true;
		}
		if (this.finish()) {
			return true;
		}
		if (this.finish()) {
			return true;
		}
		if (this.finish()) {
			return true;
		}
		if (this.finish()) {
			return true;
		}
		if (this.finish()) {
			return true;
		}
		if (this.finish()) {
			return true;
		}
		if (this.finish()) {
			return true;
		}
		if (this.finish()) {
			return true;
		}
		if (this.finish()) {
			return true;
		}
		if (this.finish()) {
			return true;
		}
		if (this.finish()) {
			return true;
		}
		if (this.finish()) {
			return true;
		}
		if (this.finish()) {
			return true;
		}
		if (this.finish()) {
			return true;
		}
		if (this.finish()) {
			return true;
		}
		if (this.finish()) {
			return true;
		}
		if (this.finish()) {
			return true;
		}
		if (this.finish()) {
			return true;
		}
		if (this.finish()) {
			return true;
		}
		if (this.finish()) {
			return true;
		}
		if (this.finish()) {
			return true;
		}
		if (this.finish()) {
			return true;
		}
		if (this.finish()) {
			return true;
		}
		if (this.finish()) {
			return true;
		}
		if (this.finish()) {
			return true;
		}
		if (this.finish()) {
			return true;
		}
		if (this.finish()) {
			return true;
		}
		if (this.finish()) {
			return true;
		}
		if (this.finish()) {
			return true;
		}
		if (this.finish()) {
			return true;
		}
		if (this.finish()) {
			return true;
		}
		if (this.finish()) {
			return true;
		}
		if (this.finish()) {
			return true;
		}
		if (this.finish()) {
			return true;
		}
		if (this.finish()) {
			return true;
		}
		if (this.finish()) {
			return true;
		}
		if (this.finish()) {
			return true;
		}
		if (this.finish()) {
			return true;
		}
		if (this.finish()) {
			return true;
		}
		if (this.finish()) {
			return true;
		}
		if (this.finish()) {
			return true;
		}
		if (this.finish()) {
			return true;
		}
		if (this.finish()) {
			return true;
		}
		if (this.finish()) {
			return true;
		}
		if (this.finish()) {
			return true;
		}
		if (this.finish()) {
			return true;
		}
		if (this.finish()) {
			return true;
		}
		if (this.finish()) {
			return true;
		}
		if (this.finish()) {
			return true;
		}
		if (this.finish()) {
			return true;
		}
		if (this.finish()) {
			return true;
		}
		if (this.finish()) {
			return true;
		}
		if (this.finish()) {
			return true;
		}
		if (this.finish()) {
			return true;
		}
		if (this.finish()) {
			return true;
		}
		if (this.finish()) {
			return true;
		}
		if (this.finish()) {
			return true;
		}
		if (this.finish()) {
			return true;
		}
		if (this.finish()) {
			return true;
		}
		if (this.finish()) {
			return true;
		}
		if (this.finish()) {
			return true;
		}
		if (this.finish()) {
			return true;
		}
		if (this.finish()) {
			return true;
		}
		if (this.finish()) {
			return true;
		}
		if (this.finish()) {
			return true;
		}
		if (this.finish()) {
			return true;
		}
		if (this.finish()) {
			return true;
		}
		if (this.finish()) {
			return true;
		}
		if (this.finish()) {
			return true;
		}
		if (this.finish()) {
			return true;
		}
		if (this.finish()) {
			return true;
		}
		if (this.finish()) {
			return true;
		}
		if (this.finish()) {
			return true;
		}
		if (this.finish()) {
			return true;
		}
		if (this.finish()) {
			return true;
		}
		if (this.finish()) {
			return true;
		}
		if (this.finish()) {
			return true;
		}
		if (this.finish()) {
			return true;
		}
		if (this.finish()) {
			return true;
		}
		if (this.finish()) {
			return true;
		}
		if (this.finish()) {
			return true;
		}
		if (this.finish()) {
			return true;
		}
		if (this.finish()) {
			return true;
		}
		if (this.finish()) {
			return true;
		}
		if (this.finish()) {
			return true;
		}
		if (this.finish()) {
			return true;
		}
		if (this.finish()) {
			return true;
		}
		if (this.finish()) {
			return true;
		}
		if (this.finish()) {
			return true;
		}
		if (this.finish()) {
			return true;
		}
		if (this.finish()) {
			return true;
		}
		if (this.finish()) {
			return true;
		}
		if (this.finish()) {
			return true;
		}
		if (this.finish()) {
			return true;
		}
		if (this.finish()) {
			return true;
		}
		if (this.finish()) {
			return true;
		}
		if (this.finish()) {
			return true;
		}
		if (this.finish()) {
			return true;
		}
		if (this.finish()) {
			return true;
		}
		if (this.finish()) {
			return true;
		}
		if (this.finish()) {
			return true;
		}
		if (this.finish()) {
			return true;
		}
		if (this.finish()) {
			return true;
		}
		if (this.finish()) {
			return true;
		}
		if (this.finish()) {
			return true;
		}
		if (this.finish()) {
			return true;
		}
		if (this.finish()) {
			return true;
		}
		if (this.finish()) {
			return true;
		}
		if (this.finish()) {
			return true;
		}
		if (this.finish()) {
			return true;
		}
		if (this.finish()) {
			return true;
		}
		if (this.finish()) {
			return true;
		}
		if (this.finish()) {
			return true;
		}
		if (this.finish()) {
			return true;
		}
		if (this.finish()) {
			return true;
		}
		if (this.finish()) {
			return true;
		}
		if (this.finish()) {
			return true;
		}
		if (this.finish()) {
			return true;
		}
		if (this.finish()) {
			return true;
		}
		if (this.finish()) {
			return true;
		}
		if (this.finish()) {
			return true;
		}
		if (this.finish()) {
			return true;
		}
		if (this.finish()) {
			return true;
		}
		if (this.finish()) {
			return true;
		}
		if (this.finish()) {
			return true;
		}
		if (this.finish()) {
			return true;
		}
		if (this.finish()) {
			return true;
		}
		if (this.finish()) {
			return true;
		}
		if (this.finish()) {
			return true;
		}
		if (this.finish()) {
			return true;
		}
		if (this.finish()) {
			return true;
		}
		if (this.finish()) {
			return true;
		}
		if (this.finish()) {
			return true;
		}
		if (this.finish()) {
			return true;
		}
		if (this.finish()) {
			return true;
		}
		if (this.finish()) {
			return true;
		}
		if (this.finish()) {
			return true;
		}
		if (this.finish()) {
			return true;
		}
		if (this.finish()) {
			return true;
		}
		if (this.finish()) {
			return true;
		}
		if (this.finish()) {
			return true;
		}
		if (this.finish()) {
			return true;
		}
		if (this.finish()) {
			return true;
		}
		if (this.finish()) {
			return true;
		}
		if (this.finish()) {
			return true;
		}
		if (this.finish()) {
			return true;
		}
		if (this.finish()) {
			return true;
		}
		if (this.finish()) {
			return true;
		}
		if (this.finish()) {
			return true;
		}
		if (this.finish()) {
			return true;
		}
		if (this.finish()) {
			return true;
		}
		if (this.finish()) {
			return true;
		}
		if (this.finish()) {
			return true;
		}
		if (this.finish()) {
			return true;
		}
		if (this.finish()) {
			return true;
		}
		if (this.finish()) {
			return true;
		}
		if (this.finish()) {
			return true;
		}
		if (this.finish()) {
			return true;
		}
		if (this.finish()) {
			return true;
		}
		if (this.finish()) {
			return true;
		}
		if (this.finish()) {
			return true;
		}
		if (this.finish()) {
			return true;
		}
		if (this.finish()) {
			return true;
		}
		if (this.finish()) {
			return true;
		}
		if (this.finish()) {
			return true;
		}
		if (this.finish()) {
			return true;
		}
		if (this.finish()) {
			return true;
		}
		if (this.finish()) {
			return true;
		}
		if (this.finish()) {
			return true;
		}
		if (this.finish()) {
			return true;
		}
		if (this.finish()) {
			return true;
		}
		if (this.finish()) {
			return true;
		}
		if (this.finish()) {
			return true;
		}
		if (this.finish()) {
			return true;
		}
		if (this.finish()) {
			return true;
		}
		if (this.finish()) {
			return true;
		}
		if (this.finish()) {
			return true;
		}
		if (this.finish()) {
			return true;
		}
		if (this.finish()) {
			return true;
		}
		if (this.finish()) {
			return true;
		}
		if (this.finish()) {
			return true;
		}
		if (this.finish()) {
			return true;
		}
		if (this.finish()) {
			return true;
		}
		if (this.finish()) {
			return true;
		}
		if (this.finish()) {
			return true;
		}
		if (this.finish()) {
			return true;
		}
		if (this.finish()) {
			return true;
		}
		if (this.finish()) {
			return true;
		}
		if (this.finish()) {
			return true;
		}
		if (this.finish()) {
			return true;
		}
		if (this.finish()) {
			return true;
		}
		if (this.finish()) {
			return true;
		}
		if (this.finish()) {
			return true;
		}
		if (this.finish()) {
			return true;
		}
		if (this.finish()) {
			return true;
		}
		if (this.finish()) {
			return true;
		}
		if (this.finish()) {
			return true;
		}
		if (this.finish()) {
			return true;
		}
		if (this.finish()) {
			return true;
		}
		if (this.finish()) {
			return true;
		}
		if (this.finish()) {
			return true;
		}
		if (this.finish()) {
			return true;
		}
		if (this.finish()) {
			return true;
		}
		if (this.finish()) {
			return true;
		}
		if (this.finish()) {
			return true;
		}
		if (this.finish()) {
			return true;
		}
		if (this.finish()) {
			return true;
		}
		if (this.finish()) {
			return true;
		}
		if (this.finish()) {
			return true;
		}
		if (this.finish()) {
			return true;
		}
		if (this.finish()) {
			return true;
		}
		if (this.finish()) {
			return true;
		}
		if (this.finish()) {
			return true;
		}
		if (this.finish()) {
			return true;
		}
		if (this.finish()) {
			return true;
		}
		if (this.finish()) {
			return true;
		}
		if (this.finish()) {
			return true;
		}
		if (this.finish()) {
			return true;
		}
		if (this.finish()) {
			return true;
		}
		if (this.finish()) {
			return true;
		}
		if (this.finish()) {
			return true;
		}
		if (this.finish()) {
			return true;
		}
		if (this.finish()) {
			return true;
		}
		if (this.finish()) {
			return true;
		}

		this.vez++;
		return this.monta();

	}

	private boolean finish() {

		T o = copy.remove(0);
		String key = getKey_.call(o);

		if (StringEmpty.is(key)) {
			key = StringParse.get(o);
		}

		if (keys.contains(key)) {
			throw new RuntimeException("KEY REPETIDA >>>> " + key);
		}
		keys.add(key);

		if (Null.is(func_)) {
			list.push(func2_.call(o, index++));
		} else {
			list.push(func_.call(o));
		}

		return this.copy.isEmpty();
	}

}
