import When from './When';
/*
 * para melhor desempenho utilizar
 * arrowFunctions quando o result
 * só for preparado para este fim
 *  */
export default class Case {

	whens = {};

	when0(expression, result) {
		return this.when(expression, () => result);
	}

	when1(expression, result) {
		return this.when(() => expression, () => result);
	}

	when2(expression, result) {
		return this.when(() => expression, result);
	}

	when(expression, result) {
		this.whens.add(new When(expression, result));
		return this;
	}

	other(result) {
		this.other_ = result;
		return this;
	}

	other0(result) {
		return this.other(() => result);
	}

	end() {
		for (when : this.whens) {
			if (when.e()) {
				return when.r();
			}
		}
		if (this.other_ === null) {
			return null;
		}
		return this.other_();
	}

}
