package fix.was;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class WasEnvLocator {

    public final File wasRoot;
    public final File profilesRoot;
    public final List<File> profiles = new ArrayList<>();

    public File profile;
    public File bin;
    public File wsadmin;

    public File wimRepository;
    public File wimConfig;

    public WasEnvLocator() {
        this.wasRoot = detectWasRoot();
        this.profilesRoot = new File(wasRoot, "profiles");
        detectProfiles();
        detectBin();
        detectWimFilesDeep();
    }

    private File detectWasRoot() {
        String[] paths = {
                "C:/dev/IBM/WebSphere/AppServer",
                "C:/IBM/WebSphere/AppServer",
                "C:/Program Files/IBM/WebSphere/AppServer",
                "C:/Program Files (x86)/IBM/WebSphere/AppServer",
                "/opt/IBM/WebSphere/AppServer"
        };

        for (String p : paths) {
            File f = new File(p);
            if (f.exists()) return f;
        }

        throw new RuntimeException("Não foi possível encontrar o WebSphere AppServer.");
    }

    private void detectProfiles() {
        if (!profilesRoot.exists()) {
            throw new RuntimeException("A pasta profiles não existe: " + profilesRoot);
        }

        File[] dirs = profilesRoot.listFiles();
        if (dirs == null) throw new RuntimeException("Nenhum profile encontrado.");

        for (File d : dirs) {
            if (new File(d, "bin/wsadmin.bat").exists()) {
                profiles.add(d);
            }
        }

        if (profiles.isEmpty()) {
            throw new RuntimeException("Nenhum profile WAS válido encontrado.");
        }

        // usa o primeiro encontrado
        profile = profiles.get(0);
    }

    private void detectBin() {
        bin = new File(profile, "bin");
        wsadmin = new File(bin, "wsadmin.bat");

        if (!wsadmin.exists()) {
            throw new RuntimeException("wsadmin.bat não encontrado em: " + wsadmin);
        }
    }

    // =====================================================
    // NOVA BUSCA PROFUNDA (deep scan)
    // =====================================================
    private void detectWimFilesDeep() {

        System.out.println("Varredura profunda para localizar arquivos WIM...");

        wimRepository = deepFind(profile, "wimRepository.xml");
        if (wimRepository == null) {
            // nome alternativo comum
            wimRepository = deepFind(profile, "wimrepository.xml");
        }

        wimConfig = deepFind(profile, "wimconfig.xml");

        if (wimRepository == null) {
            throw new RuntimeException("wimRepository.xml não encontrado após varredura profunda.");
        }

        if (wimConfig == null) {
            throw new RuntimeException("wimconfig.xml não encontrado após varredura profunda.");
        }
    }

    // busca recursiva universal
    private File deepFind(File base, String name) {
        File[] list = base.listFiles();
        if (list == null) return null;

        for (File f : list) {
            if (f.getName().equalsIgnoreCase(name)) return f;
            if (f.isDirectory()) {
                File r = deepFind(f, name);
                if (r != null) return r;
            }
        }

        return null;
    }
}
