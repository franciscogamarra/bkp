package gm.utils.string;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Predicate;

import gm.utils.abstrato.Lista;
import gm.utils.abstrato.SimpleIdObject;
import gm.utils.classes.UClass;
import gm.utils.comum.Lst;
import gm.utils.comum.SO;
import gm.utils.comum.SystemPrint;
import gm.utils.comum.UAssert;
import gm.utils.comum.UConstantes;
import gm.utils.comum.UHtml;
import gm.utils.comum.ULog;
import gm.utils.exception.UException;
import gm.utils.files.GFile;
import gm.utils.files.UFile;
import gm.utils.lambda.F1;
import gm.utils.lambda.F2;
import gm.utils.lambda.P1;
import gm.utils.lambda.P2;
import gm.utils.number.ListInteger;
import gm.utils.outros.ThreadList;
import gm.utils.reflection.Atributo;
import gm.utils.reflection.Atributos;
import gm.utils.reflection.AtributosBuild;
import js.support.console;
import lombok.Getter;
import lombok.Setter;
import src.commom.utils.integer.IntegerParse;
import src.commom.utils.string.StringAfterFirst;
import src.commom.utils.string.StringBeforeFirst;
import src.commom.utils.string.StringCompare;
import src.commom.utils.string.StringContains;
import src.commom.utils.string.StringEmpty;
import src.commom.utils.string.StringLength;
import src.commom.utils.string.StringParse;
import src.commom.utils.string.StringPrimeiraMaiuscula;
import src.commom.utils.string.StringReplace;
import src.commom.utils.string.StringReplacePalavra;
import src.commom.utils.string.StringRight;
import src.commom.utils.string.StringTrim;

@Getter @Setter
public class ListString extends Lista<String> {

	private CharSet charSet = CharSet.UTF8;
	private boolean checarExistencia = true;
	private boolean printOnAdd = false;
	private boolean autoIdentacao = false;
	private int pularLinhasOnLoad = 0;

	public ListString() {}

	private Margem margem = new Margem();

	public ListString(Object... os) {
		for (Object o : os) {
			add(StringParse.get(o));
		}
	}

	public ListString(String... list) {
		for (String s : list) {
			add(s);
		}
	}
	public ListString(Collection<String> list) {
		addAll(list);
	}

	private static final long serialVersionUID = 1;

	private String fileName = "c:/tmp/x.txt";
	private String saveOnLoad;

	public static ListString newFromArray(String... strings) {
		ListString list = new ListString();
		Collections.addAll(list, strings);
		return list;
	}
	public static ListString array(String... strings) {
		return ListString.newFromArray(strings);
	}
	public static ListString fromFile(File file) {
		return ListString.fromFile(file.toString());
	}
	public static ListString fromFile(String file) {
		ListString list = new ListString();
		list.load(file);
		return list;
	}
	public static ListString fromFileUTF8(String file) {
		ListString list = new ListString();
		list.loadUTF8(file);
		return list;
	}
	public static ListString fromFileISO88591(String file) {
		ListString list = new ListString();
		list.loadISO88591(file);
		return list;
	}
	public static ListString newListString(ListString... lists) {
		ListString list = new ListString();
		for (ListString l : lists) {
			list.add(l);
		}
		return list;
	}
	public static ListString newListString(Enumeration<String> list) {
		ListString result = new ListString();
		while (list.hasMoreElements()) {
			result.add(list.nextElement());
		}
		return result;
	}
	public ListString addArray(String... list) {
		for (String string : list) {
			add(string);
		}
		return this;
	}

	private List<F1<String,String>> filters = new ArrayList<>();

	public void addFilter(F1<String,String> filter) {
		filters.add(filter);
	}

	@Override
	public boolean add(String s) {

		if (StringEmpty.notIs(s)) {

			if (StringContains.is(s, "{\"array\":")) {
				throw new RuntimeException();
			}

			if (StringContains.is(s, "\n") && !s.contentEquals("\n")) {
				ListString split = ListString.split(s, "\n");
				for (String ss : split) {
					add(ss);
				}
				return true;
			}
			
		}

		for (F1<String,String> f : filters) {
			s = f.call(s);
			if (s == null) {
				return false;
			}
		}

		if (autoIdentacao) {
			return addComAutoIdentacao(s);
		}
		return addSemAutoIdentacao(s);

	}

	private boolean addComAutoIdentacao(String s) {

		s = s.trim();

		while (s.endsWith(";;")) {
			s = StringRight.ignore1(s);
		}

		if (s.startsWith("}") || s.startsWith(")") || s.startsWith("]")) {
			while (StringEmpty.is(getLast())) {
				removeLast();
			}
			String last = getLast();
			if (last.endsWith(",")) {
				removeLast();
				last = StringRight.ignore1(last).trim();
				addSemAutoIdentacao(last);
			}
			getMargem().dec();
		}
		boolean result = addSemAutoIdentacao(s);
		if (StringContains.is(s, "//")) {
			s = StringBeforeFirst.get(s, "//");
		}
		if (s.endsWith("{") || s.endsWith("(") || s.endsWith("[")) {
			getMargem().inc();
//		} else if (StringContains.is(s, "(") && !StringAfterLast.get(s, "(").contains(")")) {
//			getMargem().inc();
		}
		return result;
	}

