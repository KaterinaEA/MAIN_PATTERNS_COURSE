package multithreadedCommandExecutionTest;

import multithreadedCommandExecutionM2L14.CommandExecutor;
import multithreadedCommandExecutionM2L14.HardStopCommand;
import multithreadedCommandExecutionM2L14.SoftStopCommand;
import multithreadedCommandExecutionM2L14.StartExecutionCommand;
import org.junit.Test;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class MultithreadedCommandExecutionTest {

    @Test
    public void startCommandStartsWorkerThread() throws InterruptedException {

        CommandExecutor executor = new CommandExecutor();

        new StartExecutionCommand(executor).execute();

        assertTrue("После команды старта поток должен запуститься (сигнал через CountDownLatch, без Thread.sleep)",
                executor.awaitStart(1, TimeUnit.SECONDS));
    }

    @Test
    public void hardStopTerminatesThreadWithoutDrainingQueue() throws InterruptedException {

        CommandExecutor executor = new CommandExecutor();
        new StartExecutionCommand(executor).execute();
        assertTrue(executor.awaitStart(1, TimeUnit.SECONDS));

        CountDownLatch blocker = new CountDownLatch(1);
        AtomicInteger executed = new AtomicInteger();

        // Первая команда надолго занимает поток — остальные гарантированно остаются в очереди.
        executor.executeCommand(() -> {
            try {
                blocker.await(5, TimeUnit.SECONDS);
            } catch (InterruptedException ignored) {
                Thread.currentThread().interrupt();
            }
        });
        for (int i = 0; i < 5; i++) {
            executor.executeCommand(executed::incrementAndGet);
        }

        new HardStopCommand(executor).execute();
        blocker.countDown();

        assertTrue("Поток должен завершиться после hardStop",
                executor.awaitTermination(2, TimeUnit.SECONDS));
        assertEquals("hardStop не должен выполнять команды, оставшиеся в очереди",
                0, executed.get());
    }

    @Test
    public void softStopWaitsForQueueToDrainBeforeTerminating() throws InterruptedException {

        CommandExecutor executor = new CommandExecutor();
        new StartExecutionCommand(executor).execute();
        assertTrue(executor.awaitStart(1, TimeUnit.SECONDS));

        int commandCount = 20;
        AtomicInteger executed = new AtomicInteger();
        for (int i = 0; i < commandCount; i++) {
            executor.executeCommand(executed::incrementAndGet);
        }

        new SoftStopCommand(executor).execute();

        assertTrue("Поток должен завершиться после softStop, как только очередь опустеет",
                executor.awaitTermination(2, TimeUnit.SECONDS));
        assertEquals("softStop должен дождаться выполнения всех команд, поставленных в очередь",
                commandCount, executed.get());
    }

    @Test
    public void exceptionFromCommandDoesNotStopTheThread() throws InterruptedException {

        CommandExecutor executor = new CommandExecutor();
        new StartExecutionCommand(executor).execute();
        assertTrue(executor.awaitStart(1, TimeUnit.SECONDS));

        AtomicInteger executed = new AtomicInteger();

        executor.executeCommand(() -> {
            throw new RuntimeException("Команда сломалась");
        });
        executor.executeCommand(executed::incrementAndGet);

        new SoftStopCommand(executor).execute();

        assertTrue(executor.awaitTermination(2, TimeUnit.SECONDS));
        assertEquals("Исключение из одной команды не должно мешать выполнению следующих",
                1, executed.get());
    }
}
