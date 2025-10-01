package gm.utils.string;

import gm.utils.exception.UException;
import jcifs.smb.NtlmPasswordAuthentication;
import jcifs.smb.SmbFile;

public class ListStringSmb {

	public static ListString load(SmbFile file) {
		return load(file, CharSet.UTF8);
	}

	public static ListString load(SmbFile file, CharSet charSet) {
		try {
			return new ListString().load(file.getInputStream(), charSet);
		} catch (Exception e) {
			throw UException.runtime(e);
		}
	}

	public static ListString load(String file, CharSet charSet) {

		try {
			String domain = System.getProperty("smb-domain");
			String username = System.getProperty("smb-username");
			String password = System.getProperty("smb-password");
			NtlmPasswordAuthentication auth = new NtlmPasswordAuthentication(domain, username, password);
			SmbFile smbFile = new SmbFile(file, auth);
			return load(smbFile, charSet);
		} catch (Exception e) {
			throw UException.runtime(e);
		}

	}

}
