package exceptionModule1L3;

import lspIspModule1L2.move.IMovable;
import lspIspModule1L2.move.Move;
import lspIspModule1L2.move.Vector;

import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class CommandProcessor {

    // Создаем blocking queue с максимальным размером 100
    BlockingQueue<ICommand> q = new LinkedBlockingQueue<>(100);

    public void run(ICommand command) throws InterruptedException {

        q.put(command);

        while (!q.isEmpty()) {

            var c = q.take();

            try {

                c.execute();

            } catch (Exception e) {
                new ExceptionHandler().handle(command, e).execute();
                //IoC.Resolve<ICommand>("ExceptionHandler.Handle", c, e).Execute();
            }

        }

    }

}
