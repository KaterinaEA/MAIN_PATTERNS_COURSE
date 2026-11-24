package iocTest;

import exceptionModule1L3.ICommand;
import iocModule2L1.IoC;
import iocModule2L1.Scope.InitCommand;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.function.Function;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * Проверяет, что скоупы IoC (построенные на ThreadLocal) действительно
 * изолируют потоки друг от друга: несколько потоков одновременно создают
 * свой собственный скоуп, регистрируют в нём одну и ту же зависимость
 * с разным значением и должны получить обратно именно своё значение,
 * а не значение, зарегистрированное другим потоком.
 */
public class ConcurrentScopeTest {

    @Test
    public void differentThreadsDoNotShareScopes() throws Exception {

        new InitCommand().execute();

        int threadCount = 8;
        ExecutorService pool = Executors.newFixedThreadPool(threadCount);

        List<Callable<String>> tasks = new ArrayList<>();

        for (int i = 0; i < threadCount; i++) {
            String scopeId = "ScopeThread" + i;
            String expectedValue = "value-" + i;

            tasks.add(() -> {
                try {
                    ICommand newScope = IoC.resolve("IoC.Scope.New", scopeId);
                    newScope.execute();

                    ICommand setScope = IoC.resolve("IoC.Scope.Current.Set", scopeId);
                    setScope.execute();

                    Function<Object[], Object> strategy = (args) -> expectedValue;
                    ICommand register = IoC.resolve("IoC.register", "Marker", strategy);
                    register.execute();

                    // Даём другим потокам шанс вклиниться между регистрацией и чтением,
                    // чтобы утечка состояния между потоками проявилась, если она есть.
                    Thread.sleep(50);

                    return (String) IoC.resolve("Marker");
                } finally {
                    ICommand clearScope = IoC.resolve("IoC.Scope.Current.Clear");
                    clearScope.execute();
                }
            });
        }

        List<Future<String>> results = pool.invokeAll(tasks);
        pool.shutdown();
        assertTrue(pool.awaitTermination(10, TimeUnit.SECONDS));

        for (int i = 0; i < threadCount; i++) {
            assertEquals("Поток " + i + " должен получить своё собственное значение, а не чужое",
                    "value-" + i, results.get(i).get());
        }
    }
}
