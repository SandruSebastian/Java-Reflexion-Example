package core.parser;

import core.reflexionapi.ReflexionWrapper;
import net.openhft.compiler.CachedCompiler;
import net.openhft.compiler.CompilerUtils;
import org.jetbrains.annotations.NotNull;
import utils.ProjectResources;
import utils.threading.ThreadHelper;

import javax.swing.*;
import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class CustomFileParser {

    public static void readFile(String path) {
        ArrayList<String> lines = new ArrayList<>();
        JFileChooser input = new JFileChooser();
        String file = path;
        try {

            FileInputStream fstream = new FileInputStream(file);
            DataInputStream in = new DataInputStream(fstream);
            BufferedReader br = new BufferedReader(new InputStreamReader(in));
            String line;
            while ((line = br.readLine()) != null) {
                lines.add(line);
                _processLine(line);
            }
        } catch (IOException fne) {
            System.out.println(fne.toString());
        }
    }

    /**
     * @param line
     * @info Also, this is the entry point for Google's Guava. You can add new features like object dynamic updating or removing
     * using the Event Bus Arhitecture.
     */
    private static void _processLine(@NotNull String line) {
        HashMap<String, String> mHashMap = new HashMap<String, String>();
        String className = "", attrString = "";
        String command = "";
        String[] words = line.split(" ");
        for (int i = 1; i <= words.length; i++) {
            if (words[i - 1].equals("CREATE"))
                className = words[i];
                if (words[i - 1].equals("WITH")) {
                    for (int j = i; j < words.length; j++)
                        attrString += words[j] + " ";
                    break;
                }
            }
            String attr[] = attrString.split(",");
            for (String s : attr) {
                String s1[] = s.split("as");
                mHashMap.put(s1[s1.length - 1],
                        s1[0].split("=")[s1[0].split("=").length - 1].
                                split("\"")[s1[0].split("=")[s1[0].
                                split("=").length - 1].split("\"").length - 2] + "DEL" +
                                s1[0].split("=")[0]);
            }
            _constructClazz(className,mHashMap);
            ReflexionWrapper.compileJavaFile(ProjectResources.GENERATED_PATH + className + ".java");
        }

    private static void _constructClazz(String clazzName, HashMap<String, String> args) {
        try {
            String className = "generated." + clazzName;
            String javaCode = "package generated;\n" +
                    "public class " + clazzName + " implements Runnable {\n" +
                    "public " + clazzName + "(){}\n";
            javaCode += _createDynamicJavaCode(clazzName, args);
            javaCode += ProjectResources.getJAVA_CODE_BASE(clazzName);

            Class aClass = CompilerUtils.CACHED_COMPILER.loadFromJava(className, javaCode);
            Runnable runner = (Runnable) aClass.newInstance();
            File source = new File(ProjectResources.PROJECT_PATH);
            File destination = new File(ProjectResources.PROJECT_PATH);
            final String code = javaCode;

            ThreadHelper.getInstance().execute(() -> {
                try {
                    CachedCompiler JCC = new CachedCompiler(source, destination);
                    JCC.loadFromJava(className, code);
                    Thread.currentThread().interrupt();
                } catch (ClassNotFoundException fni) {
                    System.out.println(fni.toString());
                }
            });

        } catch (InstantiationException | IllegalAccessException | ClassNotFoundException e) {
            System.out.println(e.toString());
        }
    }

    private static String _createDynamicJavaCode(String clazzName, @NotNull HashMap<String, String> args) {
        String constructor = "";
        for (Map.Entry<String, String> entry : args.entrySet()) {
            constructor += _defineField("private", entry.getKey(), entry.getValue());
        }
        constructor += _defineConstructor(args, clazzName);
        return constructor;
    }

    @NotNull
    private static String _defineField(String access, @NotNull String fieldtype, @NotNull String fieldName) {
        if (fieldtype.equals(" String"))
            return access + " " + fieldtype + " " +
                    fieldName.split("DEL")[fieldName.split("DEL").length - 1] +
                    "=" + "\"" + fieldName.split("DEL")[0] + "\"" +
                    ";\n";
        else
            return access + " " + fieldtype + " " +
                    fieldName.split("DEL")[fieldName.split("DEL").length - 1] +
                    "=" + fieldName.split("DEL")[0] +
                    ";\n";
    }

    private static String _defineConstructor(@NotNull HashMap<String, String> args, String clazzName) {
        String constructor = "public " + clazzName + "(";
        for (Map.Entry<String, String> entry : args.entrySet()) {
            constructor += entry.getKey() + " " +
                    entry.getValue().split("DEL")[entry.getValue().split("DEL").length - 1] + ", ";
        }
        StringBuilder build = new StringBuilder(constructor);
        build.deleteCharAt(constructor.length() - 2);
        constructor = build.toString();
        constructor += "){\n";
        for (Map.Entry<String, String> entry : args.entrySet()) {
            if (entry.getKey().equals(" String")) {
                constructor += "this." +
                        entry.getValue().split("DEL")[entry.getValue().split("DEL").length - 1] +
                        "= " + " \" " + entry.getValue().split("DEL")[0] + " \" " + ";\n";
            } else
                constructor += "this." +
                        entry.getValue().split("DEL")[entry.getValue().split("DEL").length - 1] +
                        "=" + entry.getValue().split("DEL")[0] + ";\n";
        }
        constructor += "}\n";
        return constructor;
    }
}