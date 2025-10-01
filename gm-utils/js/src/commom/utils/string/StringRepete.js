export default class StringRepete {
	static exec(s, vezes) {
		let result = "";
		while (vezes > 0) {
			result += s;
			vezes--;
		}
		return result;
	}
}
