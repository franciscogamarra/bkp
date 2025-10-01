package gm.utils.git;

import gm.utils.comum.SystemPrint;
import gm.utils.exception.NaoImplementadoException;
import gm.utils.files.GFile;
import gm.utils.lambda.F1;
import gm.utils.string.ListString;
import src.commom.utils.string.StringConstants;
import src.commom.utils.string.StringRight;
import src.commom.utils.tempo.DataHora;

public abstract class GitOperations {
	
	private final GFile file;
	public final String branch;
//	private final Runtime runtime = Runtime.getRuntime();
	private final GFile logFile = GFile.get("c:/tmp/logs/" + DataHora.now().formatFileName() + ".log");
	private final GFile logFileTemp = GFile.get("c:/tmp/log.log");
	private final ListString log = new ListString();

	public GitOperations() {
		log.setFileName(logFile.toString());
//		log.save();
		this.file = getFile();
		verificaSeEhUmDiretorioGit();
		branch = getCurrentBranch();
		validar();
	}
	
    protected abstract GFile getFile();
    
	private void verificaSeEhUmDiretorioGit() {

		if (!file.isDirectory()) {
			throw new RuntimeException("Não é um diretorio: " + file);
		}
		
		GFile git = file.join(".git");
		
		if (!git.exists()) {
			throw new RuntimeException("Não encontrado o .git na path: " + file);
		}

		if (!git.isDirectory()) {
			throw new RuntimeException(".git encontrado mas não é um diretorio: " + file);
		}
		
	}
	    
    private String exec(String comando, F1<ListString, String> func, boolean rodar) {
    	
    	if (!log.isEmpty()) {
			log.add();
		}
    	
    	SystemPrint.ln(comando);
    	
//    	log.add("==========================================");
//    	log.add(DataHora.now().toString() + " << start");
    	log.add(comando);
//    	log.add("==========================================");
//    	log.save();
    	
//    	logFileTemp.create();
    	
    	if (!rodar) {
			return "";
		}
    	
//    	Cronometro cron = new Cronometro();
    	
    	try {
    		
    		String[] command = ListString.split(comando, " ").trimPlus().toArray();
    		
			ProcessBuilder processBuilder = new ProcessBuilder(command);
			processBuilder.directory(file.toFile());
			processBuilder.redirectOutput(logFileTemp.toFile());
			
			Process process = processBuilder.start();
            
            int exitCode = process.waitFor();
            
            ListString exit = logFileTemp.load();
			log.addAll(exit);
//            log.add("==========================================");
//            log.add(DataHora.now().toString() + " << end");
//            log.add("==========================================");
//            log.save();
            
            if (exitCode == 0) {
            	if (func == null) {
					return null;
				} else {
					return func.call(exit);
				}
            } else {
            	return null;
            }
    		
		} catch (Throwable e) {
			throw toRuntime(e);
		} finally {
//	    	cron.print();
		}
    	
    }
    
    protected abstract void validar();

	public static RuntimeException toRuntime(Throwable e) {
    	return e instanceof RuntimeException ? (RuntimeException) e : new RuntimeException(e);
    }
    
    private void exec(String comando, boolean rodar) {
    	exec(comando, null, rodar);
    }

    private String getCurrentBranch() {
    	return exec("git symbolic-ref --short HEAD", exit -> exit.get(0), true);
    }
    
    public void add() {
    	validar();
    	exec("git add .", rodar());
    }
    
    public void commit() {
    	validar();
    	exec("git commit -m \"" + getMensagem() + "\"", rodar());
    }
    
    public String getMensagem() {
    	
//    	String numero = StringAfterFirst.get(branch, "-");
//    	numero = StringBeforeFirst.get(numero, "-");
//    	
//    	if (!IntegerIs.is(numero)) {
//    		throw new RuntimeException("O nome da branch deve iniciar com .../PDC-<NUMERO>-");
//		}

    	String tipo;
    	
    	if (branch.startsWith("feature/PDC-")) {
    		tipo = "feat";
		} else if (branch.startsWith("feature/PDC-")) {
			tipo = "fix";
		} else {
			throw new NaoImplementadoException();
		}

    	String s = tipo + ": " + getMsg().trim();
    	
    	while (s.endsWith(".") || s.endsWith(";") || s.endsWith("!") || s.endsWith("?")) {
			s = StringRight.ignore1(s).trim();
		}

    	if (s.length() > 100) {
    		SystemPrint.err(s);
			throw new RuntimeException("O tamanho máximo da mensagem é de 100 caracteres. A msg atual tem " + s.length());
		}
    	
    	if (!s.contentEquals(s.toLowerCase())) {
    		SystemPrint.err(s);
    		for (String m : StringConstants.MINUSCULAS.getArray().list) {
				s = s.replace(m, " ");
			}
    		SystemPrint.err(s);
			throw new RuntimeException("Não podem haver letras maiúsculas na mensagem");
		}

    	if (s.endsWith(".")) {
    		SystemPrint.err(s);
			throw new RuntimeException("A mensagem não pode terminar com '.'");
		}
    	
    	return s;
    	
    }
    
    protected abstract String getMsg();
    
    public void push() {
    	
    	validar();
    	
    	if ("true".equals(exec("git ls-remote --exit-code --heads origin " + branch, process -> "true", true))) {
    		exec("git push", rodar());
		} else {
			exec("git push --set-upstream origin " + branch, rodar());
		}

    }
    
    protected abstract boolean rodar();

	public void print() {
    	file.print();
    	SystemPrint.ln(branch);
    	SystemPrint.ln(getMensagem());
	}
	
    
}