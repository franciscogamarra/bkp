package fix.was;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;
import java.nio.file.Files;

public class FixWebSphereAdminUser {

    public static void main(String[] args) {

        String wasRoot = "C:/dev/IBM/WebSphere/AppServer";
        String profile = wasRoot + "/profiles/AppSrv01";
        String wimConfig = profile + "/config/cells/cell01/wim/config";

        File repo = new File(wimConfig + "/wimRepository.xml");
        File repo2 = new File(wimConfig + "/wimFileBasedRepository.xml");

        try {
            System.out.println("Criando repositório WIM mínimo...");

            repo.getParentFile().mkdirs();
            repo2.getParentFile().mkdirs();

            String minimal = ""
                + "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n"
                + "<sdo:datagraph xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\"\n"
                + "    xmlns:wim=\"http://www.ibm.com/websphere/wim\"\n"
                + "    xmlns:sdo=\"commonj.sdo\">\n\n"
                + "  <wim:Root>\n"
                + "  </wim:Root>\n\n"
                + "</sdo:datagraph>";

            Files.write(repo.toPath(), minimal.getBytes());
            Files.write(repo2.toPath(), minimal.getBytes());

            System.out.println("Repositórios criados.");

            // script jython
            String scriptContent = ""
                + "print \"Criando usuário administrador...\"\n"
                + "uid = \"wasadmin\"\n"
                + "pwd = \"wasadmin\"\n"
                + "AdminTask.createUser('[-uid ' + uid + ' -password ' + pwd + ' -cn wasadmin -sn wasadmin]')\n"
                + "AdminConfig.save()\n"
                + "print \"Concluído!\"\n";

            File script = new File(profile + "/bin/createWasAdmin.py");
            Files.write(script.toPath(), scriptContent.getBytes());

            System.out.println("Script criado.");

            // executa wsadmin
            ProcessBuilder pb = new ProcessBuilder(
                    profile + "/bin/wsadmin.bat",
                    "-conntype", "NONE",
                    "-lang", "jython",
                    "-f", script.getAbsolutePath()
            );

            pb.redirectErrorStream(true);
            Process p = pb.start();

            try (BufferedReader br = new BufferedReader(new InputStreamReader(p.getInputStream()))) {
                String line;
                while ((line = br.readLine()) != null)
                    System.out.println(line);
            }

            int exit = p.waitFor();
            System.out.println("wsadmin finalizado com código: " + exit);

            System.out.println("\n=== FIM ===");
            System.out.println("Agora reinicie o WebSphere e faça login com:");
            System.out.println("Usuário: wasadmin");
            System.out.println("Senha: wasadmin");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
