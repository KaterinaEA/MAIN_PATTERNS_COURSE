package multithreadedCommandExecutionTest;

import multithreadedCommandExecutionM2L14.CommandExecutor;
import org.junit.Test;

public class MultithreadedCommandExecutionTest {

/*    @Test
    public void hardStop() {

        CommandExecutor executor = new CommandExecutor();
        executor.startExecution();

        // Запуск команды
        executor.executeCommand(() -> System.out.println("Команда 1"));
        executor.executeCommand(() -> {
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("Команда 2");
        });

        // Жесткая остановка
        executor.hardStop();

    }*/

/*    @Test
    public void softStop() {

        CommandExecutor executor = new CommandExecutor();
        executor.startExecution();

        // Запуск команды
        executor.executeCommand(() -> System.out.println("Команда 1"));
        executor.executeCommand(() -> {
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("Команда 2");
        });

        // Жесткая остановка
        executor.softStop();

    }*/

}
