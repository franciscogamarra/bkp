package js.promise;

import gm.utils.anotacoes.Ignorar;
import gm.utils.comum.Lst;
import gm.utils.comum.SystemPrint;
import gm.utils.lambda.F1;
import gm.utils.lambda.P0;
import gm.utils.lambda.P1;
import gm.utils.lambda.P2;
import js.Js;
import js.array.Array;
import src.commom.utils.comum.PromiseBuilder;

@Ignorar
public class Promise<T> {

	public enum PromiseStatus {
		pending, // pendente: Estado inicial, que não foi realizada nem rejeitada.
		fulfilled, // realizada: sucesso na operação.
		rejected// rejeitado: falha na operação.
	}

	PromiseStatus status = PromiseStatus.pending;
//	private P1<T> then;

	private Lst<P1<T>> thens = new Lst<>();
	private Lst<P1<Throwable>> catchs = new Lst<>();

	private final Lst<P0> finallys;
	private final P2<P1<T>, P1<Throwable>> func;
	T value;
	Throwable motivoRejeicao;
	private final RuntimeException origem;

	private Promise(P2<P1<T>, P1<Throwable>> func, Lst<P0> finallys) {
		
		if (func == null) {
			throw new NullPointerException("func == null");
		}
		
		this.func = func;
		this.finallys = finallys;
		origem = new RuntimeException();
		origem.getStackTrace();
		Js.setTimeout(() -> exec());
	}

	public Promise(P2<P1<T>, P1<Throwable>> func) {
		this(func, new Lst<>());
	}

	//utilizado no await do js
	public T get() {
		exec();
		return value;
	}

	Promise(Lst<P0> finallys) {
		this.func = null;
		this.finallys = finallys;
		origem = new RuntimeException();
		origem.getStackTrace();
	}

	private void exec(T o) {
		exec(() -> resolvePrivate(o));
	}

	private void exec() {
		if (func != null) {
			exec(() -> func.call(o -> resolvePrivate(o), error -> rejectPrivate(error)));
		}
	}

	private void exec(P0 funcao) {

		try {
			funcao.call();
		} catch (Throwable e) {
			
			try {
				funcao.call();
			} catch (Exception e2) {
				// TODO: handle exception
			}
			
			
			rejectPrivate(e);
		} finally {
			for (P0 m : finallys) {
				m.call();
			}
		}

	}

	public <X> Promise<X> then(F1<T,X> func) {
		Promise<X> p = new Promise<>(finallys);
		P1<T> f = o -> {
			X x = func.call(o);
			p.exec(x);
		};
		thens.add(f);
		p.catchs = this.catchs;
		return p;
	}

	public Promise<T> catch_(P1<Throwable> func) {
		catchs.add(func);
		return this;
	}

	public Promise<T> finally_(P0 func) {
		finallys.add(func);
		return this;
	}
	
	public static <TT> Promise<TT> resolve(TT o) {
		return PromiseBuilder.ft(() -> o);
	}

	protected Promise<T> resolvePrivate(T o) {
		for (P1<T> f : thens) {
			f.call(o);
		}
		this.value = o;
		status = PromiseStatus.fulfilled;
		return this;
	}

	protected void rejectPrivate(Throwable e) {
		this.motivoRejeicao = e;
		status = PromiseStatus.rejected;
		if (catchs.isEmpty()) {
			SystemPrint.err("========================================================");
			SystemPrint.err("A função foi rejeitada e não tem nehum catch configurado");
			SystemPrint.err("========================================================");
			e.printStackTrace();
		} else {

			e.printStackTrace();

			//atencao, nao usar foreach pois o cathc eh propagado
			while (!catchs.isEmpty()) {
				catchs.remove(0).call(e);
			}

		}

		SystemPrint.err("========================================================");
		origem.printStackTrace();
		SystemPrint.err("========================================================");
//		throw new RuntimeException(e);

	}

	public static PromiseAll all(Array<Promise<?>> promises) {
		return new PromiseAll(promises.list);
	}

}