	private boolean addSemAutoIdentacao(String s) {

		s = margem + s;
		if (!isAceitaRepetidos() && contains(s)) {
			return false;
		}
		if (printOnAdd) {
			console.log(s);
		}

		return super.add(s);

	}
	@Override
	public void add(int index, String element) {
		
		if (isEmpty()) {
			add(element);
			return;
		}
		
		while (index < 0) {
			index += size();
		}
		
		super.add(index, margem + element);
	}
	public boolean add(String... list) {
		boolean b = false;
		for (String string : list) {
			b = add(string) || b;
		}
		return b;
	}
	public ListString loadUTF8() {
		loadUTF8("c:\\temp\\x.txt");
		return this;
	}
	public ListString loadISO88591() {
		loadISO88591("c:\\temp\\x.txt");
		return this;
	}
	public ListString load() {
		load(fileName);
		
		return this;
	}
	public ListString loadIfExists() {
		return loadIfExists(fileName);
	}
	public ListString loadUTF8(String file) {
		load(file, CharSet.UTF8);
		return this;
	}
	public ListString loadISO88591(String file) {
		load(file, CharSet.ISO88591);
		return this;
	}
	public ListString loadISO88591(File file) {
		return loadISO88591(file.toString());
	}
	public ListString load(String file) {
		load(file, getCharSet());
		return this;
	}
	public ListString load(GFile file) {
		return load(file.toFile());
	}
	public ListString load(File file) {
		return load(file.toString());
	}
	public ListString load(File file, CharSet charSet) {
		return load(file.toString(), charSet);
	}

	public ListString load(String file, CharSet charSet, F1<String,String> filterAdd) {
		if (filterAdd != null) {
			filters.add(filterAdd);
		}
		ListString list = load(file, charSet);
		filters.remove(filterAdd);
		return list;
	}

	public ListString load(String file, CharSet charSet) {
		
		file = UFile.tratarPath(file);

		if (!UFile.exists(file)) {
			throw new RuntimeException("Arquivo nao encontrado: " + file);
		}
		
		fileName = file;

		try {
			return load(new FileInputStream(file), charSet);
		} catch (Exception e) {
			throw UException.runtime(e);
		}
	}

//	String detectCharSet(InputStream is){
//
//		try {
//			byte[] buf = new byte[4096];
//			UniversalDetector detector = new UniversalDetector(null);
//			int nread;
//			while ((nread = is.read(buf)) > 0 && !detector.isDone()) {
//			  detector.handleData(buf, 0, nread);
//			}
//			detector.dataEnd();
//			String encoding = detector.getDetectedCharset();
//			detector.reset();
//			return encoding;
//
//		} catch (Exception e) {
//			U.printStackTrace(e);
//			return null;
//		}
//	}

	public ListString load(InputStream is, CharSet charSet) {

		try {

			UAssert.notEmpty(is, "is == null");

			setCharSet(charSet);

			InputStreamReader in;

			if ( charSet == null ) {
				in = new InputStreamReader(is);
			} else {
				in = new InputStreamReader(is, charSet.getNome());
			}

			UAssert.notEmpty(in, "in == null");

			BufferedReader buffer = new BufferedReader(in);

			String linha = buffer.readLine();

			if (pularLinhasOnLoad > 0) {

//				buffer.skip(pularLinhasOnLoad);

				int y = 0;
				int x = -1;

				for (int i = 0; i < pularLinhasOnLoad; i++) {

					if (x == 10000) {
						x = 0;
						y += 10000;
					} else {
						x++;
					}

					if (x == 0) {
						console.log("pulando linhas " + y);
					}

					linha = buffer.readLine();

					if (linha == null) {
						console.log("break linha " + y);
						break;
					}

				}

			}

//			console.log("carregando");

//			buffer.skip(pularLinhasOnLoad);

			if (saveOnLoad != null) {

				checarExistencia = false;

				int i = 0;

				while (linha != null) {
					add(linha);
					linha = buffer.readLine();
					i++;
					if (i == 10000) {
						save(saveOnLoad);
						i = 0;
					}
				}

				save(saveOnLoad);

				checarExistencia = true;

			} else {
				while (linha != null) {
					add(linha);
					linha = buffer.readLine();
				}
			}

			buffer.close();
			return this;
		} catch (Exception e) {

			if (e instanceof java.io.UnsupportedEncodingException) {

				if (charSet == null) {
					load(is, CharSet.UTF8);
				} else if (charSet == CharSet.UTF8) {
					load(is, CharSet.ISO88591);
				}
				return this;

			}

			throw UException.runtime(e);

		}
	}
	public boolean has(){
		return !isEmpty();
	}
	public ListString sort_considerando_case() {
		sort((a, b) -> {
			a = StringNormalizer.get(a);
			b = StringNormalizer.get(b);
			return a.compareTo(b);
		});
		return this;
	}
	public void inverteOrdem() {
		ListString list = copy();
		clear();
		for (String s : list) {
			add(0, s);
		}
	}
	public ListString sort() {
		sort((a, b) -> {
			a = StringNormalizer.get(a).toLowerCase();
			b = StringNormalizer.get(b).toLowerCase();
			return a.compareTo(b);
		});
		return this;
	}
	public ListString mergeBefore(String s) {
		ListString x = new ListString();
		for (String string : this) {
			x.add(s + string);
		}
		clear();
		addAll(x);
		return this;
	}
	public ListString mergeAfter(String s) {
		ListString x = new ListString();
		for (String string : this) {
			x.add(string + s);
		}
		clear();
		addAll(x);
		return this;
	}
	public ListString add() {
		add("");
		return this;
	}
	public ListString trataCaracteresEspeciais() {
		return this;
	}
	public ListString replace(int char1, int char2, String s) {
		Character c1 = Character.toChars(195)[0];
		Character c2 = Character.toChars(65533)[0];
		String a = "" + c1 + c2;
		replaceTexto(a, s);
		return this;
	}
	public ListString replace(String a, Object b) {
		replace(a, b.toString());
		return this;
	}
	public ListString replace(String a, String b) {
		if (a.equals(b)) {
			return this;
		}
		while (contains(a)) {
			int i = indexOf(a);
			remove(i);
			add(i, b);
		}
		return this;
	}

	public ListString replaceTexto(String a, String b) {
		boolean safe = autoIdentacao;
		int margemSafe = getMargem().get();
		autoIdentacao = false;
		getMargem().set(0);
		ListString list = mapString(s -> StringReplace.exec(s, a, b));
		clear();
		addAll(list);
		autoIdentacao = safe;
		getMargem().set(margemSafe);
		return this;
	}

