package gm.utils.temp;

import java.io.IOException;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

import gm.utils.comum.SystemPrint;

public class Temp {

	public static void main(String[] args) throws IOException {
		
		try (JarFile jar = new JarFile("/usr/lib/jvm/java-8-openjdk-amd64/jre/lib/rt.jar");) {
			JarEntry entry = jar.getJarEntry("java/util/Date.class");
			SystemPrint.ln(entry);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}

}
