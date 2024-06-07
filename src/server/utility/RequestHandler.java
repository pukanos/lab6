package server.utility;

import common.*;

public class RequestHandler {
    private CommandManager commandManager;
    public RequestHandler(CommandManager commandManager) {
        this.commandManager = commandManager;
    }

    public String handle(Request request){ // return String
        commandManager.addToHistory(request.getCommandName());
//        System.out.println(request);
        execute(request.getCommandName(), request.getCommandArgument(), request.getCommandObject());
        String responseMessage = ResponsePrinter.getString();
        ResponsePrinter.clear();
        return responseMessage; // return message
    }

    private void execute(String commandName, String commandArgument, Object commandObject){
        switch (commandName) {
            case "" -> {}
            case "add" -> commandManager.add(commandArgument, commandObject);
            case "add_if_max" -> commandManager.addIfMax(commandArgument, commandObject);
            case "clear" -> commandManager.clear(commandArgument, commandObject);
            case "execute_script" -> commandManager.executeScriptCommand(commandArgument, commandObject);
            case "exit" -> commandManager.exit(commandArgument, commandObject);
            case "filter_less_than_director" -> commandManager.filterLessThanDirector(commandArgument, commandObject);
            case "help" -> commandManager.help(commandArgument, commandObject);
            case "history" -> commandManager.history(commandArgument, commandObject);
            case "info" -> commandManager.info(commandArgument, commandObject);
            case "print_ascending" -> commandManager.printAscending(commandArgument, commandObject);
            case "print_unique_oscars_count" -> commandManager.printUniqueOscarsCount(commandArgument, commandObject);
            case "remove_by_id" -> commandManager.removeById(commandArgument, commandObject);
            case "remove_greater" -> commandManager.removeGreater(commandArgument, commandObject);
            case "save" -> commandManager.save(commandArgument, commandObject);
            case "show" -> commandManager.show(commandArgument, commandObject);
            case "update" -> commandManager.update(commandArgument, commandObject);
            default -> commandManager.noSuchCommand(commandArgument, commandObject);
        }
    }
}
