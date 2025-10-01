package gm.utils.image;

import java.awt.Color;

import gm.utils.comum.SystemPrint;
import gm.utils.files.GFile;

public class PretoEBranco {

	public static void main(String[] args) {
		SystemPrint.ln(350/7);
		
		Bitmap bitmap = new Bitmap("C:\\Users\\Master\\Pictures\\Screenpresso\\2023-03-22_13h08_59.png");
		
		for (int x = 0; x < 50; x++) {
			
			for (int y = 0; y < bitmap.getHeight(); y++) {
				
				int i = bitmap.get(x, y);
				
				Color color = new Color(i, true);
				
				double bla = 0.2126*color.getRed() + 0.7152*color.getGreen() + 0.0722*color.getBlue();
				
				if (bla < 128) {
					bitmap.set(x, y, Color.BLACK.getRGB());
				} else {
					bitmap.set(x, y, Color.WHITE.getRGB());
				}
				
			}
			
		}
		
		bitmap.saveAs(GFile.get("c:\\tmp\\x.bmp"));
		
	}
	
	public static void main0(String[] args) {
		
		Bitmap bitmap = new Bitmap("C:\\Users\\Master\\Pictures\\Screenpresso\\2023-03-22_13h08_59.png");
		
		for (int x = 0; x < bitmap.getWidth(); x++) {
			
			for (int y = 0; y < bitmap.getHeight(); y++) {
				
				int i = bitmap.get(x, y);
				
				Color color = new Color(i, true);
				
				double bla = 0.2126*color.getRed() + 0.7152*color.getGreen() + 0.0722*color.getBlue();
				
				if (bla < 128) {
					bitmap.set(x, y, Color.BLACK.getRGB());
				} else {
					bitmap.set(x, y, Color.WHITE.getRGB());
				}
				
			}
			
		}
		
		bitmap.saveAs(GFile.get("c:\\tmp\\x.bmp"));
	}
	
}