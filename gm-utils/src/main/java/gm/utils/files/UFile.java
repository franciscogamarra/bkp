package gm.utils.files;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.function.Predicate;

import org.apache.commons.io.FileUtils;

import gm.utils.classes.UClass;
import gm.utils.comum.Lst;
import gm.utils.comum.SO;
import gm.utils.comum.UConstantes;
import gm.utils.comum.UList;
import gm.utils.date.Data;
import gm.utils.exception.UException;
import gm.utils.string.ListString;
import js.support.console;
import src.commom.utils.string.StringBeforeFirst;
import src.commom.utils.string.StringCompare;
import src.commom.utils.string.StringContains;

public class UFile {

	public static boolean desprezar_target = true;
	public static boolean desprezar_node_modules = true;
	public static boolean desprezar_os_que_comecam_com_ponto = true;

	public static void assertExists(String s) {
		assertExists(newFile(s));
	}

	public static void assertExists(File file) {
		if (!exists(file)) {
			throw UException.runtime("Arquivo n" + UConstantes.a_til + "o existe: " + file);
		}
	}

	public static boolean exists(String s) {
		return exists(newFile(s));
	}

	public static boolean exists(File file) {
		return file.exists();
	}

	public static Data data(Class<?> classe) {
		return data(UClass.getJavaFileName(classe, true));
	}

	public static Data data(String s) {
		assertExists(s);
		return new Data(newFile(s).lastModified());
	}

//	public static StreamingOutput streamOutput(String fileName) {
//		File file = newFile(fileName);
//		return streamOutput(file);
//	}
//
//	public static StreamingOutput streamOutput(File file) {
//
//		try {
//
//			StreamingOutput streamOutput = new StreamingOutput() {
//
//				@Override
//				public void write(OutputStream output) throws IOException, WebApplicationException {
//					BufferedOutputStream bus = new BufferedOutputStream(output);
//
//					try {
//		                FileInputStream fizip = newFileInputStream(file);
//		                byte[] buffer2 = IOUtils.toByteArray(fizip);
//		                bus.write(buffer2);
//		            } catch (Exception e) {
//		            	UException.printTrace(e);
//		            }
//				}
//			};
//
//			return streamOutput;
//
//		} catch (Exception e) {
//			throw UException.runtime(e);
//		}
//
//	}

	public static boolean delete(String fileName) {
		
		fileName = tratarPath(fileName);

		if (SO.windows() && fileName.startsWith("/")) {
			fileName = fileName.substring(1);
		}

		if (!exists(fileName)) {
			return false;
		}
		while (exists(fileName)) {
			try {
				File file = newFile(fileName);
				if (file.isDirectory()) {
					delete(getFilesAndDirectories(file));
				}
				Path path = Paths.get(fileName);
				Files.deleteIfExists(path);
			} catch (Exception e) {
				throw UException.runtime(e);
			}
		}

		console.log("delete " + fileName);
		return true;

	}