	public ListString replacePalavra(String a, String b) {
		ListString list = mapString(s -> StringReplacePalavra.exec(s, a, b));
		clear();
		addAll(list);
		return this;
	}

	public boolean eq(ListString list){
		if (list.size() != size()) {
			return false;
		}
		if (super.equals(list) || list.toString("").equals(toString(""))) {
			return true;
		}
		return false;
	}

	public boolean save(File diretorio, String fileName) {
		return save(diretorio.toString() + "/" + fileName);
	}
	public boolean save() {
		if (StringEmpty.is(fileName)) {
			throw UException.runtime("fileName == null");
		}
		return save(fileName);
	}
	public boolean save(String fileName) {
		return salvar(fileName);
	}
	
	public boolean salvar(String fileName) {
		return save(GFile.get(fileName));
	}
	
	public boolean save(GFile file) {

		if (file.getSimpleNameWithoutExtension().contentEquals("TextAreaBind")) {
			throw new RuntimeException();
		}

		if (file.getSimpleNameWithoutExtension().contentEquals("Botao")) {
			throw new RuntimeException();
		}
		
		if (file.exists()) {

			if (checarExistencia) {
				ListString list = new ListString();
				list.load(file);
				//se for igual nao precisa salvar de novo
				if (list.eq(this)) {
					return false;
				}
			}

			console.log("Gravando: " + file+ " (replace)");

		} else {
			file.getPath().mkdir();
			try {
				file.toFile().createNewFile();
			} catch (Throwable e) {
				throw UException.runtime(e);
			}
			console.log("Gravando: " + file + " (new)");
		}

		if ( file.getSimpleName().contentEquals("null.war") ) {
			throw UException.runtime("Gravando no lugar errado");
		}

		File f = file.toFile();
		
		try {
			try (FileOutputStream fos = new FileOutputStream(f)) {
				if (getCharSet() == null) {
					try (OutputStreamWriter osw = new OutputStreamWriter(fos)) {
						save(osw);
					}
				} else {
					try (OutputStreamWriter osw = new OutputStreamWriter(fos, getCharSet().getNome())) {
						save(osw);
					}
				}
			}
		} catch (IOException e) {
			throw new RuntimeException(e);
		}

		f.setExecutable(true, false);
		f.setReadable(true, false);
		f.setWritable(true, false);

		return true;
	}
	
	private boolean rtrimOnSave = true;

	private void save(OutputStreamWriter osw) {
		try (BufferedWriter out = new BufferedWriter(osw)) {
			for (String s : this) {
				if (s == null) {
					s = "";
				} else if (!s.replace("\t", "").isEmpty() && rtrimOnSave) {
					s = rtrim(s);
				}
				out.write(s + "\n");
			}
		} catch (Exception e) {
			throw UException.runtime(e);
		}
	}

	public static ListString byDelimiter(String s, ListString limits) {

		if (StringEmpty.is(s)) {
			return new ListString();
		}

		String limit = limits.remove(0);

		for (String ss : limits) {
			s = s.replace(ss, limit);
		}

		return byDelimiter(s, limit);

	}

	public static ListString split(String s, String delimiter) {
		return ListString.byDelimiter(s, delimiter);
	}

	public static ListString byQuebra(String s) {
		s = s.replace("\n\r", "\n");
		s = s.replace("\r\n", "\n");
		s = s.replace("\r", "\n");
		return ListString.byDelimiter(s, "\n");
	}

	public static ListString byDelimiter(String s, String limit) {
		ListString list = byDelimiterSplit(s, limit);
		list.removeLastEmptys();	
		return list;
	}
	
	public static ListString byDelimiterSplit(String s, String limit) {

//		String[] split = s.split(limit);
		ListString list = new ListString();
		
		if (limit.isEmpty()) {
			
			while (!s.isEmpty()) {
				list.add(s.substring(0,1)); 
				s = s.substring(1);
			}
			
			return list;
			
		}
		
//		list.removeLastEmptys();
//		return list;
///*
		while (true) {

			int ix = s.indexOf(limit);

			if (ix < 0) {
				break;
			}

			String x = s.substring(0, ix);
			s = s.substring(x.length() + limit.length());
			list.add(x);

		}

		if (StringLength.get(s) > 0) {
			list.add(s);
		}

		return list;

	}

	public static ListString byDelimiter(String s, String delimiter0, String delimiter1, String... delimiters) {

		ListString list = new ListString();
		if (StringEmpty.is(s)) {
			return list;
		}

		ListString limits = new ListString();
		limits.add(delimiter0);
		limits.add(delimiter1);

		for (String dm : delimiters) {
			if (dm != null && !"".contentEquals(dm)) {
				limits.add(dm);
			}
		}

		return byDelimiter(s, limits);

	}

	public ListString add(String before, ListString list) {
		for (String s : list) {
			add(before + s);
		}
		return this;
	}
	public ListString add(ListString list) {
		addAll(list);
		return this;
	}
	public Integer getInt(int index) {
		return IntegerParse.toInt(get(index));
	}

	@Override
	public ListString filter(Predicate<String> predicate) {
		return new ListString(super.filter(predicate));
	}

	@Override
	public ListString removeAndGet(Predicate<String> predicate) {
		return new ListString(super.removeAndGet(predicate));
	}

	public ListString removeIfTrimStartsWith(String s) {
		Predicate<String> p = x -> {
			if (x == null) {
				return s == null;
			}
			return x.trim().startsWith(s.trim());
		};
		removeIf(p);
		return this;
	}
	public ListString removeIfStartsWith(String s) {
		Predicate<String> p = x -> {
			if (x == null) {
				return false;
			}
			return x.startsWith(s);
		};
		removeIf(p);
		return this;
	}

	public ListString removeIfNotStartsWith(ListString itens) {

		Predicate<String> p = x -> {
			if (x == null) {
				return true;
			}
			for (String s : itens) {
				if (x.startsWith(s)) {
					return false;
				}
			}
			return true;
		};
		removeIf(p);
		return this;

	}

