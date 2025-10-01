package gm.utils.outros;
import gm.utils.files.UFile;
import gm.utils.map.MapSO;
import gm.utils.map.MapSoFromObject;

public class PersistObject {

	public static void save(Object o, String file) {
		MapSO map = MapSoFromObject.get(o);
		map.save(file);
	}

	public static void load(Object o, String file) {
		if (UFile.exists(file)) {
			MapSO map = new MapSO();
			map.loadIfExists(file);
			map.setInto(o, false);
		}
	}

}