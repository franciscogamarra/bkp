package gm.utils.comum;

import gm.utils.exception.UException;
import js.support.console;
import src.commom.utils.string.StringParse;

public class ULog {

	public static boolean printPrefixo = false;
	public static boolean debug = true;
	public static boolean warn = true;
	public static boolean info = true;
	public static boolean error = true;
	public static boolean log4j = false;
//
//	private static Logger LOGGER;
//
//	private static Logger getLogger() {
//		if (LOGGER == null) {
//			LOGGER = Logger.getLogger(ULog.class);
//		}
//		return LOGGER;
//	}

	public static void print(String prefixo, Object o){
		if (printPrefixo) {
			console.log("Print "+ prefixo + ": " + o);
		} else {
			console.log(o);
		}

	}

	public static void debug(Object o){
		if (debug) {
			if (log4j) {
//				getLogger().debug("DEBUG: " + o);
			} else {
				print("DEBUG", o);
			}
		}
	}
	public static void warn(Object o) {
		if (warn) {
			if (log4j) {
//				getLogger().warn(o);
			} else {
				print("WARN", o);
			}
		}
	}
	public static void error(Object o) {
		if (error) {
			if (log4j) {
//				getLogger().error(o);
			} else {
				print("ERROR", o);
				UException.printTrace(StringParse.get(o));
			}
		}
	}
	public static void info(Object o) {
		if (info) {
			if (log4j) {
//				getLogger().info("INFO: " + o);
			} else {
				print("INFO", o);
			}
		}
	}
	public static void info() {
		info("");
	}
	public static void debug() {
		debug("");
	}
}
