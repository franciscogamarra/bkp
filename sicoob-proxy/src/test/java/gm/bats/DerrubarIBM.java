package gm.bats;

public class DerrubarIBM {

	public static void main(String[] args) throws Exception {
		
	    // Mata todos os "java.exe" com path contendo "WebSphere"
	    String cmd = 
	        "wmic process where \"ExecutablePath like '%WebSphere%'\" call terminate";

	    Process p = Runtime.getRuntime().exec(new String[]{"cmd", "/c", cmd});
	    p.waitFor();
	    System.out.println("Processos WebSphere finalizados.");
	    
	    Runtime.getRuntime().exec(
    	    new String[]{"cmd", "/c", "taskkill /F /IM java.exe /T"}
    	);
	    
	}

	
}
