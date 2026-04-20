package gm.bats;

import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.Set;

import br.support.dev.GFile;

public class CorrigirIBM {
	
    private static final Set<String> EXTENSOES_EXCECAO = Set.of(
            "jar", "zip", "7z", "gz", "tar",
            "png", "jpg", "jpeg", "gif", "ico", "bmp",
            "mp3", "mp4", "avi", "mkv",
            "exe", "dll", "class", "bin", "pdf",
            "war", "ear", "bak"
            //, "log"
    );
	
	public static void main(String[] args) {
		percorre(GFile.get("c:/dev/ibm"));
	}

	private static void percorre(GFile dir) {
		dir.getFiles().forEach(i -> exec(i));
		dir.getDirs().forEach(d -> percorre(d));
	}

	private static void exec(GFile file) {
		
		try {
			
			if (EXTENSOES_EXCECAO.contains(file.getExtensao())) {
				return;
			}
			
			String original = Files.readString(file.getPath());
			String s = original
			.replace("Program Files (x86)", "dev")
			.replace("Program Files", "dev")
			.replace("Arquivos de Programas", "dev");
			if (!original.equals(s)) {
				System.out.println(file);
				Files.writeString(file.getPath(), s, StandardCharsets.UTF_8);
			}
			
		} catch (Exception e) {
			
		}
		
	}
	
}
