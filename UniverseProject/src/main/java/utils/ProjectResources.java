package utils;

public class ProjectResources {

    public static final String PROJECT_PATH = "C:\\Users\\sandr\\Documents\\UniverseProject\\src\\main\\java\\";
    public static final String DSL_PATH = PROJECT_PATH + "dsl\\";
    public static final String GENERATED_PATH = PROJECT_PATH + "generated\\";
    public static String getJAVA_CODE_BASE(String clazzName){
        return "    public void run() {\n" +
                "        System.out.println(\"The planet " + clazzName + " was created\");\n" +
                "new Thread(() -> {\n" +
                "                int rTime = 0;\n" +
                "                while (rTime !=this.Distanta) {\n" +
                "                    try {\n" +
                "                        Thread.sleep(1000);\n" +
                "                        rTime++;\n" +
                "                    } catch (InterruptedException ie) {\n" +
                "                        System.out.println(ie.toString());\n" +
                "                    }\n" +
                "                } System.out.println(\"This planet " + clazzName + " was distroyed\");\n" +
                "            }).start();" +
                "    }\n" +
                "}\n";
    }
}
