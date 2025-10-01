package gm.utils.image;

import java.io.FileInputStream;

import gm.utils.exception.UException;
import gm.utils.files.GFile;

public class Image {

	private GFile file;

	public Image(String caminho) {
		this(GFile.get(caminho));
	}
	
	public Image(GFile file) {
		this.file = file;
	}
	
	private byte[] bytes;

	public byte[] getBytes() {
		
		if (bytes == null) {
			try (FileInputStream fileInputStream = new FileInputStream(file.toFile())) {
				bytes = new byte[fileInputStream.available()];
				fileInputStream.read(bytes);
			} catch (Exception e) {
				throw UException.runtime(e);
			}
		}
		
		return bytes;
	}
	
	public boolean equals(Image image) {
		return equivalente(image, 1);
	}
	
	public boolean equivalente(Image image, int saltos) {
		
		if (this == image) {
			return true;
		}
		
		byte[] bytesa = getBytes();
		byte[] bytesb = image.getBytes();
		
		if (bytesa == bytesb) {
			return true;
		}
		
		if (bytesa.equals(bytesb)) {
			return true;
		}
		
		if (bytesa.length != bytesb.length) {
			return false;
		}

		for (int i = 0; i < bytesa.length; i+= saltos) {
			
			if (bytesa[i] != bytesb[i]) {
				return false;
			}
			
		}
		
		return true;
		
	}

}