	public static void delete(List<File> files) {
		for (File file : files) {
			try {
				
				if (file.isDirectory()) {
					delete(file.listFiles());
				}
				
				delete(file);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	public static void delete(File file) {
		delete(file.getAbsolutePath());
		file.delete();
		console.log(file + " (delete)");
	}

	public static void save(File file, String fileName) {
		try {
			save(new FileInputStream(file), fileName);
		} catch (Exception e) {
			throw UException.runtime(e);
		}
	}

	public static void save(InputStream is, String fileName) {

		try {
			
			fileName = tratarPath(fileName);

			String path = StringBeforeFirst.get(fileName, "/");

//			file.exists();
			final File directory = newFile(path);
			if (!directory.exists()) {
				directory.mkdirs();
			}
			File destinationFile = newFile(path, fileName);
//			destinationFile.getAbsolutePath();
			FileOutputStream fos = new FileOutputStream(destinationFile, false);
			copyInputStreamToOutputStream(is, fos);
			
		} catch (Exception e) {
			throw UException.runtime(e);
		}
		
	}

	private static void copyInputStreamToOutputStream(InputStream src, OutputStream dest) {
		
		try {

			if (!(dest instanceof BufferedOutputStream)) {
				dest = new BufferedOutputStream(dest);
			}

			if (!(src instanceof BufferedInputStream)) {
				src = new BufferedInputStream(src);
			}

			int countBytesRead = -1;
			byte[] bufferCopy = new byte[2048];
			while ((countBytesRead = src.read(bufferCopy)) != -1) {
				dest.write(bufferCopy, 0, countBytesRead);
			}

			dest.flush();
			dest.close();
			
		} catch (Exception e) {
			throw UException.runtime(e);
		}
		
	}

	public static void copy(File origem, String destino) {
		copy(origem, newFile(destino));
	}

	public static void copy(File origem, File destino) {

		try {

			if (origem.isDirectory()) {

				if (!destino.exists()) {
					destino.mkdir();
				}

				String[] filhos = origem.list();

				if (filhos == null) {
					console.log(origem);
				} else {
					for (String filho : filhos) {
						copy(newFile(origem, filho), newFile(destino, filho));
					}
				}

			} else {

				boolean exists = destino.exists();

				if (exists) {
					
					if (origem.lastModified() == destino.lastModified() && origem.getTotalSpace() == destino.getTotalSpace()) {
						return;
					}
					
					delete(destino);
					
				}
				
				try {
					
					//Tenta primeiro com a NIO
					Path o = origem.toPath();
					Path d = destino.toPath();
					Files.copy(o, d);
					
				} catch (Exception e) {

					//Não dando certo tenta da forma antiga e mais lenta
					
					new ListString().save(destino);

					try (InputStream in = new FileInputStream(origem); OutputStream out = new FileOutputStream(destino)) {

						byte[] buffer = new byte[1024];
						int len;
						while ((len = in.read(buffer)) > 0) {
							out.write(buffer, 0, len);
						}
					}
					
				}
				
				destino.setLastModified(origem.lastModified());

				if (exists) {
					console.log("Gravando: " + destino + " (replace)");
				}

			}

		} catch (Exception e) {
			throw UException.runtime(e);
		}

	}

	public static void deleteFilesOfPath(String path) {
		deleteFilesOfPath(newFile(path));
	}

	public static void deleteFilesOfPath(File path) {
		if (exists(path)) {
			delete(getFilesAndDirectories(path));
		}
	}

	public static void delete(File... files) {
		if (files == null || files.length == 0) {
			return;
		}
		delete(new Lst<>(files));
	}
	
//	private static final UFileFilter FilterDefault = new UFileFilter();
	
	public static Lst<File> getFilesAndDirectories(File file, UFileFilter filter) {
		
		if (!file.exists()) {
			throw UException.runtime("!exists:" + file);
		}
		
		if (!file.isDirectory()) {
			throw UException.runtime("!isDirectory:" + file);
		}
		
		Lst<File> lst = new Lst<>(file.listFiles());
		lst.removeIf(i -> filter.deveIgnorar(i));
		return lst;
		
	}

	private static Lst<File> getFilesOrDirectory(File file, boolean directory) {
		Lst<File> list = getFilesAndDirectories(file);
		Lst<File> result = new Lst<>();
		for (File file2 : list) {
			if (file2.isDirectory() == directory) {
				result.add(file2);
			}
		}
		sort(result);
		return result;
	}

	private static void sort(List<File> list) {
		list.sort((a, b) -> {
			if (a.isDirectory() == b.isDirectory()) {
				return StringCompare.compare(a.getName(), b.getName());
			}
			if (a.isDirectory()) {
				return 1;
			}
			return -1;
		});
	}

	public static Lst<File> getFiles(String path, String... extensoes) {
		File file = newFile(path);
		return getFiles(file, extensoes);
	}

	public static Lst<File> getFiles(File path, String... extensoes) {
		Lst<File> files = getFiles(path);
		Lst<File> list = new Lst<>();
		for (String extensao : extensoes) {
			for (File file : files) {
				if (file.getName().endsWith("." + extensao)) {
					list.add(file);
				}
			}
		}
		return list;
	}

	public static Lst<File> getFiles(String path) {
		return getFiles(newFile(path));
	}

	public static Lst<File> getFiles(File file) {
		return getFilesOrDirectory(file, false);
	}

	public static Lst<File> getDirectories(String fileName) {
		return getDirectories(newFile(fileName));
	}

	public static Lst<File> getDirectories(File file) {
		return getFilesOrDirectory(file, true);
	}

	public static void criaDiretorio(String s) {
		newFile(s).mkdirs();
	}

	public static void copy(String de, String para) {

		if (StringCompare.eq(de, para)) {
			throw new RuntimeException("de == para");
		}

		copy(newFile(de), newFile(para));
	}

	private static void getAllFiles(File path, List<File> list) {
		list.addAll(getFiles(path));
		Lst<File> directories = getDirectories(path);
		for (File file : directories) {
			getAllFiles(file, list);
		}
	}

	public static Lst<File> getAllFiles(File path) {
		Lst<File> list = new Lst<>();
		getAllFiles(path, list);
		return list;
	}
	
	private static String toString(File file) {
		return file.toString().replace("\\", "/");
	}

	private static void getAllDirectories(File path, List<File> list) {
		
		list.addAll(getDirectories(path));
		List<File> directories = getDirectories(path);
		
		for (File file : directories) {
			
			String s = toString(file);
			
			if (desprezar_target && StringContains.is(s, "/target")) {
				continue;
			}
			
			if (desprezar_node_modules && StringContains.is(s, "/node_modules")) {
				continue;
			}
			
			getAllDirectories(file, list);
			
		}
		
	}

	public static Lst<File> getAllDirectories(File path) {
		Lst<File> list = new Lst<>();
		getAllDirectories(path, list);
		return list;
	}

	public static Lst<File> getAllDirectories(String path) {
		return getAllDirectories(newFile(path));
	}

	public static Lst<File> getAllFiles(String path) {
		return getAllFiles(newFile(path));
	}

	public static Lst<File> getJavas() {
		String s = tratarPath(System.getProperty("user.dir"));
		return getJavas(s + "/src");
	}

	public static Lst<File> getJavas(String path) {
		return getPorExtensao(path, "java");
	}

	public static Lst<File> getJavaScripts(String path) {
		return getPorExtensao(path, "js");
	}
	
	public static Lst<File> getPorExtensao(String path, String extensao) {
		String s = "." + extensao;
		return UList.filter(getAllFiles(path), o -> o.toString().endsWith(s));
	}
	
	public static Lst<File> getPoms(String path) {
		return getAllFiles(path).filter(o -> o.getName().contentEquals("pom.xml"));
	}

	public static Lst<File> findFilesByName(String path, String name) {
		Lst<File> files = getAllFiles(path);
		Lst<File> list = new Lst<>();
		for (File file : files) {
			if (file.getName().equals(name)) {
				list.add(file);
			}
		}
		return list;
	}

	public static byte[] getBytes(File file) {
		try {
			return FileUtils.readFileToByteArray(file);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public static long size(Path path) {
		try {
			return Files.size(path);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public static void deleteIfContains(String path, String text) {
		deleteIf(path, file -> file.toString().contains(text));
	}

	public static void deleteIfEndsWith(String path, String text) {
		deleteIf(path, file -> file.toString().endsWith(text));
	}

	public static void deleteIf(String path, Predicate<File> predicate) {
		getAllFiles(path).filter(predicate).forEach(file -> {
			delete(file);
		});
	}

	private static void getPastasVazias(File path, Lst<File> files) {

		if (getFilesAndDirectories(path).isEmpty()) {
			files.add(path);
		} else {

			Lst<File> directories = getDirectories(path);

			directories.removeIf(file -> {

				String s = toString(file);

				if (s.contains("/target/") || s.contains("/node_modules/") || s.contains("/.") || s.contains("/META-INF")) {
					return true;
				}

				return false;

			});

			for (File file : directories) {
				getPastasVazias(file, files);
			}

		}

	}

	public static Lst<File> getPastasVazias(File path) {
		Lst<File> files = new Lst<>();
		getPastasVazias(path, files);
		return files;
	}

	public static Lst<File> getPastasVazias(Path path) {
		return getPastasVazias(path.toFile());
	}

	public static Lst<File> getPastasVazias(String path) {
		return getPastasVazias(newFile(path));
	}

	/* excluir e retorna os diretorios que foram excluidos */

	public static void main(String[] args) {
		UFile.getPoms("/opt/desen/commons/sicop").print();
	}

	public static Lst<File> excluirPastasVazias(File path) {
		Lst<File> files = getPastasVazias(path);
		delete(files);
		return files;
	}

	public static Lst<File> excluirPastasVazias(Path path) {
		return excluirPastasVazias(path.toFile());
	}

	public static Lst<File> excluirPastasVazias(String path) {
		return excluirPastasVazias(newFile(path));
	}

	//repetido mesmo, mas deixar para retro-compatibilidade
	public static Lst<File> deleteEmptyDirs(String path) {
		return excluirPastasVazias(path);
	}

	
	// =========================================================
	// defaults
	// =========================================================
	
	public static Lst<File> getFilesAndDirectories(File file) {
		return getFilesAndDirectories(file, new UFileFilter());
	}

	private static File newFile(File path, String nome) {
		return new File(path, nome);
	}
	
	public static String tratarPath(String s) {
		
		s = s.replace("\\", "/");
		
		if (SO.windows()) {
			
			if (s.startsWith("/opt/")) {
				return "c:" + s;
			}
			
			if (s.startsWith("../")) {
				return s.substring(1);
			}
			
		}
		
		return s;
		
	}

	private static File newFile(String s) {
		return new File(tratarPath(s));
	}
	
	private static File newFile(String path, String nome) {
		return new File(tratarPath(path), nome);
	}
	
	
	
	
}
