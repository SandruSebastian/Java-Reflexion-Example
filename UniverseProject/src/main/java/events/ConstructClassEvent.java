package events;

import java.util.HashMap;

public class ConstructClassEvent {

    private String className;
    private HashMap<String, String> args = new HashMap<>();

    public ConstructClassEvent(String className, HashMap<String, String> args) {
        this.className = className;
        this.args = args;
    }

    public String getClassName() {
        return className;
    }

    public HashMap<String, String> getArgs(){
        return args;
    }

}
