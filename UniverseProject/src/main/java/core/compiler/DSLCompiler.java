package core.compiler;

import core.parser.CustomFileParser;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import utils.ProjectResources;
import utils.threading.ThreadHelper;

import java.io.File;
import java.util.ArrayList;

public class DSLCompiler {

    private static DSLCompiler ourInstance = new DSLCompiler();
    private volatile ArrayList<String> CACHED_FILES = new ArrayList<>();
    private File dir = new File(ProjectResources.DSL_PATH);

    @Contract(pure = true)
    public static DSLCompiler getInstance() {
        System.out.println("The universe was created");
        return ourInstance;
    }

    private DSLCompiler() {
    }

    @SuppressWarnings("InfiniteLoopStatement")
    public void start() {
        ThreadHelper.getInstance().execute(() -> {
            for (; ; ) {
                File[] files = dir.listFiles((dir1, name) -> name.endsWith(".dsl"));
                for (File f : files)
                    runInternals(f);
            }
        });
    }

    private void runInternals(@NotNull File f) {
        if (!searchInCache(f.getName())) {
            CustomFileParser.readFile(ProjectResources.DSL_PATH + f.getName());
            CACHED_FILES.add(f.getName());
        }
    }

    private boolean searchInCache(String file) {
        for (String s : CACHED_FILES)
            if (file.equals(s))
                return true;
        return false;
    }
}