	public ListString removeIfNotEndsWith(ListString itens) {

		Predicate<String> p = x -> {
			if (x == null) {
				return true;
			}
			for (String s : itens) {
				if (x.endsWith(s)) {
					return false;
				}
			}
			return true;
		};
		removeIf(p);
		return this;

	}

	public ListString removeIfNotStartsWith(String... list) {
		return removeIfNotStartsWith( ListString.array(list) );
	}

	public ListString removeIfNotEndsWith(String... list) {
		return removeIfNotEndsWith( ListString.array(list) );
	}

	public ListString removeIfTrimEquals(String s) {
		Predicate<String> p = x -> {
			if (x == null) {
				return s == null;
			}
			return x.trim().equals(s);
		};
		removeIf(p);
		return this;
	}

	public ListString removeFisrtEmptys() {
		while (!isEmpty() && StringEmpty.is(get(0))) {
			remove(0);
		}
		return this;
	}

	public ListString removeLastEmptys() {
		while (!isEmpty() && StringEmpty.is(getLast())) {
			removeLast();
		}
		return this;
	}
	
	public void removeEmptysDenessessariosDeBodys() {

		for (int i = 0; i < size()-3; i++) {
			
			String s = get(i);
			
			if (!s.endsWith("{")) {
				continue;
			}
			
			s = get(i+1);
			
			if (!s.trim().isEmpty()) {
				continue;
			}
			
			s = get(i+2);
			
			if (s.trim().isEmpty()) {
				continue;
			}
			
			s = get(i+3);

			if (!s.trim().isEmpty()) {
				continue;
			}
			
			s = get(i+4);

			if (!s.trim().startsWith("}")) {
				continue;
			}
			
			remove(i+3);
			remove(i+1);
			i += 2;
			
		}
		
		filter(s -> s.endsWith("{"));
		
	}

	public ListString removeIfEquals(String s) {
		Predicate<String> p = x -> {
			if (x == null) {
				return s == null;
			}
			return x.equals(s);
		};
		removeIf(p);
		return this;
	}

	@Override
	public boolean contains(Object o) {
		if (o instanceof String) {
			String s = (String) o;
			return contains(s);
		}
		return contains( StringParse.get(o) );
	}

	public boolean containsAny(String... strings) {
		for (String s : strings) {
			if (contains(s)) {
				return true;
			}
		}
		return false;
	}

//	nao remover
	public boolean contains(String s) {
		return super.contains(s);
	}

	public ListString removeWhites() {
		while (contains("")) {
			remove("");
		}
		return this;
	}

	public ListString addLeft(String s) {
		ListString list = new ListString();
		for (String linha : this) {
			list.add(s + linha);
		}
		clear();
		addAll(list);
		return this;
	}

	public ListString addRight(String s) {
		ListString list = new ListString();
		for (String linha : this) {
			list.add(linha + s);
		}
		clear();
		addAll(list);
		return this;
	}

	public ListString removeLeft(int length) {
		ListString list = new ListString();
		for (String s : this) {
			if (s.length() <= length) {
				list.add();
			} else {
				list.add(s.substring(length));
			}
		}
		clear();
		addAll(list);
		return this;
	}

	public ListString removeRight(int length) {
		ListString list = new ListString();
		for (String s : this) {
			if (s.length() <= length) {
				list.add();
			} else {
				list.add(StringRight.ignore(s, length));
			}
		}
		clear();
		addAll(list);
		return this;
	}

	public ListString removeLast(int quantidade) {
		return (ListString) super.removeLast_(quantidade);
	}

	public ListString remove(int index, int quantidade) {
		return (ListString) super.remove_(index, quantidade);
	}

	public ListString add(List<?> list) {
		for (Object o : list) {
			add(o.toString());
		}
		return this;
	}
	public ListString trimPlus() {
		Lista<String> lista = copy();
		clear();
		for (String s : lista) {
			s = StringTrim.plus(s);
			if (!StringEmpty.is(s)) {
				add(s);
			}
		}
		removeWhites();
		return this;
	}

	public ListString trim() {
		Lista<String> lista = copy();
		clear();
		for (String s : lista) {
			add(s.trim());
		}
		return this;
	}

	public ListString rtrim() {
		Lista<String> lista = copy();
		clear();
		for (String s : lista) {
			add(rtrim(s));
		}
		return this;
	}

	public static ListString loadResource(Class<?> classe) {
		return ListString.loadResourceByExtensao(classe, "txt");
	}

	public static ListString loadResourceByExtensao(Class<?> classe, String extensao) {
		return ListString.loadResource(classe, classe.getSimpleName() + "." + extensao);
	}

	public static ListString loadResource(Class<?> classe, String fileName) {
		return ListString.fromFile(ListString.s_resource(classe, fileName));
	}

	public static ListString loadResourceUTF8(Class<?> classe, String fileName) {
		return ListString.fromFileUTF8(ListString.s_resource(classe, fileName));
	}

	public static ListString loadResourceISO88591(Class<?> classe, String fileName) {
		return ListString.fromFileISO88591(ListString.s_resource(classe, fileName));
	}

	static String s_resource(Class<?> classe, String fileName) {
		if (fileName.startsWith("+")) {
			fileName = fileName.replace("+", classe.getSimpleName());
		}
		URL resource = classe.getResource(fileName);
		if (resource == null) {
			throw UException.runtime("resource == null : " + fileName);
		}
		String s = resource.toString();
		s = s.replace("file:/", "");
		s = s.replace("vfs:/", "");
		return s.replace("/", "\\");
	}
	public static ListString nova(String[] list) {
		ListString l = new ListString();
		l.addArray(list);
		return l;
	}
	public static ListString separaPalavras(String s) {

//		long start = Date.now();

		try {

			ListString simbolos = UConstantes.SIMBOLOS.copy();
			simbolos.remove("_");
			simbolos.remove("$");
			simbolos.remove("@");
			simbolos.remove("#");

			ListString list = new ListString();
			String x = "";
			while (!s.isEmpty()) {
				String caracter = s.substring(0, 1);
				s = s.substring(1);
				if (simbolos.contains(caracter)) {
					if (!"".equals(x)) {
						list.add(x);
					}
					list.add(caracter);
					x = "";
				} else {
					x += caracter;
				}
			}
			if (!"".equals(x)) {
				list.add(x);
			}
			return list;

		} finally {
//			Tempo.exec(start);
		}

	}

