package veneto.ui;

import veneto.command.*;
import veneto.exceptions.DanException;
import veneto.exceptions.DanOperateException;
import veneto.exceptions.DanStorageException;
import veneto.parser.Parser;
import veneto.task.TaskList;

import java.util.Arrays;
import java.util.Scanner;

public class Ui {
    /** Fields */
    private final static String greets = "\nVeneto: \n";
    private final static String[] commands = new String[] {
            "toDo [TASK]",
            "deadline [TASK] /by [DEADLINE(YYYY-MM-DD)]",
            "event [TASK] /from [START_TIME(YYYY-MM-DD)] /to [END_TIME(YYYY-MM-DD)]",
            "mark [TASK_ID]", "unmark [TASK_ID]",
            "list", "bye"
    };

    /** Methods */
    public void showError(DanException e) {
        if (e instanceof DanOperateException) {
            switch (e.getMessage()) {
                case "Unmarked":
                    System.out.println(greets + " 这个没标记过哦！\n");
                    return;
                case "Marked":
                    System.out.println(greets + " 这个已经做完了哦！\n");
                    return;
                case "Add":
                    System.out.println(greets + " 输入格式不对！");
                    System.out.println(" 你可以跟我说：\n" + Arrays.toString(commands) + "\n");
            }
        } else if (e instanceof DanStorageException) {
            System.out.println(greets + " 没找到内存哦 现在重新创建一个！\n");
        } else if (e.getMessage().equals("Invalid Command")) {
            System.out.println(greets + " 输入格式不对！");
            System.out.println(" 你可以跟我说：\n" + Arrays.toString(commands) + "\n");
        } else {
            System.out.println(greets + "???\n");
        }
    }

    public void afterCommand(Command c, TaskList tasks) {
        switch (c.getType()) {
            case "exit":
                goodbye();
                break;
            case "add":
                System.out.println(
                        greets + " 新任务：\n " + c +
                                "!\n 现在有" + tasks.size() + "项任务哦！\n"
                );
                break;
            case "list":
                System.out.println(
                        greets + " 你还有些要做的事情呢 我看看有什么吧！\n" +
                                tasks.toString() + "\n"
                );
                break;
            case "mark":
                System.out.println(
                        greets + " 哟 做完啦？帮你标记好了！\n " + c + "\n"
                );
                break;
            case "unmark":
                System.out.println(
                        greets + " 啊？没做完啊 是不小心手滑了么？\n " + c + "\n"
                );
                break;
            case "delete":
                System.out.println(
                        greets +
                                " 好啦，帮你擦掉了一条任务哦：\n " + c +
                                "\n 现在还剩下" + tasks.size() + "项任务哦！\n"
                );
                break;
        }
    }

    public Command getCommand() {
        String text = new Scanner(System.in).nextLine();
        return Parser.translateCommand(text);
    }

    public void hello() {
        System.out.println(
                greets +
                        " 我是小丹！\n" +
                        " 有什么可以要帮忙可以跟我说！\n"
        );
    }

    public void goodbye() {
        System.out.println(
                greets +
                        " 拜拜啦！下次见！\n"
        );
    }

//    try {
//        chat();
//    } catch (Exception e) {
//        if (e instanceof dan.exceptions.DanException ) {
//            System.out.println(greets + " 你输入的东西不太对哦！");
//        } else if (e instanceof IndexOutOfBoundsException) {
//            System.out.println(greets + " 输入格式不对！");
//        } else if (e instanceof IllegalArgumentException) {
//
//    }
}
