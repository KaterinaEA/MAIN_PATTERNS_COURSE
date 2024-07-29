package multithreadedCommandExecutionM2L14;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class MultiThreadedCommands {
    public static void main(String[] args) {
        // Создаем пул потоков с фиксированным размером
        ExecutorService executor = Executors.newFixedThreadPool(4);

        // Задачи, которые хотим выполнить параллельно
        Runnable command1 = () -> System.out.println("Выполняется команда 1");
        Runnable command2 = () -> System.out.println("Выполняется команда 2");
        Runnable command3 = () -> System.out.println("Выполняется команда 3");
        Runnable command4 = () -> System.out.println("Выполняется команда 4");

        // Добавляем задачи в пул потоков
        executor.execute(command1);
        executor.execute(command2);
        executor.execute(command3);
        executor.execute(command4);

        // Ждем завершения всех задач
        executor.shutdown();
        try {
            if (!executor.awaitTermination(1, TimeUnit.MINUTES)) {
                System.out.println("Некоторые задачи не завершились в течение минуты.");
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