	@SuppressWarnings("unchecked")
	@Override
	public ListString copy() {
		ListString list = super.copy();
		list.autoIdentacao = autoIdentacao;
		list.getMargem().set(getMargem().get());
		return list;
	}
	
	@Override
	public ListString clone() {
		return copy();
	}
	
	public ListString menos(ListString list) {
		ListString copy = copy();
		for (String string : list) {
			copy.remove(string);
		}
		return copy;
	}
	public static ListString loadClass(Class<?> classe) {
		String s = UClass.getJavaFileName(classe);
		if (s == null) {
			throw UException.runtime("ListString - JavaFile nao encontrado: " + classe.getSimpleName());
		}
		File file = new File(s);
		return new ListString().load(file);
	}
	public ListString juntarComASuperiorSe(F1<String,Boolean> predicate, String separador) {
		return juntarComASuperiorSe(predicate, separador, "");
	}
	public ListString juntarComASuperiorSe(F1<String,Boolean> predicate, String separador, String after) {
		ListString list = new ListString();
		for (String linha : this) {
			if (!list.isEmpty() && predicate.call(linha)) {
				linha = list.removeLast() + separador + linha.trim() + after;
			}
			list.add(linha);
		}
		clear();
		add(list);
		return this;
	}
	public ListString juntarComASuperiorSeEquals(String s, String separador, String after) {
		return juntarComASuperiorSe(linha -> StringCompare.eq(s, linha), separador, after);
	}
	public ListString juntarComASuperiorSeEquals(String s, String separador) {
		return juntarComASuperiorSe(linha -> StringCompare.eq(s, linha), separador);
	}
	public ListString juntarComASuperiorSeEquals(String s) {
		return juntarComASuperiorSe(linha -> StringCompare.eq(s, linha), "");
	}
	public ListString juntarComASuperiorSeTrimStartarCom(String prefix) {
		return juntarComASuperiorSeTrimStartarCom(prefix, "");
	}
	public ListString juntarComASuperiorSeTrimStartarCom(String prefix, String separador) {
		return juntarComASuperiorSe(s -> s.trim().startsWith(prefix), separador);
	}

	public ListString juntarComAProximaSeTrimTerminarCom(String prefix) {
		return juntarComAProximaSeTrimTerminarCom(prefix, "");
	}
	public ListString juntarComAProximaSe(F1<String,Boolean> predicate, String separador, String after) {
		ListString list = new ListString();
		boolean juntar = false;
		for (String linha : this) {
			if (juntar) {
				linha = StringTrim.right(list.removeLast()) + separador + linha.trim() + after;
				juntar = false;
			} else if (predicate.call(linha)) {
				juntar = true;
			}
			list.add(linha);
		}
		clear();
		add(list);
		return this;
	}
	public ListString juntarComAProximaSe(F1<String,Boolean> predicate, String separador) {
		return juntarComAProximaSe(predicate, separador, "");
	}
	public ListString juntarComAProximaSeEquals(String s, String separador) {
		return juntarComAProximaSe(linha -> StringCompare.eq(s, linha), separador);
	}
	public ListString juntarComAProximaSeEquals(String s) {
		return juntarComAProximaSe(linha -> StringCompare.eq(s, linha), "");
	}
	public ListString juntarComAProximaSeTrimTerminarCom(String prefix, String separador) {
		return juntarComAProximaSe(s -> s.trim().endsWith(prefix), separador);
	}
	public ListString add(Throwable e) {
		add(e.getClass().getName());
		StackTraceElement[] stack = e.getStackTrace();
		for (StackTraceElement o : stack) {
			add(o.toString());
		}
		return this;
	}
	public ListString loadResource(String fileName) {
		return loadResource(fileName, getCharSet());
	}
	public ListString loadResource(String fileName, CharSet charSet) {

		ClassLoader classLoader = getClass().getClassLoader();
		InputStream is = classLoader.getResourceAsStream(fileName);

		if (is == null) {
			throw UException.runtime("is is null: " + fileName);
		}

		load(is, charSet);
//		File file = new File(classLoader.getResource(fileName).getFile());
//		load(file, charSet);

//		ServletContext x = ServletActionContext.getServletContext();
//		String realPath = x.getRealPath("");
//		String realFileName = realPath + "/" + fileName;
//		load(realFileName);
		return this;
	}
	public ListString toStringSqlDeclaration() {
		ListString list = new ListString();
		list.add("static final String sql = \"\"");

		for (String s : this) {

			if (StringEmpty.is(s)) {
				list.add();
				continue;
			}
			s = rtrim(s);
			if (StringContains.is(s, "--")) {
				s = s.replace("--", "/*");
				s += "*/";
			}

			s = s.replace("\t", "  ");

			s = "\t+ \" " + s;
			s += "\"";

			list.add(s);
		}

		list.add(";");
		return list;
	}
//	public static ListString clipboard(){
//		ListString list = new ListString();
//		list.add(UString.clipboard().split("\n"));
//		return list;
//	}
//	public void toClipboard(){
//		UString.clipboard(toString("\n"));
//	}
	public void save(Class<?> classe){
		save(UClass.getJavaFileName(classe));
	}
	public static ListString loadClassText(Class<?> classe){
  		return new ListString().load( UClass.getJavaFileName(classe) );
	}
	public ListString removeIfStartsWithAndEndsWith(String a, String b) {

		Predicate<String> p = x -> x.startsWith(a) && x.endsWith(b);
		removeIf(p);
		return this;

	}
	public ListString removeIfNotContains(String s) {

		Predicate<String> p = x -> {
			if (x == null) {
				return s == null;
			}
			return !x.contains(s);
		};
		removeIf(p);
		return this;

	}
	public ListString removeIfContains(String s) {
		String s2 = UHtml.replaceSpecialChars(s);
		Predicate<String> p = x -> {
			if (x == null) {
				return s == null;
			}
			return x.contains(s) || x.contains(s2);
		};
		return remove(p);
	}

