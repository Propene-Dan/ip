package veneto.parser;

import veneto.command.*;
import veneto.exceptions.VenetoException;
import veneto.task.Deadline;
import veneto.task.Event;
import veneto.task.Task;
import veneto.task.ToDo;

public class Parser {
    /* Fields */
    public static final int FIND_COMMAND_START_INDEX = 5;

    /* Methods */
    /**
     * translate user put to commands that need to operate
     * @param text the input from user
     * @return the Command that the user wants to operate
     */
    public static Command translateCommand(String text) {
        String[] texts = text.split(" ");
        String command = texts[0].toLowerCase();
        switch (command) {
            case "bye":
                return new ExitCommand();
            case "list":
                return new ListCommand();
            case "mark":
                return new MarkCommand(Integer.parseInt(texts[1]), 1);
            case "unmark":
                return new MarkCommand(Integer.parseInt(texts[1]), 0);
            case "todo":
                return prepareAdd(text, 1);
            case "deadline":
                return prepareAdd(text, 2);
            case "event":
                return prepareAdd(text, 3);
            case "delete":
                return new DeleteCommand(Integer.parseInt(texts[1]));
            case "find":
                return prepareFind(text);
            default:
                throw new VenetoException("Invalid Command");
        }
    }
    
    private static Command prepareFind(String text) {
        String keyword = text.substring(FIND_COMMAND_START_INDEX);
        return new FindCommand(keyword);
    }

    /**
     * generate AddCommand according to the user input
     * @param text the details about the task
     * @param funcId indicates the type of task
     * @return the command that the user want to operate
     */
    private static Command prepareAdd(String text, int funcId) {
        String[] texts = text.split("/");
        for (int i = 0; i < texts.length; i++) {
            texts[i] = texts[i].trim();
        }
        Task newTask = null;
        String description;
        switch (funcId) {
            case 1:
                description = texts[0].substring(5);
                newTask = new ToDo(description);
                break;
            case 2:
                description = texts[0].substring(9);
                String deadline = texts[1].substring(3);
                newTask = new Deadline(description, deadline);
                break;
            case 3:
                description = texts[0].substring(5);
                String start = texts[1].substring(5);
                String end = texts[2].substring(3);
                newTask = new Event(description, start, end);
                break;
        }
        return new AddCommand(newTask);
    }
}
