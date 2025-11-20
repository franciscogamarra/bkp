package br.utils;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Stream;

import br.utils.strings.StringCompare;
import br.utils.strings.Substring;
import lombok.Getter;

@Getter
public class GFile {

	private Path path;
	
	private GFile(Path path) {
		this.path = path;
	}
	
	public boolean exists() {
		return Files.exists(path);
	}
	
	public boolean isDirectory() {
		return Files.isDirectory(path);
	}
	
	public boolean isFile() {
		return !isDirectory();
	}
	
	public boolean delete() {
		
		if (exists()) {
			try {
				if (isDirectory()) {
					getItens().forEach(i -> i.delete());
				}
				Files.delete(path);
				Print.ln("deleted " + toString());
				return true;
			} catch (IOException e) {
				throw DevException.build(e);
			}
		} else {
			return false;
		}
		
	}
	
	public Lst<GFile> getItens() {
        if (!exists()) {
            return new Lst<>();
        }
		try (Stream<Path> stream = Files.list(path)) {
			Lst<GFile> lst = new Lst<>();
			stream.forEach(i ->
				lst.add(get(i.toString().replace("\\", "/")))
			);
			return lst;
		} catch (IOException e) {
			throw DevException.build(e);
		}
		
	}
	
	public Lst<GFile> getFiles() {
		return getItens().filter(i -> i.isFile());
	}

	public Lst<GFile> getDirs() {
		return getItens().filter(i -> i.isDirectory());
	}
	
	@Override
	public String toString() {
		return path.toString().replace("\\", "/");
	}

	private static Map<String, GFile> MAP = new HashMap<>();
	
	public static synchronized GFile get(Path path) {
		GFile o = MAP.get(path.toString());
		if (o == null) {
			o = new GFile(path);
			MAP.put(path.toString(), o);
		}
		return o;
	}
	
	public static GFile get(String path) {
		return get(Paths.get(path));
	}

	public String getSimpleName() {
		return Substring.afterLast(toString(), "/");
	}
	
	public String getContent() {
		try {
			return Files.readString(getPath()).replace("\r\n", "\n").replace("\r", "\n");
		} catch (IOException e) {
			throw DevException.build(e);
		}
	}

	public void print() {
		Print.ln("path: " + getPath());
		Print.ln("exists: " + exists());
	}

	public GFile getParent() {
		return GFile.get(getPath().getParent());
	}

	public void assertExists() {
		if (!exists()) {
			throw DevException.build("o diretorio " + this + " não existe");
		}
	}

	public boolean isExtensao(String s) {
		return StringCompare.eq(Substring.afterLast(path.toString(), "."), s);
	}

	public LocalDateTime getModificacao() {
		return Instant.ofEpochMilli(path.toFile().lastModified())
		        .atZone(ZoneId.systemDefault())
		        .toLocalDateTime();
	}
	
	public void copy(String destino) {
		copy(get(destino));
	}
	
	public static void copy(Path origem, Path destino) {
		origem = origem.toAbsolutePath().normalize();
		destino = destino.toAbsolutePath().normalize();
		copy2(origem, destino);
	}
	
	private static void copy2(Path origem, Path destino) {
		
		if (!isValid(destino)) {
			return;
		}
		
		if (origem.equals(destino)) {
			throw DevException.build("origem == destino");
		}

	    try {

	        if (destino.toAbsolutePath().startsWith(origem.toAbsolutePath())) {
	        	throw DevException.build("Destino não pode estar dentro da origem.");
	        }

	        if (Files.isDirectory(origem)) {

	            try (Stream<Path> stream = Files.walk(origem)) {

	                stream.forEach(src -> {
	                	
	                    Path dest = destino.resolve(origem.relativize(src));

	            		if (!isValid(dest)) {
	            			return;
	            		}

	                    try {

	                        if (Files.isDirectory(src)) {
	                            Files.createDirectories(dest);
	                        } else {
	                            copyFinal(src, dest);
	                        }

	                    } catch (Exception e) {
	                    	throw DevException.build(e);
	                    }
	                });

	            }

	        } else {
	            copyFinal(origem, destino);
	        }

	    } catch (Exception e) {
	        throw DevException.build(e);
	    }
	}

	private static boolean isValid(Path o) {
		
		
		String s = o.toString().replace("\\", "/");
		s = s.replace("/./", "/").replace("//", "/");
		
		if (s.contains("/target/")) {
			return false;
		}
		
		if (s.endsWith("/target")) {
			return false;
		}
		
		if (s.contains("/node_modules/")) {
			return false;
		}
		
		if (s.endsWith("/node_modules")) {
			return false;
		}
		
		return true;
		
	}

	private static void copyFinal(Path src, Path dest) {
		try {
			Files.createDirectories(dest.getParent());
			Files.copy(src, dest, StandardCopyOption.REPLACE_EXISTING);
		} catch (Exception e) {
			throw DevException.build(e);
		}
	}

	public void copy(GFile destino) {
		copy(path, destino.path);
	}

}