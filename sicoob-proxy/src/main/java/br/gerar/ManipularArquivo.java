package br.gerar;

import br.support.SuperObject;
import br.support.comum.LstString;
import br.support.exceptions.DevException;

public abstract class ManipularArquivo extends SuperObject {

	protected final LstString list = new LstString();
	protected LstString load;
	protected String regionStart;
	
	public final void exec() {
		
		load = Main.load(getFileName());
		
		String regionStart = getRegionStart().trim();
		
		int index = load.getIndexOf(s -> s.trim().equals(regionStart));
		
		if (index == -1) {
			throw DevException.build("nao encontrada regionStart " + regionStart);
		}
		
		this.regionStart = load.get(index);

		index++;

		String regionEnd = getRegionEnd().trim();
		while (!load.get(index).trim().equals(regionEnd)) {
			load.remove(index);
		}

		impl();

		load.addAll(index, list);
		
		Main.save(load);
		
	}
	
	protected abstract void impl();
	public abstract String getFileName();
	public abstract String getRegionStart();	
	public abstract String getRegionEnd();
	
}
