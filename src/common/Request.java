package common;

import java.io.Serializable;

public class Request implements Serializable {
    private static final long serialVersionUID = 12L;
    private String commandName;
    private String commandArgument;
    private Serializable commandObject;
    private boolean a;


    public void setCommandObject(Serializable commandObject) {
        this.commandObject = commandObject;
    }

    public Request(String commandName, String commandArgument, Serializable commandObject) {
        this.commandName = commandName;
        this.commandArgument = commandArgument;
        this.commandObject = commandObject;
    }
    public Request(String commandName, String commandArgument) {
        this.commandName = commandName;
        this.commandArgument = commandArgument;
        commandObject = null;
    }
    public Request() {
        this.commandName = "";
        this.commandArgument = "";
        commandObject = null;
    }
    public void setCommandName(String commandName) {
        this.commandName = commandName;
    }
    public String getCommandName() {
        return commandName;
    }
    public String getCommandArgument() {
        return commandArgument;
    }
    public Object getCommandObject() {
        return commandObject;
    }
    public boolean isEmpty(){
        return commandName.isEmpty() && commandArgument.isEmpty() && commandObject == null;
    }
    @Override
    public String toString() {
        return "Request [commandName=" + commandName + ", commandArgument=" + commandArgument + ", commandObject=" + commandObject + "]";
    }

    public boolean isA() {
        return a;
    }

    public void setA(boolean a) {
        this.a = a;
    }
}
