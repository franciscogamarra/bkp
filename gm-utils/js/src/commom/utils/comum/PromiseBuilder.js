export default class PromiseBuilder {

	static ft(func) {

		return new Promise((/* P1<?> */ resolve, /* P1<?> */ reject) => {
			try {
				resolve(func());
			} catch (e) {
				reject(e);
			}
		});
	}

}
