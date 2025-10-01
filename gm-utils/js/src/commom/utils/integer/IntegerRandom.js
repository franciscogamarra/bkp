export default class IntegerRandom {

	static get(max) {
		return Math.floor(Math.random() * max);
	}

}
