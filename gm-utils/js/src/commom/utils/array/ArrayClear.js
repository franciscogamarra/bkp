import ArrayEmpty from './ArrayEmpty';

export default class ArrayClear {
	static exec(o) {
		while (!ArrayEmpty.is0(o)) {
			o.pop();
		}
	}
}
