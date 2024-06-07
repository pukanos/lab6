package server.commands;
/**
 * Интерфейс всех команд.
 */
public interface ICommand {

    /**
     * Выполняет команду.
     * @param commandArgument аргумент команды
     * @param commandObject объект, с которым работает команда
     * @return true, если команда была выполнена корректно, false - иначе
     */
    boolean execute(String commandArgument, Object commandObject);

    /**
     * Возвращает название команды.
     * @return название
     */
    String getCommandName();

    /**
     * Возвращает описание команды.
     * @return описание
     */
    String getCommandDescription();

    /**
     * Возвращает объект, с которым работает команда.
     * @return объект
     */
    Object getCommandObject();
}
