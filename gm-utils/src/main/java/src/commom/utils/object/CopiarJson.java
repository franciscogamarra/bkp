package src.commom.utils.object;

import js.outros.ObjJson;
import js.support.JSON;

public class CopiarJson {

	public static ObjJson exec(ObjJson o) {
		return JSON.parse(JSON.stringify(o), ObjJson.class);
	}
	
}