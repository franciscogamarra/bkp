package gm.utils.classes.sequenciais;
import gm.utils.classes.UClass;
import gm.utils.comum.ULog;
import gm.utils.files.UFile;
import gm.utils.string.ListString;
import src.commom.utils.integer.IntegerFormat;
import src.commom.utils.integer.IntegerParse;
import src.commom.utils.string.StringBeforeLast;
import src.commom.utils.string.StringRight;
public class ExcluirSequencia {
	public static void exec(Class<?> para) {
		String name = para.getName();
		String right = StringRight.get(name, 3);
		name = StringBeforeLast.get(name, right);
		ULog.debug(right);
		ULog.debug(name);
		Integer i = IntegerParse.toInt(right);
		do {
			i++;
			Class<?> de = UClass.getClass(name + IntegerFormat.zerosEsquerda(i, 3));
			if (de == null) {
				UFile.delete( UClass.getJavaFileName(para) );
				break;
			}
			exec(de, para);
			para = de;
			} while (true);
		}
//		public static void main(String[] args) {
//			UConfigFwConstructor.config();
//			exec(Install003.class);
//		}
		private static void exec(Class<?> de, Class<?> para) {
			ListString list = ListString.loadClass(de);
			list.replaceTexto("public class " + de.getSimpleName() + " ", "public class " + para.getSimpleName() + " ");
			list.replaceTexto(" " + de.getSimpleName() + "(", " " + para.getSimpleName() + "(");
			list.save(para);
		}
	}
