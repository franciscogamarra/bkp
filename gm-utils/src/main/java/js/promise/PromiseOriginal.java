package js.promise;

import gm.utils.anotacoes.Ignorar;
import gm.utils.lambda.F0;
import gm.utils.lambda.F1;
import gm.utils.lambda.P0;
import gm.utils.lambda.P1;
import gm.utils.lambda.P2;
import gm.utils.map.MapSO;
import gm.utils.rest.ResponseException;
import js.Js;

@Ignorar
public class PromiseOriginal<T> implements IPromise<T> {

	private F1<T,T> onThen;
	private P1<Response> onCatch;
	private P0 onFinally;

	public PromiseOriginal<T> buffer(boolean value) {
		return this;
	}

	public PromiseOriginal<T> parse(F1<String, Response> func) {
		return this;
	}

	@Override
	public PromiseOriginal<T> then(F1<T,T> func) {
		if (this.onThen == null) {
			this.onThen = func;
			return this;
		}
		throw new RuntimeException("Nao preparado para then em cascata");
	}

	@Override
	public PromiseOriginal<T> catch_(P1<Response> func) {
		if (this.onCatch == null) {
			this.onCatch = func;
			return this;
		}
		throw new RuntimeException("Nao preparado para catch em cascata");
	}

	@Override
	public PromiseOriginal<T> finally_(P0 func) {
		if (this.onFinally == null) {
			this.onFinally = func;
			return this;
		}
		throw new RuntimeException("Nao preparado para finally em cascata");
	}

	public PromiseOriginal(F0<T> func) {
		Js.setTimeout(() -> {
			try {
				T o = func.call();
				if (onThen != null) {
					onThen.call(o);
				}
			} catch (ResponseException e) {
				if (onCatch != null) {
					Response res = new Response();
					res.body = e;
					res.status = e.getStatusCode();
					res.data = e.getBody();
					onCatch.call(res);
				}
			} catch (Exception e) {
				if (onCatch == null) {
					throw new RuntimeException(e);
				}
				Response res = new Response();
				res.body = e;
				res.status = 500;
				res.data = new MapSO().add("message", e.getMessage());
				onCatch.call(res);
			} finally {
				if (onFinally != null) {
					onFinally.call();
				}
			}
		}, 0);
	}

	public PromiseOriginal(P2<P1<Object>, Object> func) {

	}

	public T get() {
		return null;
	}

	public static <TT> PromiseOriginal<TT> resolve(P0 onSuccess) {
		onSuccess.call();
		return null;
	}

}
