package exceptionModuleTest;

import exceptionModule1L3.CommandProcessor;
import exceptionModule1L3.ICommand;
import exceptionModule1L3.ILogger;
import exceptionModule1L3.Logging;
import lspIspModule1L2.move.IMovable;
import lspIspModule1L2.move.Move;
import lspIspModule1L2.move.Vector;
import org.junit.Test;
import org.mockito.Mockito;

public class ExceptionHandlerTest {

    @Test
    public void testLoggingCommand() throws InterruptedException {

        ILogger l = Mockito.mock(ILogger.class);

        ICommand command = new Logging(l); // Создаем экземпляр команды

        new CommandProcessor().run(command);

    }

    @Test
    public void testMoveCommand() throws InterruptedException {

        IMovable m = Mockito.mock(IMovable.class);

        ICommand command = new Move(m); // Создаем экземпляр команды

        new CommandProcessor().run(command);

    }

}
