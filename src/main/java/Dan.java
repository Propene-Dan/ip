import java.util.Arrays;
import java.util.Scanner;

public class Dan {
    private final static String greets = "\nDan: \n";
    private static MyList tasks = new MyList(100);


    public static void main(String[] args) {
        hello();
        chat();
        goodbye();
    }



    private static void chat() {
        String text;
        String[] texts;
        String command;
        a: while (true) {
            text = new Scanner(System.in).nextLine();
            texts = text.split(" ");
            System.out.println(Arrays.toString(texts));
            command = texts[0].toLowerCase();
            switch (command) {
                case "bye" :
                    break a;
                case "list" :
                    list(); break;
                case "mark" :
                    if (!mark(texts[1]))
                        continue;
                    break;
                case "unmark" :
                    if (!unmark(texts[1]))
                        continue;
                    break;
                case "todo" :
                    addTask(text, 1);
                    break;
                case "deadline" :
                    addTask(text, 2);
                    break;
                case "event" :
                    addTask(text, 3);
                    break;
                default:
                    System.out.println("?");
            }
        }
    }


    private static void list() {
        System.out.println(
                greets + " 你还有些要做的事情呢 我看看有什么吧！\n" +
                        tasks.toString() + "\n"
        );
    }

    private static boolean mark(String taskId) {
        Task currTask = markTask(taskId, 0);
        if (currTask == null) {
            System.out.println(greets + " 这个已经做完了哦！\n");
            return false;
        }
        System.out.println(
                greets + " 哟 做完啦？帮你标记好了！\n " +
                currTask + "\n"
        );
        return true;
    }

    private static boolean unmark(String taskId) {
        Task currTask = markTask(taskId, 1);
        if (currTask == null) {
            System.out.println(greets + " 这个没标记过哦！\n");
            return false;
        }
        System.out.println(
                greets + " 啊？没做完啊 是不小心手滑了么？\n " +
                        currTask.toString() + "\n"
        );
        return true;
    }

    public static void addTask(String text, int id) throws NullPointerException {
        String[] texts = text.split("/");
        for (int i = 0; i < texts.length; i++) {
            texts[i] = texts[i].trim();
        }
        Task newTask = null;
        String description;
        switch (id) {
            case 1 :
                description = texts[0].substring(5);
                newTask = new ToDo(description);
                break;
            case 2 :
                description = texts[0].substring(9);
                String deadline = texts[1].substring(3);
                newTask = new Deadline(description, deadline);
                break;
            case 3 :
                description = texts[0].substring(5);
                String start = texts[1].substring(5);
                String end = texts[2].substring(3);
                newTask = new Event(description, start, end);
                break;
        }
        tasks.add(newTask);
        System.out.println(
                greets + " 新任务：\n " + newTask +
                "!\n 现在有" + tasks.size() + "项任务哦！\n"
        );
    }

    private static Task markTask(String taskId, int funcId) {
        Task currTask = tasks.get(Integer.parseInt(taskId) - 1);
        boolean succ = currTask.mark(funcId);
        if (!succ) {
            return null;
        }
        return currTask;
    }

    public static void hello() {
        System.out.println(
                greets +
                " 我是小丹！\n" +
                " 有什么可以要帮忙可以跟我说！\n"
        );
    }
    public static void goodbye() {
        System.out.println(
                greets +
                " 拜拜啦！下次见！\n"
        );
    }
}
