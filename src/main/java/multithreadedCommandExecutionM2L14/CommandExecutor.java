package multithreadedCommandExecutionM2L14;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;

public class CommandExecutor {
    private BlockingQueue<Runnable> queue;
    private AtomicBoolean shutdownRequested;

    public void CommandExecutor() {
        this.queue = new LinkedBlockingQueue<>();
        this.shutdownRequested = new AtomicBoolean(false);
    }

    public void executeCommand(Runnable command) {
        queue.offer(command);
    }

    public void startExecution() {
        new Thread(() -> {
            while (!shutdownRequested.get()) {
                try {
                    Runnable command = queue.poll(100, TimeUnit.MILLISECONDS);
                    if (command != null) {
                        command.run();
                    }
                } catch (InterruptedException e) {
                    // Прерывание не должно влиять на выполнение потока
                    e.printStackTrace();
                }
            }
        }).start();
    }

    public void hardStop() {
        shutdownRequested.set(true);
    }

    public void softStop() {
        // Закрываем пул потоков, ожидая завершения всех задач
        queue.clear();
        shutdownRequested.set(true);
    }
}
