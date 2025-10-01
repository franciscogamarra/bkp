export default class StringOcorrencias {

	static get(s, substring) {
		let i = s.length;
		s = s.replace(substring, "");
		i -= s.length;
		return i / substring.length;
	}

}
