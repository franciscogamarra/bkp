package js.promise;

import gm.utils.lambda.F1;
import gm.utils.lambda.P0;
import gm.utils.lambda.P1;

public interface IPromise<T> {
//	IPromise<T> then(P0 func);
	IPromise<T> then(F1<T,T> func);
	IPromise<T> catch_(P1<Response> func);
	IPromise<T> finally_(P0 func);
}
