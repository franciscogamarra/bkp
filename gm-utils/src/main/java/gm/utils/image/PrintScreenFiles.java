package gm.utils.image;

import gm.utils.comum.Lst;
import gm.utils.files.GFile;

public class PrintScreenFiles {

	private final Lst<PrintScreenFile> files = new Lst<>();
	
	private Integer x;
	private Integer y;
	
	public PrintScreenFile last;

	public PrintScreenFiles(GFile path, String name) {
		
		int i = 0;
		
		while (true) {
			
			GFile file = path.join(name + "_"+i+".bmp");
			
			if (!file.exists()) {
				break;
			}
			
			files.add(new PrintScreenFile(file));
			
			i++;
				
		}
		
	}
	
	public PrintScreenFiles(GFile path, String name, int x, int y) {
		this(path, name);
		this.x = x;
		this.y = y;
	}
	
	public PrintScreenFiles setXMaximo(int x) {
		files.each(i -> i.x_maximo = x);
		return this;
	}

	public PrintScreenFiles setYMaximo(int x) {
		files.each(i -> i.y_maximo = x);
		return this;
	}

	public PrintScreenFiles setXMinimo(int x) {
		files.each(i -> i.x_minimo = x);
		return this;
	}

	public PrintScreenFiles setYMinimo(int x) {
		files.each(i -> i.y_minimo = x);
		return this;
	}

	public boolean findAndClick(int tentativas) {
		
		for (int i = 0; i < tentativas; i++) {

			for (PrintScreenFile o : files) {
				if (o.findAndClick(1)) {
					last = o;
					return true;
				}
			}
			
		}
		
		return false;
		
	}

	public boolean find() {
		
		if (x != null && y != null) {

			for (PrintScreenFile o : files) {
				
				o.setX(x);
				o.setY(y);
				
				if (o.is()) {
					last = o;
					return true;
				}
			}
			
			return false;
			
		}

		for (PrintScreenFile o : files) {
			if (o.find()) {
				last = o;
				return true;
			}
		}
		
		return false;
		
	}

	public boolean moveMouseToCenter() {
		
		if (find()) {
			last.moveMouseToCenter();
			return true;
		}
		
		return false;
		
	}

	public void click() {

		if (find()) {
			last.click();
			return;
		}

		throw new RuntimeException("nao encontrado " + this);
		
	}
	
	@Override
	public String toString() {
		return files.get(0).toString();
	}

	public boolean is() {
		return find();
	}
	
}
