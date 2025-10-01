package gm.languages.ts.angular.forms;

import gm.utils.comum.Lst;
import gm.utils.lambda.P1;

public class Subscribe<T> {

	private Lst<P1<T>> lst = new Lst<>();
	
	public void subscribe(P1<T> func) {
		lst.add(func);
	}
	
	public void exec(T o) {
		lst.each(func -> func.call(o));
	}
	
}
