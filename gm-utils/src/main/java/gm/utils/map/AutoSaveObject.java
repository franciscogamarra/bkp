package gm.utils.map;

import gm.utils.comum.USystem;
import gm.utils.files.GFile;
import gm.utils.outros.UThread;
import gm.utils.reflection.Atributos;
import gm.utils.reflection.AtributosBuild;

public final class AutoSaveObject {

	private GFile file;
	private Atributos as;
	private int milisegundosWatcher;
	private Object obj;
	private boolean ativo;

	public AutoSaveObject(GFile file, Object obj, int milisegundosWatcher) {
		this.file = file;
		this.obj = obj;
		this.milisegundosWatcher = milisegundosWatcher;
		as = AtributosBuild.get(obj).removeStatics();
		as.sortByName();
		as.remove("as");
		as.remove("file");
		if (file.exists()) {
			MapSO map = MapSO.load(file);
			map.setInto(obj);
		}
	}

	private void save() {
		
		if (ativo) {
			MapSoFromObject.get(obj).save(file);
			USystem.setTimeout(() -> save(), milisegundosWatcher);
		}
		
	}
	
	public void ativar() {
		ativo = true;
		UThread.exec(() -> save());
	}
	
	public void desativar() {
		ativo = false;
	}
	
	
	
}