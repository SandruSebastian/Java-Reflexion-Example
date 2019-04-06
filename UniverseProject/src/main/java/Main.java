import core.compiler.DSLCompiler;

public class Main {
    public static void main(String[] args) {

        DSLCompiler.getInstance().start();
        Universe.getInstance().bigBang();

    }
}
