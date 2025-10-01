package br.sicoob.was;

import gm.utils.files.UFile;
import gm.utils.string.ListString;

public class ReplacePomsVersions {

	public static void main(String[] args) {
		
		UFile.getPoms("c:/dev/projects/cre-concessao-bndes-api").each(i ->
			new ListString().load(i).replaceTexto("1.0.27.25", "2022-08-28-a").save(i)
		);
		
	}
	
}