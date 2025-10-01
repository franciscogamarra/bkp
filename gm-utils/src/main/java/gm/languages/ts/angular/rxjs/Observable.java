package gm.languages.ts.angular.rxjs;

import gm.utils.exception.NaoImplementadoException;
import gm.utils.lambda.P1;

public class Observable<T> {
	
	public Observable<T> pipe() {
		return this;
	}

	public <A> Observable<A> pipe(OperatorFunction<T, A> op1) {
		throw new NaoImplementadoException();
	}

	public void subscribe(P1<T> func) {
		
	}
	
}