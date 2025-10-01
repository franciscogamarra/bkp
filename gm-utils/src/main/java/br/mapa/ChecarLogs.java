package br.mapa;

import gm.utils.files.GFile;

public class ChecarLogs {

	public static void main(String[] args) {
		exec("C:\\Users\\Master\\Downloads\\comex-api-slave.log.2023-05-30.0");
		exec("C:\\Users\\Master\\Downloads\\comex-api-master.log.2023-05-30.0");
		exec("C:\\Users\\Master\\Downloads\\comex-api-slave.log.2023-05-30-217.0");
		exec("C:\\Users\\Master\\Downloads\\comex-api-slave.log.2023-05-30.0");
	}

	private static void exec(String string) {
		GFile.get(string).load().trimPlus()
		.filter(i -> i.contains("[LECOM] - Registros encontrados na view \"CARGA_LECOM.VW_PRODUTO_COMEX\":") && !i.endsWith("0"))
		.print();
	}
	
}
