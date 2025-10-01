import StringAfterFirst from './StringAfterFirst';
import StringCompare from './StringCompare';
import StringContains from './StringContains';
import StringEmpty from './StringEmpty';
import StringReplace from './StringReplace';
import StringSplit from './StringSplit';

export default class StringLike {

	static is(a, b) {

		if (StringEmpty.is(b)) {
			return true;
		}
		if (StringEmpty.is(a)) {
			return false;
		}

		if (StringCompare.eq(a, b)) {
			return true;
		}

		if (!StringContains.is(b, "%")) {
			return false;
		}

		while (StringContains.is(b, "%%")) {
			b = StringReplace.exec(b, "%%", "%");
		}

		if (StringCompare.eq(b, "%") || StringContains.is(a, b)) {
			return true;
		}

		let itens = StringSplit.exec(b, "%");

		if (b.startsWith("%")) {
			itens.remove(0);
		} else if (!a.startsWith(itens.get(0))) {
			return false;
		}

		if (b.endsWith("%")) {
			itens.removeLast();
		} else if (!a.endsWith(itens.getLast())) {
			return false;
		}

		while (!itens.isEmpty()) {

			let s = itens.remove(0);

			if (!StringContains.is(a, s)) {
				return false;
			}

			a = StringAfterFirst.get(a, s);

		}

		return true;

	}

}