	private ListString remove(Predicate<String> predicate) {
		ListString list = new ListString();
		for (String s : this) {
			if ( predicate.test(s) ) {
				list.add(s);
			}
		}
		for (String s : list) {
			remove(s);
		}
		return list;
	}

	public boolean save(File file) {
		return save(file.toString());
	}

	private String lastRemoved;

	@Override
	public String remove(int index) {
		
		if (isEmpty()) {
			return null;
		}
		
		while (index < 0) {
			index += size();
		}
		
		lastRemoved = super.remove(index);
		return lastRemoved;
	}

	static void testeRemoveTextEntre(){

		ListString list = new ListString();
		list.add("teste a");
		list.add("teste b");
		list.add("teste c");
		list.add("<nome>na mesma linha abc");
		list.add("linha abc</nome>");
		list.add("teste x");
		list.add("teste y");
		list.add("teste z");

		ListString result = list.removeTextEntre("<nome>", "</nome>");
		list.print();
		ULog.debug("=======");
		result.print();

	}

	public ListString removeTextEntre(String inicio, String fim) {

		ListString result = new ListString();
		ListString l = new ListString();

		int inicios = 0;

		while ( has() ) {

			String s = remove(0);

			if (StringContains.is(s, inicio)) {
				String x = StringBeforeFirst.get(s, inicio);
				s = StringAfterFirst.get(s, inicio);
				if (!StringEmpty.is(x)) {
					l.add(x);
				}
				inicios++;
				if (StringEmpty.is(s)) {
					continue;
				}
			}

			if ( inicios == 0 ) {
				l.add(s);
				continue;
			}

			if (!StringContains.is(s, fim)) {
//				result.add(s);
				continue;
			}

			String x = StringAfterFirst.get(s, fim);
			s = StringBeforeFirst.get(s, fim);
			if (!StringEmpty.is(x)) {
				l.add(x);
			}

			if (!StringEmpty.is(s) && inicios > 0) {
				result.add(s);
			}

			inicios--;

			if (inicios == 0) {
				l.addAll( this );
				clear();
				break;
			}

		}

		addAll(l);
		return result;

	}

	private static ListInteger invalids = new ListInteger(65533);

	public static ListString load_e_escolhe_qual_por_encoding(File file){
		return ListString.load_e_escolhe_qual_encoding(file.toString());
	}
	public static ListString load_e_escolhe_qual_encoding(String file){

		ListString a = new ListString();
		a.loadUTF8(file);

		ListString b = new ListString();
		b.loadISO88591(file);

		for (String s : a) {
			while (!s.isEmpty()) {
				int c = s.charAt(0);
				if (ListString.invalids.contains(c)) {
//					ULog.debug("iso");
					return b;
				}
//				ULog.debug(c);
				s = s.substring(1);
			}
		}

//		ULog.debug("utf");
		return a;

	}

	public ListString eachRemoveBefore(String string, boolean paramTo) {

		ListString list = new ListString();

		for (String s : this) {

			if (StringContains.is(s, string)) {
				s = StringAfterFirst.get(s, string);
				if (!paramTo) {
					s = string + s;
				}
			}

			list.add(s);

		}

		clear();
		addAll(list);
		return this;

	}
	public ListString eachRemoveAfter(String string, boolean paramTo) {

		ListString list = new ListString();

		for (String s : this) {

			if (StringContains.is(s, string)) {
				s = StringBeforeFirst.get(s, string);
				if (!paramTo) {
					s += string;
				}
			}

			list.add(s);

		}

		clear();
		addAll(list);
		return this;

	}
	public boolean size(int i) {
		return size() == i;
	}

	public void addObject(Object o) {
		add(o.toString());
	}

	public InputStream getFileStream() {

		return null;
	}
	public String toSQL() {
		replaceTexto("'", "''");
		return toString("");
	}
	public static <T> List<T> loadResource(Class<?> from, String file, String delimiter, Class<T> classe){
		ListString list = ListString.loadResource(from, file);
		return ListString.load(list, delimiter, classe);
	}
	public static <T> List<T> load(String file, String delimiter, Class<T> classe){
		ListString list = new ListString();
		list = list.load(file);
		return ListString.load(list, delimiter, classe);
	}
	public static <T> List<T> load(ListString list, String delimiter, Class<T> classe){

		List<T> result = new ArrayList<>();

		Atributos as = AtributosBuild.get(classe);

		for( final String s : list ) {
			T o = UClass.newInstance(classe);
			result.add(o);

			ListString values = ListString.byDelimiter(s, delimiter);

			for (Atributo a : as) {
				a.set(o, values.remove(0));
			}

		}

		return result;

	}
	private static String getTempFile() {
		if (SO.windows()) {
			return "c:/temp/x.txt";
		}
		return "target/tmp/x.txt";
	}
	private static String getTempFile(int index) {
		if (SO.windows()) {
			return "c:/temp/x"+index+".txt";
		}
		return "target/tmp/x"+index+".txt";

	}
	public ListString loadTemp() {
		load( ListString.getTempFile() );
		return this;
	}
	public void saveTemp() {
		save( ListString.getTempFile() );
	}
	public void saveTemp(int index) {
		save( ListString.getTempFile(index) );
	}
	public ListString load(Map<?,?> map){
		for (Object key : map.keySet()) {
			add( StringParse.get(key) + " = " + StringParse.get( map.get(key) ) );
		}
		return this;
	}
	public void quebraPor(String delimiter, boolean antes) {
		ListString list = copy();
		clear();
		for (String s : list) {
			while (StringContains.is(s, delimiter)) {
				String x = StringBeforeFirst.get(s, delimiter);
				if (antes) {
					x += delimiter;
					add(x);
				} else {
					add(x);
					add(delimiter);
				}
				s = StringAfterFirst.get(s, delimiter).trim();
			}
			if (!StringEmpty.is(s)) {
				add(s);
			}
		}

		if (!antes) {
			list = copy();
			clear();
			String s = "";
			while (!list.isEmpty()) {
				s += list.remove(0);
				if (s.equals(delimiter)) {
					continue;
				}
				add(s);
				s = "";
			}
		}
	}
	public void removeHtml() {
		ListString list = copy();
		clear();
		for( String s : list ) {
			s = UHtml.removeAtributos(s);
			s = UHtml.removeHtml(s);
			add(s);
		}
	}
	public void toHtml(){
		ListString list = copy();
		clear();
		for( String s : list ) {
			s = UHtml.replaceSpecialChars(s);
			add(s);
		}
	}
	public List<SimpleIdObject> putIds() {
		List<SimpleIdObject> list = new ArrayList<>();
		int i = 1;
		for (String s : this) {
			list.add( new SimpleIdObject(i, s) );
			i++;
		}
		return list;
	}
	public ListString primeirasMaiusculas() {
		ListString x = new ListString();
		for (String s : this) {
			if (s == null) {
				continue;
			}
			s = StringPrimeiraMaiuscula.exec(s);
			x.add(s);
		}
		clear();
		addAll(x);
		return this;
	}
	public String join(String separador) {
		return toString(separador);
	}

