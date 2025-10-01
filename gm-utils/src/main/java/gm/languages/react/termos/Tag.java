package gm.languages.react.termos;

import java.util.HashMap;
import java.util.Map;

public class Tag {
	
	private static final Map<String, Tag> map = new HashMap<>();
	
	private final String s;

	private Tag(String s) {
		this.s = s;
	}
	
	public static Tag get(String s) {
		
		Tag o = map.get(s);
		
		if (o == null) {
			o = new Tag(s);
			map.put(s, o);
		}
		
		return o;
		
	}
	
	@Override
	public String toString() {
		return s;
	}
	
	public static Tag frag() {
		return get("Fragment");
	}
	
	public static boolean contains(String key) {
		return map.containsKey(key);
	}

}
