import core.reflexionapi.ReflexionWrapper;
import org.jetbrains.annotations.NotNull;
import utils.threading.ThreadHelper;

import java.io.File;
import java.util.ArrayList;

public class Universe {
    private static Universe ourInstance = new Universe();

    public static Universe getInstance() {
        return ourInstance;
    }

    private Universe() {
    }

    public static final String PATH = "C:\\Users\\sandr\\Documents\\UniverseProject\\src\\main\\java\\generated\\";
    public static final String PROJECT_PATH = "C:\\Users\\sandr\\Documents\\UniverseProject\\src\\main\\java\\";
    private volatile ArrayList<String> CACHED_FILES = new ArrayList<>();
    private File dir = new File(PATH);


    @SuppressWarnings("InfiniteLoopStatement")
    public void bigBang() {
        ThreadHelper.getInstance().execute(() -> {
            for (; ; ) {
                File[] files = dir.listFiles((dir1, name) -> name.endsWith(".class"));
                for (File f : files)
                    runInternals(f);
            }
        });
    }

    private void runInternals(@NotNull File f) {
        if (!searchInCache(f.getName())) {
            System.out.println(f.getName());
            CACHED_FILES.add(f.getName());
            ReflexionWrapper.createObjectFromClass(f.getName(), "generated");
        }
    }

    private boolean searchInCache(String file) {
        for (String s : CACHED_FILES)
            if (file.equals(s))
                return true;
        return false;
    }
}