	public int indexOfWithTrim(String s) {
		if (s == null) {
			throw new NullPointerException("s == null");
		}
		return copy().trim().indexOf(s.trim());
	}
	public int indexOfWithTrim(ListString list) {

		for (int i = 0; i < size()-list.size()+1; i++) {
			boolean igual = true;
			for (int j = 0; j < list.size(); j++) {
				String a = StringTrim.plus(get(i+j));
				String b = StringTrim.plus(list.get(j));
				if (StringCompare.eq(a, b)) {
					continue;
				}
				if (b.endsWith("(*?)")) {
					b = b.substring(0, b.length()-4);
					if (a.startsWith(b)) {
						continue;
					}
				}
				igual = false;
				break;
			}
			if (igual) {
				return i;
			}
		}

		return -1;
	}
	public void add(int index, ListString list){
		for(String s : list){
			add(index, s);
			index++;
		}
	}
	public int replace(ListString velho, String novo) {
		ListString list = new ListString();
		list.add(novo);
		return replace(velho, list);
	}
	private boolean replacePrivate(ListString velho, F1<ListString, ListString> func) {
		int index = indexOfWithTrim(velho);
		if (index == -1) {
			return false;
		}
		int margens = 0;
		String s = get(index);
		while (s.startsWith("\t")) {
			s = s.substring(1);
			margemInc();
			margens++;
		}
		remove(velho);
		int m = margem.get();
		margem.set(margens);
		ListString novo = func.call(velho);
		add(index, novo);
		margem.set(m);
		return true;
	}
	public int replace(ListString velho, ListString novo) {
		return replace(velho, v -> novo);
	}
	public int replace(ListString velho, F1<ListString, ListString> func) {
		int result = 0;
		while (replacePrivate(velho, func)) {
			result++;
		}
		return result;
	}
	public int remove(String... itens) {
		return remove(ListString.array(itens));
	}
	@SuppressWarnings("unused")
	public int remove(ListString list) {
		int index = indexOfWithTrim(list);
		if (index == -1) {
			return -1;
		}
		for (String element : list) {
			remove(index);
		}
		return index;
	}
	
	public ListString removeDoubleWhitesGet(){
		removeDoubleWhites();
		return this;
	}
	
	public int removeDoubleWhites(){
		
		int count = 0;
		
		int i = 1;
		
		int size = size();
		
		while (i < size) {
			
			if (StringEmpty.is(get(i)) && StringEmpty.is(get(i-1))) {
				remove(i);
				size--;
				count++;
			} else {
				i++;
			}
			
		}
		
		return count;
		
//
//		ListString novo = new ListString();
//		novo.add();
//		ListString velho = new ListString();
//		velho.add();
//		velho.add();
//		return replace(velho, novo);
	}
	@Override
	public ListString addIfNotContains(String s) {
		if (s != null) {
			super.addIfNotContains(s);
		}
		return this;
	}
	public static ListString newListString(Set<String> keySet) {
		ListString list = new ListString();
		list.addAll(keySet);
		return list;
	}

	public void removeEmptys() {
		removeIf(s -> StringEmpty.is(s));
	}

	public boolean moveToFirst(String s, boolean addIfNotContains) {
		boolean remove = remove(s);
		if (remove || addIfNotContains) {
			add(0, s);
		}
		return remove;
	}
	public boolean moveToLast(String s, boolean addIfNotContains) {
		boolean remove = remove(s);
		if (remove || addIfNotContains) {
			add(s);
		}
		return remove;
	}
	public List<Map<String, Object>> asMap() {
		List<Map<String, Object>> maps = new ArrayList<>();
		int id = 1;
		for (String s : this) {
			HashMap<String, Object> map = new HashMap<>();
			map.put("id", id);
			id++;
			map.put("text", s);
			maps.add(map);
		}
		return maps;
	}

	public void removeRepetidos() {
		ListString copy = copy();
		clear();
		for (String s : copy) {
			addIfNotContains(s);
		}
	}

	public boolean containsIgnoreCase(String valor) {
		for (String s : this) {
			if ( s.equalsIgnoreCase(valor) ) {
				return true;
			}
		}
		return false;
	}

	@Override
	public String[] toArray() {
		String[] list = new String[size()];
		for (int i = 0; i < size(); i++) {
			list[i] = get(i);
		}
		return list;
	}

