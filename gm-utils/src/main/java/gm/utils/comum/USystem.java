package gm.utils.comum;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.util.Enumeration;

import gm.utils.exception.UException;
import gm.utils.lambda.P0;
import gm.utils.lambda.P1;
import src.commom.utils.string.StringBeforeFirst;
import src.commom.utils.string.StringCompare;
import src.commom.utils.string.StringContains;

public class USystem {

	public static void sleepHoras(double horas) {
		USystem.sleepMinutos(horas * 60);
	}
	
	public static void sleepMinutos(double minutos) {
		USystem.sleepSegundos(minutos * 60);
	}
	
	public static void sleepSegundos(double segundos) {
		long d = (long) (segundos * 1000);
		USystem.sleepMiliSegundos(d);
	}
	
//	public static ListString sleepMiliSegundosWatcher;
	public static P1<Long> sleepMiliSegundosWatcher;
	
	public static void sleepMiliSegundos(long milisegundos) {
		try {
			if (sleepMiliSegundosWatcher != null) {
				sleepMiliSegundosWatcher.call(milisegundos);
			}
			Thread.sleep(milisegundos);
		} catch (InterruptedException e) {
			UException.printTrace(e);
		}
	}

	private static String hostName;

	public static boolean hostIs(String s) {
		return StringCompare.eq(USystem.hostName(), s);
	}

	public static String hostName() {
		
		if (USystem.hostName != null) {
			return USystem.hostName;
		}
		
		try {
			USystem.hostName = InetAddress.getLocalHost().getHostName().toLowerCase();
			return USystem.hostName;
		} catch (Exception e) {
			String s = e.getMessage();

			if (StringContains.is(s, ":")) {
				String a = StringBeforeFirst.get(s, ":");
				if (StringContains.is(s, ":")) {
					String b = StringBeforeFirst.get(s, ":");
					if (a.equals(b)) {
						USystem.hostName = a.toLowerCase();
						return USystem.hostName;
					}
				}
			}

			throw UException.runtime(e);
		}
	}

	public static int timeoutsEmExecucao = 0;

	public static void setTimeout(P0 f, int milisegundos) {
		USystem.setTimeout(f, milisegundos, true);
	}

	private static void setTimeout(Thread threadOrigem, P0 f, int milisegundos, boolean count) {

//		long currentTimeMillis = System.currentTimeMillis();

		if (count) {
			USystem.timeoutsEmExecucao++;
		}

		new Thread() {
			@Override
			public void run() {
				try {
					USystem.sleepMiliSegundos(milisegundos);
//					while (System.currentTimeMillis() - currentTimeMillis < milisegundos) {
//
//					}
					if (threadOrigem.isAlive()) {
						USystem.setTimeout(threadOrigem, f, 500, count);
					} else {
						f.call();
					}
				} finally {
					if (count) {
						USystem.timeoutsEmExecucao--;
					}
				}
			}
		}.start();

	}

	public static void setTimeout(P0 f, int milisegundos, boolean count) {
		USystem.setTimeout(Thread.currentThread(), f, milisegundos, count);
	}

	public static String userHome() {
		return System.getProperty("user.home");
	}

	public static boolean hostCooper() {
		return hostName().startsWith("ws00");
	}

	public static boolean hostGamarraCooper() {
		return hostGamarraLinux() || hostGamarraWindowsNote();
	}

	public static boolean hostGamarraLinux() {
		return hostName().startsWith("ws002600");
	}

	public static boolean hostGamarraWindowsNote() {
		return hostName().startsWith("ntl003752");
	}
	
	public static void main(String[] args) {
		SystemPrint.ln(getIp());
		SystemPrint.ln(getIpVpn());
		SystemPrint.ln(getIpPrincipal());
	}

	public static boolean hostMauricioGamarraCooper() {
		return hostName().startsWith("ws002554");
	}

	public static boolean hostCasa() {
		return false;
	}
	
	@Deprecated//chame getIp
	public static String hostIp() {
		return getIp();
	}
	
	public static String getIp() {
		try {
			return InetAddress.getLocalHost().getHostAddress();
		} catch (Exception e) {
			throw UException.runtime(e);
		}
	}
	
	public static String getIpVpn() {
		
		try {
			
			Enumeration<NetworkInterface> networkInterfaces = NetworkInterface.getNetworkInterfaces();
			while (networkInterfaces.hasMoreElements()) {
				NetworkInterface e = networkInterfaces.nextElement();
				SystemPrint.ln(e);
				if (e.getName().startsWith("vpn")) {
					Enumeration<InetAddress> inetAddresses = e.getInetAddresses();
					while (inetAddresses.hasMoreElements()) {
						InetAddress x = inetAddresses.nextElement();
						if (x.getHostAddress().startsWith("10.")) {
							return x.getHostAddress();
						}
					}
				}
			}
			
			return null;
			
		} catch (Exception e) {
			throw UException.runtime(e);
		}
		
	}
	
	public static String getIpPrincipal() {
		String vpn = getIpVpn();
		return vpn == null ? getIp() : vpn;
	}
	
	public static String getJbossHome() {
		
		String s = System.getProperty("jboss.home");
		
		if (s != null) {
			return s;
		}
		
		if (hostGamarraLinux()) {
			return "/opt/desen/server/jboss-eap-6.4.9.linux";
		}
		
		if (hostGamarraWindowsNote()) {
			return "c:/opt/desen/server/jboss-eap-6.4.9";
		}
		
		return null;
		
	}

}