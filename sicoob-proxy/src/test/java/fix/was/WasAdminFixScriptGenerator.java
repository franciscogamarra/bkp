package fix.was;

import java.io.File;
import java.io.FileWriter;

public class WasAdminFixScriptGenerator {

    public File scriptFile;

    public WasAdminFixScriptGenerator(File binFolder, String newPassword) {

        scriptFile = new File(binFolder, "createWasAdmin.py");

        String script = ""
                + "print \"=== Recriando usuario wasadmin ===\"\n"
                + "uid = 'wasadmin'\n"
                + "pwd = '" + newPassword + "'\n\n"
                + "try:\n"
                + "    existing = AdminTask.searchUsers('[-uid ' + uid + ']')\n"
                + "    if existing:\n"
                + "        AdminTask.deleteUser('[-uniqueName uid=' + uid + ',o=defaultWIMFileBasedRealm]')\n"
                + "except:\n"
                + "    pass\n\n"
                + "AdminTask.createUser('[-uid ' + uid + ' -password ' + pwd + ' -cn wasadmin -sn wasadmin]')\n"
                + "AdminConfig.save()\n"
                + "print \"=== Concluido ===\"\n";

        try (FileWriter fw = new FileWriter(scriptFile)) {
            fw.write(script);
        } catch (Exception e) {
            throw new RuntimeException("Erro escrevendo script: " + e);
        }
    }

}
