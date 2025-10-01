export default class Tempo {
	static exec(start) {
		let end = Date.now();
		let dif = end - start;
		if (dif > 1000) {
			throw new Error("dif > 1000 : " + dif);
		}
	}
}
