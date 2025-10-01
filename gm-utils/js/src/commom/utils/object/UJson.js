import Null from './Null';
import StringEmpty from '../string/StringEmpty';

export default class UJson {

	static getKey(key) {
		return "\""+key+"\": ";
	}

	static getNull(key) {
		return UJson.getKey(key) + "null,";
	}

	static itemString(key, value) {

		if (StringEmpty.is(value)) {
			return UJson.getNull(key);
		}
		return UJson.getKey(key) + "\""+value+"\",";

	}

	static itemInteger(key, value) {

		if (Null.is(value)) {
			return UJson.getNull(key);
		}
		return UJson.getKey(key) + value+",";

	}

	static itemLong(key, value) {

		if (Null.is(value)) {
			return UJson.getNull(key);
		}
		return UJson.getKey(key) + value+",";

	}

	static itemBoolean(key, value) {

		if (Null.is(value)) {
			return UJson.getNull(key);
		}
		return UJson.getKey(key) + value+",";

	}
	static itemObj(key, value) {

		if (Null.is(value)) {
			return UJson.getNull(key);
		}
		return UJson.getKey(key) + value.toJSON()+",";

	}

}
