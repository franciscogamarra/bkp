package gm.utils.image;

import java.awt.Point;

import gm.utils.comum.SystemPrint;
import gm.utils.comum.USystem;
import gm.utils.date.Data;
import gm.utils.files.GFile;
import gm.utils.robo.Mouse;
import lombok.Getter;
import lombok.Setter;

@Getter
public class PrintScreenFile {
	
	private static final String TMP_PATH = "c:/tmp/PrintScreenFile/" + Data.now().format("[yyyy]-[mm]-[dd]-[hh]-[nn]-[ss]");
	
	@Setter
	private int x;
	
	@Setter
	private int y;
	
	//Quando maior, mais rapido, porém, mais arriscado. Para imagens muito simples o ideal é que seja menor. 
	public int saltosComparacao = 3;
	
	private final Bitmap bitmap;

	private boolean mouseHover;

//	public PrintScreenFile(String file, int x, int y, int width, int height) {
//		this(GFile.get(file), x, y, width, height);
//	}
	
	public PrintScreenFile(GFile file) {
		this(file, 0, 0);
	}
	
	public PrintScreenFile(GFile file, int x, int y) {
		this.bitmap = new Bitmap(file);
		this.x = x;
		this.y = y;
	}
	
	private void hover() {
		if (mouseHover) {
			moveMouseToCenter();
			USystem.sleepMiliSegundos(499);
		}
	}
	
	public boolean is() {
		Mouse.checaAbort();
		hover();
		GFile tmp = GFile.get(TMP_PATH + getFile().toString().substring(2));
		PrintScreen.exec(tmp, x, y, getWidth(), getHeight());
		return new Image(tmp).equivalente(new Image(getFile()), saltosComparacao);
	}

	private GFile getFile() {
		return bitmap.getFile();
	}
	
	public int getWidth() {
		return bitmap.getWidth();
	}

	public int getHeight() {
		return bitmap.getHeight();
	}

	public void assertt() {
		if (!is()) {
			throw new RuntimeException("diferent");
		}
	}
	
	public void esperar() {
		esperar(20);
	}
	
	public void esperar(int vezes) {
		
		if (is()) {
			return;
		}
		
		Mouse.moveToLimit();//pois pode ser que não ocorreu pq o mouse está em cima do componente e o hover pode mudar a imagem
		
		int vez = 0;
		
		do {

			Mouse.checaAbort();
			USystem.sleepMiliSegundos(198);
			vez++;
			if (vez > vezes) {
				throw new RuntimeException("nao ocorreu");
			}
			
		} while (!is());
		
	}

	public void click() {
		esperar(5);
		Mouse.click(x + getWidth() / 2, y + getHeight() / 2);
		USystem.sleepMiliSegundos(19);
	}
	
	public void doubleClick() {
		esperar(5);
		Mouse.doubleClick(x + getWidth() / 2, y + getHeight() / 2);
	}

	public void moveMouseToCenter() {
		Mouse.move(x + getWidth() / 2, y + getHeight() / 2);
	}

	public PrintScreenFile mouseHover() {
		this.mouseHover = true;
		return this;
	}
	
	public PrintScreenFile setXMinimo(int x) {
		this.x_minimo = x;
		return this;
	}

	public PrintScreenFile setYMinimo(int y) {
		this.y_minimo = y;
		return this;
	}

	public int x_minimo = 0;
	public int x_maximo = Integer.MAX_VALUE;
	public int y_minimo = 0;
	public int y_maximo = Integer.MAX_VALUE;
	public BitmapEstrategiaDeBusca estrategia = BitmapEstrategiaDeBusca.da_esquerda_para_direita_de_cima_para_baixo;
	
	public boolean find() {
		return find(2);
	}
	
	public boolean find(int tentativas) {
		
		for (int i = 0; i < tentativas; i++) {
			
			Point point = PrintScreen.get().procurar(bitmap, x_minimo, x_maximo, y_minimo, y_maximo, estrategia);
			
			if (point == null) {
				USystem.sleepMiliSegundos(19);
				Mouse.checaAbort();
			} else {
				x = point.x;
				y = point.y;
				return true;
			}
			
		}
		
		return false;
		
	}
	
	public boolean findAndClick() {
		return findAndClick(5);
	}
	
	public boolean findAndClick(int tentativas) {
		if (find(tentativas)) {
			Mouse.click(x + getWidth() / 2, y + getHeight() / 2);
			return true;
		}
		return false;
	}
	
	public static void main(String[] args) {
		
		PrintScreenFile o = new PrintScreenFile(GFile.get("c:\\dev\\tmp\\b.bmp"));
		o.find();
		o.moveMouseToCenter();
		
		
	}

	public void print() {
		
		if (find()) {
			moveMouseToCenter();
			SystemPrint.ln("newImage(\""+getFile().getSimpleNameWithoutExtension()+"\", "+x+", "+y+");");
		} else {
			throw new RuntimeException("nao encontrado");
		}
		
	}
	
	@Override
	public String toString() {
		return bitmap.getFile().getSimpleName();
	}
	
}