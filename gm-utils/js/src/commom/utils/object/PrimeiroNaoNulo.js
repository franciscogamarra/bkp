import Null from './Null';

export default class PrimeiroNaoNulo {

	static get(... calls) {
		for (ft : calls) {
			let o = ft();
			if (!Null.is(o)) {
				return o;
			}
		}
		return null;
	}

}
