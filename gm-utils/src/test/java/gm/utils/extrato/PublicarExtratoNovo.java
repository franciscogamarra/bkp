package gm.utils.extrato;

import gm.utils.files.UFile;

public class PublicarExtratoNovo {

	public static void main(String[] args) {

		UFile.deleteFilesOfPath("/opt/desen/server/jboss-eap-6.4.9.linux/standalone/deployments");
		UFile.copy("/opt/desen/cooperforte/extrato/extrato-ear/target/extrato-ear-0.ear", "/opt/desen/server/jboss-eap-6.4.9.linux/standalone/deployments/extrato-ear-0.ear");
	}

}
