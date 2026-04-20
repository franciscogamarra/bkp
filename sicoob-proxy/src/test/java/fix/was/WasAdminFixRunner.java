package fix.was;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;

public class WasAdminFixRunner {

    public static void run(File wsadmin, File script) {

        try {
            ProcessBuilder pb = new ProcessBuilder(
                    wsadmin.getAbsolutePath(),
                    "-conntype", "NONE",
                    "-lang", "jython",
                    "-f", script.getAbsolutePath()
            );

            pb.redirectErrorStream(true);
            Process p = pb.start();

            try (BufferedReader br = new BufferedReader(
                    new InputStreamReader(p.getInputStream()))) {

                String line;
                while ((line = br.readLine()) != null)
                    System.out.println(line);
            }

            int exit = p.waitFor();
            System.out.println("Finalizado. Exit = " + exit);

            if (exit != 0)
                throw new RuntimeException("Erro executando wsadmin.");
        }
        catch (Exception e) {
            throw new RuntimeException("Falha ao executar wsadmin: " + e);
        }
    }

}
