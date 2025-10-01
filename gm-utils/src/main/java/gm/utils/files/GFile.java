package gm.utils.files;

import java.io.File;
import java.io.IOException;
import java.math.BigInteger;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.function.Predicate;

import gm.utils.classes.ListClass;
import gm.utils.classes.UClass;
import gm.utils.comum.Lst;
import gm.utils.comum.SO;
import gm.utils.comum.SystemPrint;
import gm.utils.date.Data;
import gm.utils.exception.NaoImplementadoException;
import gm.utils.lambda.F1;
import gm.utils.lambda.P1;
import gm.utils.outros.ThreadList;
import gm.utils.string.ListString;
import js.support.console;
import lombok.Getter;
import src.commom.utils.string.StringAfterFirst;
import src.commom.utils.string.StringAfterLast;
import src.commom.utils.string.StringBeforeLast;
import src.commom.utils.string.StringEmptyPlus;
import src.commom.utils.string.StringSplit;

@Getter
public class GFile {
	
	private final Lst<String> list;
	private boolean rede;
	private boolean barraNoInicio;
	
	private static final Lst<GFile> files = new Lst<>();
	
	public synchronized static GFile get(String s) {
		
		if (StringEmptyPlus.is(s)) {
			throw new NullPointerException("s is empty");
		}
		
		try {

			GFile o = new GFile(s);
			String ss = o.toFile().toString();
			
			GFile oo = files.unique(i -> i.toFile().toString().contentEquals(ss));
			if (oo == null) {
				files.add(o);
				return o;
			}
			return oo;
			
		} catch (Exception e) {
			throw e;
		}
		
	}
	
	
	public static GFile get(File file) {
		return get(file.toString());
	}
	
	private GFile(Lst<String> list) {
		this.list = list;
	}
	

	private GFile(String s) {
		
		if (s.startsWith("\\\\")) {
			if (!SO.windows()) {
				throw new NaoImplementadoException();
			}
			rede = true;
			s = s.substring(2);
		}
		
		s = s.replace("\\", "/");
		list = StringSplit.exec(s, "/").getArray().list;
		
		if (list.get(0).isEmpty()) {

			if (SO.windows()) {
				String ss = list.get(1).toLowerCase();
				if (ss.contentEquals("opt") || ss.contentEquals("tmp")) {
					list.remove(0);
					list.add(0, "c:");
				} else if (ss.contentEquals("c:")) {
					list.remove(0);
				} else {
					throw new NaoImplementadoException();
				}
			} else {
				list.remove(0);
				barraNoInicio = true;
			}
			
		}
		
		list.removeIf(i -> i.isEmpty());
		
		
	}
	
	public boolean exists() {
		return UFile.exists(toStringSafe());
	}
	
	public void assertExists() {
		if (!exists()) {
			throw new RuntimeException("Não existe: " + this);
		}
	}
	
	private String toStringSafe() {
	
		String s = list.joinString("/");
		if (rede) {
			s = "\\\\" + s;
		}
		
		if (barraNoInicio) {
			s = "/" + s;
		}
		
		if (s.startsWith("../")) {
			return GFile.get(System.getProperty("user.dir")).join(s.substring(2)).toStringSafe();
		}
		
		return s;
		
	}

	@Override
	public String toString() {
		String s = toStringSafe();
		
		if (isDirectory()) {
			s += "/";
		}
		
		if (s.startsWith("c:/")) {
			s = s.replace("/", "\\");
		}
		
		return s;
	}
	
	public Lst<GFile> getFilesAndDirectories() {
		
		if (!exists()) {
			return new Lst<>();
		}
		
		File file = toFile();
		return UFile.getFiles(file).concat(UFile.getDirectories(file)).map(i -> get(i));
	}
	
	public Lst<GFile> getDirs() {
		return new Lst<>(toFile().listFiles()).filter(i -> i.isDirectory()).map(i -> get(i));
	}

	public Lst<GFile> getFiles() {
		return new Lst<>(toFile().listFiles()).filter(i -> i.isFile()).map(i -> get(i));
	}
	
	public Lst<GFile> getAllFiles() {
		return UFile.getAllFiles(toFile()).map(i -> get(i));
	}
	
	public boolean isDirectory() {
		
		if (exists()) {
			return toFile().isDirectory();
		}

		String s = getSimpleName();
		
		if (s.startsWith(".")) {
			return true;
		}
		
		if (s.contains(".")) {
			return false;
		}
		
		return true;
		
	}
	
	public boolean isFile() {
		return !isDirectory();
	}

	public String getSimpleName() {
		return list.getLast();
	}
	
	public String getSimpleNameWithoutExtension() {
		String s = getSimpleName();
		if (s.contains(".")) {
			s = StringBeforeLast.get(s, ".");
		}
		return s;
	}
	
	public Lst<GFile> filter(Predicate<GFile> predicate) {
		return getFilesAndDirectories().filter(predicate);
	}
	
