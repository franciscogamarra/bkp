package gm.utils.comum;

import gm.utils.classes.UClass;
import gm.utils.exception.UException;

public class GetMainClass {
	public static Class<?> get() {
		Lst<StackTraceElement> traces = new Lst<>(UException.runtime("").getStackTrace());
		StackTraceElement last = traces.getLast();
		String className = last.getClassName();
		Class<?> classe = UClass.getClass(className);
		return classe;
	}
}
