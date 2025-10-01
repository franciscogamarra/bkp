package js.promise;

import gm.utils.comum.Lst;
import js.Js;
import js.array.Array;

public class PromiseAll extends Promise<Array<?>> {

	PromiseAll(Lst<Promise<?>> promises) {
		super(new Lst<>());
		Js.setTimeout(() -> observarLista(promises));
	}

	private void observarLista(Lst<Promise<?>> promises) {

		if (promises.isEmpty()) {
			resolvePrivate(new Array<>());
			return;
		}

		Lst<Promise<?>> rejecteds = promises.filter(o -> o.status == PromiseStatus.rejected);

		if (!rejecteds.isEmpty()) {
			rejectPrivate(rejecteds.get(0).motivoRejeicao);
			return;
		}

		if (promises.anyMatch(o -> o.status == PromiseStatus.pending)) {
			Js.setTimeout(() -> observarLista(promises));
			return;
		}

		Array<Object> array = new Array<>();

		for (Promise<?> o : promises) {
			array.push(o.value);
		}

		resolvePrivate(array);

	}

}