	public void delete(Predicate<GFile> predicate) {
		filter(predicate).each(i -> i.delete());
	}

	public boolean delete() {
		
		if (!exists()) {
			return false;
		}
		if (isDirectory()) {
			deleteItens();
		}
		UFile.delete(toFile());
		return true;
		
	}
	
	private void deleteItens(ThreadList threads) {
		
		Lst<GFile> files = new Lst<>(toFile().listFiles()).map(i -> get(i));
		Lst<GFile> dirs = files.filter(i -> i.isDirectory());
		
		threads.exec(() -> {
			files.removeAll(dirs);
			files.each(i -> i.delete());
		});
		
		dirs.each(i -> i.deleteItens(threads));
		
	}
	
	public File toFile() {
		return new File(toStringSafe());
	}
	
	public void deleteItens() {
		
		if (!exists()) {
			return;
		}
		
		if (!isDirectory()) {
			throw new RuntimeException("nao eh um diretorio");
		}
		
		ThreadList threads = new ThreadList();
		deleteItens(threads);
		threads.esperar();
		
	}


	public GFile join(String s) {
		GFile o = new GFile(list.copy().ad(s));
		o = get(o.toFile());
		o.rede = rede;
		return o;
	}

	public void copy(String destino) {
		copy(get(destino));
	}
	
	public void copy(GFile destino) {
		UFile.copy(toFile(), destino.toFile());
	}

	public void setSimpleName(String s) {
		list.removeLast();
		list.add(s);
	}

	public GFile getPath() {
		Lst<String> l = list.copy();
		l.removeLast();
		if (l.isEmpty()) {
			return null;
		}
		GFile o = new GFile(l);
		o = get(o.toFile());
		o.rede = rede;
		return o;
	}

	public void mkdir() {
		if (!exists()) {
			console.log("Criando diretório: " + this);
			toFile().mkdirs();
		}
	}

	public long lastModified() {
		return toFile().lastModified();
	}
	
	public Data lastModifiedData() {
		return new Data(lastModified());
	}
	
	public String getExtensao() {
		if (!isFile()) {
			throw new RuntimeException("Eh um diretorio");
		}
		String s = getSimpleName();
		if (s.contains(".")) {
			return StringAfterLast.get(s, ".").toLowerCase();
		} else {
			return "";
		}
	}

	public boolean isSql() {
		return isExtensao("sql");
	}
	
	public boolean isJava() {
		return isExtensao("java");
	}

	public void moveTo(GFile path) {
		UFile.copy(toFile(), path.join(getSimpleName()).toFile());
		delete();
	}
	
	public Lst<GFile> getOutrosArquivosMesmaPath() {
		Lst<GFile> lst = getPath().getFiles();
		lst.remove(this);
		return lst;
	}

	public void print() {
		SystemPrint.ln(this);
		SystemPrint.ln("exists: " + exists());
		long size = getSize();
        double tamanhoMB = size / (1024.0 * 1024.0);
        double tamanhoGB = size / (1024.0 * 1024.0 * 1024.0);
        System.out.printf("Subpasta: %s%n", list.join("/"));
        System.out.printf("  Tamanho: %d bytes (%.2f MB / %.2f GB)%n", size, tamanhoMB, tamanhoGB);
	}

	public long getSize() {
		try {
			return Files.size(toPath());
		} catch (IOException e) {
			return -1;
		}
	}


	public void create() {
		new ListString().save(this);
	}
	
	public Lst<String> getList() {
		return list.copy();
	}
	
	private boolean endsWithPrivate(ListString itens) {

		if (itens.isEmpty()) {
			return true;
		}
		
		String s = itens.removeLast();
		
		if (!getSimpleName().equalsIgnoreCase(s)) {
			return false;
		}
		
		return getPath().endsWith(itens);
		
	}
	
	public boolean endsWith(ListString itens) {
		return endsWithPrivate(itens.copy());
	}
	
	public boolean endsWith(String... itens) {
		ListString array = ListString.array(itens);
		return endsWith(array);
	}
	
	public boolean startsWith(String s) {
		return startsWith(get(s));
	}
	
	public boolean startsWith(GFile file) {
		
		if (this == file) {
			return true;
		}
		
		if (list.isEmpty() || list.size() == 1) {
			return false;
		}
		
		return getPath().startsWith(file);
		
	}


	public boolean contains(String s) {
		return list.contains(s);
	}

	public GFile ateh(String pasta) {

		if (!contains(pasta)) {
			return null;
		}
		
		if (endsWith(pasta)) {
			GFile path = getPath();
			if (path.contains(pasta)) {
				return path.ateh(pasta);
			}
			return this;
		}
		
		return getPath().ateh(pasta);
		
	}

	public GFile before(String pasta) {
		
		if (!contains(pasta)) {
			return null;
		}
		
		return ateh(pasta).getPath();
		
	}


