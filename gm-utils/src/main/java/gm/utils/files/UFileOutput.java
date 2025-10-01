package gm.utils.files;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;

import javax.ws.rs.core.StreamingOutput;

import gm.utils.exception.UException;

public class UFileOutput {

	public static StreamingOutput streamOutput(String fileName) {
		File file = new File(fileName);
		return streamOutput(file);
	}

	public static StreamingOutput streamOutput(File file) {
		try {

			return output -> {
				BufferedOutputStream bus = new BufferedOutputStream(output);
				try {
					final FileInputStream fizip = new FileInputStream(file);
					final byte[] buffer2 = org.apache.poi.util.IOUtils.toByteArray(fizip);
					bus.write(buffer2);
				} catch (Exception e) {
					throw UException.runtime(e);
				}
			};

		} catch (Exception e) {
			throw UException.runtime(e);
		}
	}

}
