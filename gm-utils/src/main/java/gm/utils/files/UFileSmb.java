package gm.utils.files;

import java.io.File;
import java.io.FileInputStream;

import jcifs.smb.NtlmPasswordAuthentication;
import jcifs.smb.SmbFile;
import jcifs.smb.SmbFileOutputStream;
import src.commom.utils.object.Obrig;

public class UFileSmb {

	public static void copy(File origem, SmbFile destino) {

		try (FileInputStream fis = new FileInputStream(origem); SmbFileOutputStream smbfos = new SmbFileOutputStream(destino);) {
		    final byte[] b  = new byte[16*1024];
		    int read = 0;
		    while ((read=fis.read(b, 0, b.length)) > 0) {
		        smbfos.write(b, 0, read);
		    }
		} catch (Exception e) {
			throw new RuntimeException(e);
		}

	}

	public static SmbFile toSmb(File file) {

		String domain = Obrig.check(System.getProperty("smb-domain"));
		String username = Obrig.check(System.getProperty("smb-username"));
		String password = Obrig.check(System.getProperty("smb-password"));
		NtlmPasswordAuthentication auth = new NtlmPasswordAuthentication(domain, username, password);

		try {
			return new SmbFile(file.toString(), auth);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}

	}

//	private static boolean isSmb(File file) {
//		return file.toString().startsWith("smb:/");
//	}

}
