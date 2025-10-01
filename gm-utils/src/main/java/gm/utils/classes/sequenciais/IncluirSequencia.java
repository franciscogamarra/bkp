package gm.utils.classes.sequenciais;
import gm.utils.classes.UClass;
import gm.utils.comum.ULog;
import gm.utils.string.ListString;
import src.commom.utils.integer.IntegerFormat;
import src.commom.utils.integer.IntegerParse;
import src.commom.utils.string.StringAfterLast;
import src.commom.utils.string.StringBeforeLast;
import src.commom.utils.string.StringRight;
public class IncluirSequencia {
	private String name;
	private int getIndex(Class<?> para) {
		String s = para.getName();
		String right = StringRight.get(s, 3);
		name = StringBeforeLast.get(s, right);
		return IntegerParse.toInt(right);
	}
	public IncluirSequencia(Class<?> para) {
		int inicio = getIndex(para);
		Class<?> ultima = para;
		int i = inicio;
		while (true) {
			i++;
			Class<?> x = UClass.getClass(name + IntegerFormat.zerosEsquerda(i, 3));
			if (x == null) {
				break;
			}
			ultima = x;
		}
		while (i > inicio) {
			exec(ultima, i);
			i--;
			ultima = UClass.getClass(name + IntegerFormat.zerosEsquerda(i-1, 3));
		}
	}
	public static void main(String[] args) {
//		new IncluirSequencia(InstallCore009.class);
	}
	public void exec(Class<?> classe, int para) {
		String name = StringAfterLast.get(this.name, ".");
		String nomeDe = name + IntegerFormat.zerosEsquerda(para-1, 3);
		String nomePara = name + IntegerFormat.zerosEsquerda(para, 3);
		String fileName = UClass.getJavaFileName(classe);
		fileName = fileName.replace(nomeDe, nomePara);
		ULog.debug( nomeDe + " >> " + fileName );
		ListString list = ListString.loadClass(classe);
		list.replaceTexto("public class " + nomeDe + " ", "public class " + nomePara + " ");
		list.save(fileName);
	}
}
