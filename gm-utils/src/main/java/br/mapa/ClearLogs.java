package br.mapa;

import gm.utils.files.GFile;

public class ClearLogs {

	public static void main(String[] args) {
		GFile.get("C:\\trash\\MAPA-old\\WebLogic\\user_projects\\domains\\base_domain\\servers\\AdminServer\\logs\\").deleteItens();
	}
	
}