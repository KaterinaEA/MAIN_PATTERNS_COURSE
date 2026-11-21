package multithreadedCommandExecutionM2L14;

import exceptionModule1L3.ICommand;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * Выполняет поставленные в очередь команды в одном отдельном потоке.
 * <p>
 * hardStop() останавливает поток немедленно, не дожидаясь команд,
 * оставшихся в очереди. softStop() останавливает поток только после
 * того, как очередь опустеет и все команды, поставленные к этому
 * моменту, будут выполнены.
 */
public class CommandExecutor {

    private final BlockingQueue<ICommand> queue = new LinkedBlockingQueue<>();
    private final AtomicBoolean hardStopRequested = new AtomicBoolean(false);
    private final AtomicBoolean softStopRequested = new AtomicBoolean(false);
    private final CountDownLatch started = new CountDownLatch(1);
    private final CountDownLatch terminated = new CountDownLatch(1);

    public void executeCommand(ICommand command) {
        queue.offer(command);
    }

    public void startExecution() {
        new Thread(this::runLoop).start();
    }

    private void runLoop() {
        started.countDown();
        try {
            while (!hardStopRequested.get() && !(softStopRequested.get() && queue.isEmpty())) {

                ICommand command;
                try {
                    command = queue.poll(100, TimeUnit.MILLISECONDS);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    continue;
                }

                if (command == null) {
                    continue;
                }

                try {
                    command.execute();
                } catch (Exception e) {
                    // Исключение из команды не должно прерывать выполнение потока.
                    e.printStackTrace();
                }
            }
        } finally {
            terminated.countDown();
        }
    }

    /**
     * Блокируется до тех пор, пока поток не запустится, либо пока не истечёт таймаут.
     */
    public boolean awaitStart(long timeout, TimeUnit unit) throws InterruptedException {
        return started.await(timeout, unit);
    }

    /**
     * Блокируется до тех пор, пока поток не завершится, либо пока не истечёт таймаут.
     */
    public boolean awaitTermination(long timeout, TimeUnit unit) throws InterruptedException {
        return terminated.await(timeout, unit);
    }

    public void hardStop() {
        hardStopRequested.set(true);
    }

    public void softStop() {
        softStopRequested.set(true);
    }
}
