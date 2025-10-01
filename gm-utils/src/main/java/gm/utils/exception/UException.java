package gm.utils.exception;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import gm.utils.classes.UClass;
import gm.utils.config.UConfig;
import gm.utils.reflection.Atributo;
import gm.utils.reflection.AtributosBuild;
import gm.utils.string.ListString;
import src.commom.utils.string.StringContains;
import src.commom.utils.string.StringEmpty;

public class UException {

	public static Throwable getTarget(Throwable e) {
		Atributo a = getAtributoTarget(e);
		if (a == null) {
			return null;
		}
		return (Throwable) a.get(e);
	}

	private static Atributo getAtributoTarget(Throwable e) {
		return AtributosBuild.get( UClass.getClass(e) ).get("target");
	}

	private static void setTarget(Throwable e, Throwable value) {
		Atributo a = getAtributoTarget(e);
		if (a != null) {
			a.set(e, value);
		}
	}

	public static RuntimeException runtime(String message) {
		RuntimeException e = new RuntimeException(message);
		return (RuntimeException) trata(e);
	}

	public static RuntimeException runtime(Throwable e) {

		if (e instanceof InvocationTargetException) {
			InvocationTargetException x = (InvocationTargetException) e;
			e = x.getTargetException();
		}

		RuntimeException r;
		if (e instanceof RuntimeException) {
			r = (RuntimeException) e;
		} else {
			r = new RuntimeException(e);
		}
		return r;

	}

	public static RuntimeException runtime(String message, Throwable e) {
		return new RuntimeException(message, e);
	}

	public static ListString trace(Throwable e) {
		return new ListString(GetTrace.getList(e));
	}

	public static Throwable trata(Throwable e) {

		Throwable o = trata(e, null);

		try {
			Throwable cause = getSafeCause(o);
			if (cause != null) {
				trata(cause);
			}
		} catch (Exception e2) {}

		return o;

	}

	private static Throwable getSafeCause(Throwable o) {
		Throwable cause = o.getCause();
		if (cause == null || cause.getClass().getName().startsWith("sun.security.")) {
			return null;
		}
		return cause;
	}

	public static Throwable trata(Throwable e, String mensagemExtra) {

		if (e instanceof InvocationTargetException) {
			InvocationTargetException x = (InvocationTargetException) e;
			e = x.getTargetException();
		}

		Throwable original = e;

		ListString messages = new ListString();

		if (mensagemExtra != null) {
			messages.add(mensagemExtra);
		}

		ListString list = trace(e);

		while (!list.isEmpty() && !list.get(0).contains(".java:")) {
			list.remove(0);
		}
		
		list.removeIf(s -> StringContains.is(s, "(Unknown Source)"));

		List<StackTraceElement> traces = new ArrayList<>();

		while (e != null && !list.isEmpty()) {

			if (!StringEmpty.is(e.getMessage())) {
				messages.addIfNotContains(e.getMessage());
			}

			StackTraceElement[] stackTrace = e.getStackTrace();
			List<StackTraceElement> tracesJoin = new ArrayList<>();
			Collections.addAll(tracesJoin, stackTrace);

			Throwable target = getTarget(e);

			if (target != e && target != null) {
				stackTrace = target.getStackTrace();
				Collections.addAll(tracesJoin, stackTrace);
			}

			while (!list.isEmpty()) {
				String s = list.remove(0);

				for (StackTraceElement o : tracesJoin) {
					//					if (o.toString().contains("StringIndexOutOfBoundsException")) {
					//						SystemPrint.ln(o);
					//					}
					if (s.equals(o.toString())) {
						traces.add(o);
						if (list.isEmpty()) {
							break;
						}
						s = list.remove(0);
					}
				}

			}

			e = e.getCause();

		}

		messages.removeEmptys();

		StackTraceElement[] trace = new StackTraceElement[traces.size()+messages.size()];

		int index = 0;

		for (String m : messages) {
			StackTraceElement ste = new StackTraceElement(m, "", "", 0);
			trace[index] = ste;
			index++;
		}

		//		if (traces.isEmpty()) {
		//			ULog.debug("c");
		//		}

		for (int j = 0; j < traces.size(); j++) {
			trace[j+index] = traces.get(j);
		}

		e = original;
		if (e != null) {
			e.setStackTrace(trace);
			setTarget(e, null);
		}
		return e;
	}

	public static void printTrace(Exception e) {
//		trata(e).printStackTrace();
		e.printStackTrace();
	}

	public static RuntimeException business(String message) {
		return runtime(message);
	}

	private static Throwable lastPrinted;

	public static void printTrace(Throwable e){

		if (UConfig.get() == null || !UConfig.get().onLine()) {
			e.printStackTrace();
			return;
		}

		if (lastPrinted == e) {
			return;
		}

		e = trata(e);

		if (!(e instanceof MessageException)) {
			e.printStackTrace();
		}

		lastPrinted = e;

	}

	public static void printTrace(String message) {
		printTrace( runtime(message) );
	}

}
