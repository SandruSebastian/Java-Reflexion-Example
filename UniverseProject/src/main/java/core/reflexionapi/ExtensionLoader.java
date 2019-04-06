package core.reflexionapi;

import java.io.File;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;

public class ExtensionLoader<C> {

    public C LoadClass(String directory, String classpath, Class<C> parentClass) throws ClassNotFoundException {
        File pluginsDir = new File(System.getProperty("user.dir") + directory);
        for (File jar : pluginsDir.listFiles()) {
            try {
                ClassLoader loader = URLClassLoader.newInstance(
                        new URL[]{jar.toURL()},
                        getClass().getClassLoader()
                );
                Class<?> clazz = Class.forName(classpath, true, loader);
                Class<? extends C> newClass = clazz.asSubclass(parentClass);
                Constructor<? extends C> constructor = newClass.getConstructor();
                return constructor.newInstance();

            } catch (MalformedURLException | NoSuchMethodException | InvocationTargetException | IllegalAccessException
                    | InstantiationException e) {
                e.printStackTrace();
            }
        }
        throw new ClassNotFoundException("Class " + classpath
                + " wasn't found in directory " + System.getProperty("user.dir") + directory);
    }
}