package core.reflexionapi;

import utils.ProjectResources;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;

public class ReflexionWrapper {

    @Deprecated
    public static void createObjectFromJava(String clazzName, String packageName) {
        String className = packageName + "." + clazzName;
        try {
            Class c = Class.forName(className);
            Object ob = c.newInstance();
            Method[] methods = c.getMethods();
            methods[0].invoke(ob, null);
            c.getConstructors();
            System.out.println("HERE");
            Class MyPlugin = c.getClass();
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | InvocationTargetException e) {
            System.out.println(e.toString());
        }
    }

    public static void createObjectFromClass(String clazzName, String packageName) {
        try {
            String classNameToLoad = packageName + "." + clazzName.split(".class")[0];
            Object o = new URLClassLoader(
                    new URL[]{new File(ProjectResources.GENERATED_PATH).toURI().toURL()}
            ).loadClass(classNameToLoad).newInstance();
            Method[] methods = o.getClass().getMethods();
            methods[0].invoke(o, null);
            o.getClass().getConstructors();
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | MalformedURLException
                | InvocationTargetException e) {
            System.out.println(e.toString());
        }
    }


    public static void compileJavaFile(String className) {
        try {
            System.out.println(className);
            String command = "javac " + className;
            Process proc = Runtime.getRuntime().exec(command);
            BufferedReader reader =
                    new BufferedReader(new InputStreamReader(proc.getInputStream()));
            String line = "";
            while ((line = reader.readLine()) != null) {
                System.out.print(line + "\n");
            }
            proc.waitFor();
        } catch (Exception e) {
            System.out.println(e.toString());
        }
    }
}