	public static GFile userDir() {
		return get(System.getProperty("user.dir"));
	}


	public GFile replace(GFile origem, GFile destino) {
		
		if (!startsWith(origem)) {
			throw new RuntimeException();
		}
		
		String s = toString();
		s = s.replace(origem.toString(), destino.toString());
		return get(s);
		
	}

	public void copyOf(GFile origem, GFile destino) {
		copy(replace(origem, destino));
	}

	public boolean isExtensao(String s) {
		if (isDirectory()) {
			return false;
		}
		return s.equalsIgnoreCase(getExtensao());
	}

	public void deleteEmptyDirs() {
		while (!UFile.deleteEmptyDirs(toString()).isEmpty()) {
			
		}
	}
	
	public GFile after(GFile path) {
		String s = StringAfterFirst.get(toString(), path.toString());
		return s == null ? null : get(s);
	}
	
	public ListClass getClasses() {
		ListClass list = new ListClass();
		list.addAll(getAllFiles().filter(i -> i.isJava()).map(i -> i.javaClass()));
		return list;
	}
	
	public String getJavaClassName() {
		String s = toString();
		if (s.contains("src\\main\\java\\")) {
			s = StringAfterFirst.get(s, "src\\main\\java\\");
		} else if (s.contains("src/main/java/")) {
			s = StringAfterFirst.get(s, "src/main/java/");
		}
		s = StringBeforeLast.get(s, ".");
		s = s.replace("/", ".");
		s = s.replace("\\", ".");
		return s;
	}
	
	public Class<?> javaClass() {
		
		String s = getPath().list.joinString(".") + "." + getSimpleNameWithoutExtension();
		
		while (s != null) {
			Class<?> classe = UClass.getClass(s);
			if (classe != null) {
				return classe;
			}
			s = StringAfterFirst.get(s, ".");
		}
		
		return null;
		
	}

	public ListString load() {
		return new ListString().load(this);
	}
	
	/* se existir entao carrega, senão retorna uma lista vazia */
	public ListString loadSafe() {
		if (exists()) {
			return load();
		} else {
			ListString list = new ListString();
			list.setFileName(toString());
			return list;
		}
	}
	
	public Path toPath() {
		return Paths.get(toString());
	}

	public void rename(GFile file) {
		
		file.create();
		
		try {
			Files.move(toPath(), file.toPath(), StandardCopyOption.REPLACE_EXISTING);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}		
	}

	public void moveToTrashIfExists() {
		if (exists()) {
			moveToTrash();
		}
	}

	public GFile moveToTrash() {
		
		if (!exists()) {
			return null;
		}

		if (!SO.windows()) {
			throw new NaoImplementadoException();
		}
		
		if (!"c:".equalsIgnoreCase(list.get(0))) {
			throw new NaoImplementadoException();
		}
		
		Lst<String> lst = list.copy();
		
		lst.add(1, "trash");
		lst.add(2, Data.now().format("[yyyy]-[mm]-[dd]-[hh]-[nn]-[ss]"));

		String s = lst.joinString(SO.barra());
		if (rede) {
			s = "\\\\" + s;
		}
		
		if (barraNoInicio) {
			s = "/" + s;
		}
		
		GFile file = get(s);
		rename(file);
		
		return file;
		
	}

	public GFile replaceExtensao(String to) {
		return getPath().join(getSimpleNameWithoutExtension() + "." + to);
	}

	public byte[] getBytes() {
		try {
			return Files.readAllBytes(toPath());
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}
	
	private static MessageDigest messageDigest;
	
	public String getCheckSum() {
		
		if (messageDigest == null) {
			try {
				messageDigest = MessageDigest.getInstance("MD5");
			} catch (NoSuchAlgorithmException e) {
				throw new RuntimeException(e);
			}
		}
		
		return new BigInteger(1, messageDigest.digest(getBytes())).toString(16);
		
	}
	
	public void eachFile(P1<GFile> func) {
		getDirs().each(i -> i.eachFile(func));
		getFiles().each(i -> func.call(i));
	}

	public void eachFileP(P1<GFile> func) {
		getDirs().eachP(i -> i.eachFile(func));
		getFiles().eachP(i -> func.call(i));
	}
	
	public void eachFileP(P1<GFile> func, F1<GFile, Boolean> readDir) {
		getDirs().eachP(i -> {
			if (readDir.call(i)) {
				i.eachFileP(func, readDir);
			}
		});
		getFiles().eachP(i -> func.call(i));
	}


	public String toClassName() {
		
		if (!"java".contentEquals(getExtensao())) {
			throw new RuntimeException("Não é um java: " + this);
		}
		
		String s = toString();
		s = StringAfterLast.get(s, "/src/main/java/");
		s = StringBeforeLast.get(s, ".");
		s = s.replace("/", ".");
		return s;
	}
	
}
