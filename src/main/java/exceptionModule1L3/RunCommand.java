package exceptionModule1L3;

import lspIspModule1L2.move.IMovable;
import lspIspModule1L2.move.Move;
import lspIspModule1L2.move.Vector;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

public class RunCommand {

    // Создаем blocking queue с максимальным размером 100
    //BlockingQueue<Integer> queue = new ArrayBlockingQueue<>(100);

    public void test() {

        IMovable m = new IMovable() {
            @Override
            public Vector getPosition() {
                return null;
            }

            @Override
            public Vector getVelocity() {
                return null;
            }

            @Override
            public void setPosition(Vector newPosition) {

            }
        };

        ICommand command = new Move(m); // Создаем экземпляр команды

        try {
            command.execute(); // Вызываем метод Execute команды
        } catch (Exception ex) {// Перехватываем любые исключения
            new ExceptionHandler().handle(command, ex).execute();
        }

    }



}
