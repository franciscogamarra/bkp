package gm.utils.exception;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import gm.utils.classes.UClass;
import js.support.console;
import src.commom.utils.string.StringContains;

public class GetTrace {

	private final List<String> list = new ArrayList<>();
	private final List<String> result = new ArrayList<>();
	private final List<String> l = new ArrayList<>();
	private static final List<String> pacotesValidos = new ArrayList<>();
	private static final List<String> pacotesInvalidos = new ArrayList<>();

	public static boolean addPacote(String s) {
		s += ".";
		if (pacotesValidos.contains(s)) {
			return false;
		}
		pacotesValidos.add(s);
		return true;
	}

	public static boolean addPacoteInvalido(String s) {
		s += ".";
		if (pacotesInvalidos.contains(s)) {
			return false;
		}
		pacotesInvalidos.add(s);
		return true;
	}

	public static boolean removePacote(String s) {
		return pacotesValidos.remove(s);
	}

	static {
		addPacote("br");
		addPacote("gm");
		addPacote("app");
		addPacote("src");
		addPacote("html");
		addPacote("react");
		addPacote("reacts");
		addPacoteInvalido("react.outros");
	}

	private GetTrace(Throwable e) {

		Throwable original = e;

		while (e != null) {
			this.add(e);
			Throwable x = e.getCause();
			e = x != e ? x : null;
		}

		e = original;

		while (e != null) {
			this.add(e);
			Throwable x = getTarget(e);
			e = x != e ? x : null;
		}

		for (String s : list) {
			if (!result.contains(s)) {
				result.add(s);
			}
		}

	}

	private static Map<Class<?>, Field> targets = new HashMap<>();
	private static List<Class<?>> classesSemTarget = new ArrayList<>();

	private static Field getFieldTarget(Throwable e) {
		Class<?> classe = e.getClass();
		Field field = targets.get(classe);
		if (field != null) {
			return field;
		}
		if (classesSemTarget.contains(classe)) {
			return null;
		}
		field = findField(classe);
		if (field == null) {
			classesSemTarget.add(classe);
			return null;
		}
		targets.put(classe, field);
		return field;
	}

	private Throwable getTarget(Throwable e) {
		Field field = getFieldTarget(e);
		if (field == null) {
			return null;
		}
		try {
			return (Throwable) field.get(e);
		} catch (Throwable e1) {
			return null;
		}
	}

	private void add(Throwable e) {
		l.clear();
		this.add(e.getClass().getName());
		StackTraceElement[] stack = e.getStackTrace();
		for (StackTraceElement o : stack) {
			try {
				if (!UClass.getClass(o.getClassName()).isAnnotationPresent(NoTrace.class)) {
					this.add(o.toString());
				}
			} catch (Exception e2) {
				this.add(o.toString());
			}
		}
		while (!l.isEmpty()) {
			addList(removeLast());
		}
		addList(e.getMessage());
		addList(e.getClass().getSimpleName());
	}

	private void addList(String s) {
		if (!isEmpty(s)) {
			list.remove(s);
			list.add(0, s);
		}
	}

	private String removeLast() {
		int index = l.size()-1;
		String s = l.get(index);
		l.remove(index);
		return s;
	}

	private static boolean isEmpty(String s) {
		return s == null || s.trim().isEmpty();
	}

	private void add(String s) {
		if (isEmpty(s) || StringContains.is(s, "$$")) {
			return;
		}
		s = s.trim();
		if ("null".equals(s)) {
			return;
		}

		if (exists(s, pacotesValidos) && !exists(s, pacotesInvalidos)) {
			l.add(s);
		}

	}

	private static boolean exists(String s, List<String> pacotes) {

		for (String pacote : pacotes) {
			if (s.startsWith(pacote)) {
				return true;
			}
		}

		return false;

	}

	private static Field findField(Class<?> c){
		while (c != null) {
			Field[] list = c.getDeclaredFields();
			for (Field field : list) {
				if ("target".equals(field.getName())) {
					field.setAccessible(true);
					return field;
				}
			}
			c = c.getSuperclass();
		}
		return null;
	}

	public static List<String> getList(Throwable e) {
		return new GetTrace(e).result;
	}
	public static String getString(Throwable e) {
		List<String> list = getList(e);
		String s = "";
		for (String string : list) {
			s += "\n" + string;
		}
		return s.trim();
	}

	public static void main(String[] args) {
		long inicio = System.currentTimeMillis();
		for (int i = 0; i < 1_000_000; i++) {
			getList(new Exception());
		}
		long fim = System.currentTimeMillis();
		console.log("milisegundos: " + (fim - inicio));
	}

}
