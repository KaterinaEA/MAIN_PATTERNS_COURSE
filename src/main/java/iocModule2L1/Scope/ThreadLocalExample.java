package iocModule2L1.Scope;

import java.util.concurrent.ThreadLocalRandom;

public class ThreadLocalExample {
    public static void main(String[] args) throws InterruptedException {
        // Создаем экземпляр ThreadLocal, который будет хранить целые числа
        ThreadLocal<Integer> threadLocal = ThreadLocal.withInitial(() -> {
            // Инициализация значения, например, с помощью ThreadLocalRandom
            return ThreadLocalRandom.current().nextInt(100);
        });

        // Создаем два потока
        Thread thread1 = new Thread(() -> {
            // Получаем значение из ThreadLocal для текущего потока
            int value = threadLocal.get();
            System.out.println(value);
        });

        Thread thread2 = new Thread(() -> {
            // Получаем значение из ThreadLocal для текущего потока
            int value = threadLocal.get();
            System.out.println(value);
        });

        // Запускаем потоки
        thread1.start();
        thread2.start();

        // Ожидаем завершения потоков
        thread1.join();
        thread2.join();
    }
}