	public void identarJava() {
		ListString list = copy().trim();
		clear();
		margem.set(0);
		setAutoIdentacao(true);
		add(list);
	}

	public ListString union(ListString list) {
		return copy().add(list);
	}

	public static ListString convert(List<?> list) {
		ListString listString = new ListString();
		for (Object o : list) {
			String s = StringParse.get(o);
			if (s != null) {
				listString.add(s);
			}
		}
		return listString;
	}

	public static void main(String[] args) {
		ListString list = new ListString();
		list.load("/opt/desen/tmp/xx.txt");
		list.add("/opt/desen/tmp/xx.txt");
		list.save();
		list.print();
		
//		list.load("/opt/desen/gm/cs2019/compare/src/main/resources/a.java");
//		list.removeTextEntre("/*", "*/");
//		list.print();
	}

	public void juntarFimComComecos(String fim, String comeco, String separador) {

		removeFisrtEmptys();
		removeLastEmptys();

		if (isEmpty()) {
			return;
		}

		ListString list2 = new ListString();

		boolean lastEndsWithFim = false;

		while (!isEmpty()) {

			String s = remove(0);

			if (StringEmpty.is(s)) {
				list2.add(s);
				continue;
			}

			s = rtrim(s);

			if (!lastEndsWithFim) {
				list2.add(s);
				lastEndsWithFim = s.endsWith(fim);
				continue;
			}

			if (!s.trim().startsWith(comeco)) {
				list2.add(s);
				lastEndsWithFim = false;
				continue;
			}

			list2.removeLastEmptys();
			s = list2.removeLast() + separador + s.trim();
			list2.add(s);

			lastEndsWithFim = s.endsWith(fim);

		}

		add(list2);

	}

	private String rtrim(String s) {
		return StringTrim.right(s);
	}

	public boolean first(String s, String... strings) {
		if (isEmpty()) {
			return false;
		}
		return UString.in(get(0), s, strings);
	}

	public boolean endsWith(String s) {
		if (isEmpty()) {
			return false;
		}
		return getLast().equals(s);
	}

	public boolean endsWith(String s, String... strings) {
		if (isEmpty()) {
			return false;
		}
		return UString.in(getLast(), s, strings);
	}

	public static ListString loadFile(File file) {
		UFile.assertExists(file);
		ListString list = new ListString();
		list.load(file);
		return list;
	}

	public void replacePalavras(Map<String, String> map) {
		Set<String> keys = map.keySet();
		replaceEach(s -> {
			for (String key : keys) {
				String value = map.get(key);
				s = StringReplacePalavra.exec(s, key, value);
			}
			return s;
		});
	}

	public ListString replaceEach(F1<String,String> func) {
		ListString list = copy();
		clear();
		for (String s : list) {
			s = func.call(s);
			add(s);
		}
		return this;
	}
	
	public ListString replaceEachi(F2<String,Integer,String> func) {
		ListString list = copy();
		clear();
		for (int i = 0; i < list.size(); i++) {
			String s = list.get(i);
			s = func.call(s,i);
			add(s);
		}
		return this;
	}

	public ListString forEach(P2<String, Integer> action) {
		for (int i = 0; i < size(); i++) {
			action.call(get(i), i);
		}
		return this;
	}

	public int margemInc() {
		return getMargem().inc();
	}
	public int margemDec() {
		return getMargem().dec();
	}
	public ListString rm(String s) {
		remove(s);
		return this;
	}

	public String getFirst() {
		if (isEmpty()) {
			return null;
		}
		return get(0);
	}

	public ListString mapString(F1<String,String> func) {
		ListString list = new ListString();
		for (String s : this) {
			list.add(func.call(s));
		}
		return list;
	}

	public ListString mapStringTeste(F1<String,String> func) {

		Map<Integer, String> map = new HashMap<>();

		ThreadList threads = new ThreadList();

		for (int i = 0; i < size(); i++) {
			final int x = i;
			threads.exec(() -> {
				String o = get(x);
				String s = func.call(o);
				if (s == null) {
					func.call(o);
				}

				if (x == 26) {
					console.log(s);
				}

				map.put(x, s);
			});
		}

		threads.esperar();

		ListString list = new ListString();

		for (int i = 0; i < size(); i++) {

			if (!map.containsKey(i)) {
				console.log(i);
			}

			String s = map.get(i);
			list.add(s);
		}

		return list;

	}
	
	public <T> Lst<T> map(F1<String,T> func) {
		Lst<T> list = new Lst<>();
		for (String s : this) {
			list.add(func.call(s));
		}
		return list;
	}

	public static ListString readBytes(byte[] bytes) {
		try {
			return byQuebra(new String(bytes, "UTF-8"));
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public void saveOnLoad(String file) {
		saveOnLoad = file;
	}

	public ListString addd(String s) {
		add(s);
		return this;
	}

	public void removeRepetidosEmSequencia() {
		
		if (isEmpty()) {
			return;
		}
		
		ListString list = copy();
		clear();
		add(list.remove(0));
		
		for (String s : list) {
			if (!getLast().contentEquals(s)) {
				add(s);
			}
		}
		
	}

	public ListString removeSequence(String... array) {
		ListString list = new ListString(array);
		replace(list, "");
		return this;
	}

	public ListString loadIfExists(String file) {
		
		if (UFile.exists(file)) {
			load(file);
		} else {
			clear();
			fileName = file;
		}
		return this;
	}

	public ListString each(P2<String, Integer> action) {
		return forEach(action);
	}
	
	public ListString each(P1<String> action) {
		for (String s : this) {
			action.call(s);
		}
		return this;
	}

	public ListString print() {
		if (isEmpty()) {
			return this;
		}
		ULog.debug(toString("\n"));
		return this;
	}

	public ListString printErros() {
		if (isEmpty()) {
			return this;
		}
		for (String s : this) {
			SystemPrint.err(s);
		}
		return this;
	}
	
}
