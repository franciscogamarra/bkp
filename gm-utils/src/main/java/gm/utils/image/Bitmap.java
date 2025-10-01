package gm.utils.image;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.image.BufferedImage;

import javax.imageio.ImageIO;

import gm.utils.comum.SystemPrint;
import gm.utils.exception.NaoImplementadoException;
import gm.utils.exception.UException;
import gm.utils.files.GFile;
import lombok.Getter;
import src.commom.utils.integer.IntegerCompare;

@Getter
public class Bitmap {
	
	private BufferedImage image;
	private GFile file;

	public Bitmap(BufferedImage image) {
		this.image = image;
	}
	public Bitmap(int width, int height) {
		image = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
		
//		image = new BufferedImage(width, height, BufferedImage.TYPE_INT_BGR);
//		this(new BufferedImage(width, height, BufferedImage.TYPE_INT_BGR));
	}

	public Bitmap(GFile file) {
		file.assertExists();
		this.file = file;
		try {
			image = ImageIO.read(file.toFile());
		} catch (Exception e) {
			throw UException.runtime(e);
		}
	}
	
	public Bitmap(String caminho) {
		this(GFile.get(caminho));
	}
	
	public int get(int x, int y) {
		if (invalid(x, y)) {
			return 0;
		}
		return image.getRGB(x, y);
	}
	private boolean invalid(int x, int y) {
		return x < 0 || y < 0 || x >= getWidth() || y >= getHeight();
	}
	public void set(int x, int y, int rgb) {
		image.setRGB(x, y, rgb);
	}
	public void save() {
		this.saveAs(file);
	}
	public void saveAs(GFile caminho) {
//		this.saveAs(caminho, "jpg");
		saveAs(caminho, "png");
	}
	public void saveAs(GFile caminho, String extensao) {
		try {
			ImageIO.write(image, extensao, caminho.toFile());
		} catch (Exception e) {
			throw UException.runtime(e);
		}
	}
	public void removerUltimasLinhasEmBranco() {
		int y = getHeight()-1;
		while (rowIsBranco(y)) {
			y--;
		}
		image = image.getSubimage(0, 0, getWidth(), y);
	}
	public boolean rowIsBranco(int y) {
		for (int x = 0; x < getWidth(); x++) {
			if (!isBranco(x, y)) {
				return false;
			}
		}
		return true;
	}
	private boolean isBranco(int x, int y) {
		return get(x, y) == Color.WHITE.getRGB();
	}
	public int getWidth() {
		return image.getWidth();
	}
	public int getHeight() {
		return image.getHeight();
	}
	public void setWidth(int width) {
		setSize(width, getHeight());
	}
	public void setHeight(int height) {
		setSize(getWidth(), height);
	}
	public void setSize(int width, int height) {
		java.awt.Image img = image.getScaledInstance(width, height, java.awt.Image.SCALE_SMOOTH);
		image = new BufferedImage(width, height, image.getType());
		Graphics2D g2d = image.createGraphics();
		g2d.drawImage(img, 0, 0, null);
		g2d.dispose();
	}
	
	public boolean recorteIs(int x, int y, Bitmap subimage) {
		
		for (int xx = 0; xx <= subimage.getWidth()-1; xx++) {

			for (int yy = 0; yy <= subimage.getHeight()-1; yy++) {
				
				int a = get(x + xx, y + yy);
				int b = subimage.get(xx, yy);

				if (a != b) {
					return false;
				}
				
			}
			
		}
		
		return true;
		
	}
	
	public Point procurar(Bitmap subimage) {
		return procurar(subimage, 0, 0, Integer.MAX_VALUE, Integer.MAX_VALUE, BitmapEstrategiaDeBusca.da_esquerda_para_direita_de_cima_para_baixo);
	}
	
	public Point procurar(Bitmap subimage, int x_minimo, int x_maximo, int y_minimo, int y_maximo, BitmapEstrategiaDeBusca estrategia) {
		
		x_maximo = IntegerCompare.getMenor(getWidth() - subimage.getWidth() + 1, x_maximo);
		y_maximo = IntegerCompare.getMenor(getHeight() - subimage.getHeight() + 1, y_maximo);
		
		if (estrategia == BitmapEstrategiaDeBusca.da_esquerda_para_direita_de_cima_para_baixo) {

			for (int y = y_minimo; y < y_maximo; y++) {
				for (int x = x_minimo; x < x_maximo; x++) {
					if (recorteIs(x, y, subimage)) {
						return new Point(x, y);
					}
				}
			}
			
		} else if (estrategia == BitmapEstrategiaDeBusca.da_direita_para_esquerda_de_cima_para_baixo) {

			for (int y = y_minimo; y < y_maximo; y++) {
				for (int x = x_maximo; x > x_minimo; x--) {
					if (recorteIs(x, y, subimage)) {
						return new Point(x, y);
					}
				}
			}
			
		} else {
			throw new NaoImplementadoException(estrategia.toString());
		}
		
		return null;
		
	}
	
	public Point procurarEm(Bitmap superimage) {
		return superimage.procurar(this);
	}
	
	public static void main(String[] args) {
		Bitmap a = new Bitmap("c:\\dev\\tmp\\a.bmp");
		Bitmap b = new Bitmap("c:\\dev\\tmp\\b.bmp");
		SystemPrint.ln(a.procurar(b));
	}
	
}
