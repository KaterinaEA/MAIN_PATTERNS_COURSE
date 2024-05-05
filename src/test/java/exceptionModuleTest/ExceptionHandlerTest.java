package exceptionModuleTest;

import exceptionModule1L3.*;
import exceptionModule1L3.LogCommand;
import org.junit.Test;
import org.mockito.Mockito;

import java.util.*;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertTrue;
import static org.mockito.Mockito.verify;

public class ExceptionHandlerTest {

    private final Queue<ICommand> q;
    private final Command1 command1;
    private final Command2 command2;
    //private final LogCommand commandLog;
    private final NullPointerException nullPointerException;
    private final RuntimeException runtimeException;


    public ExceptionHandlerTest(){

        this.nullPointerException = new NullPointerException();
        this.runtimeException = new RuntimeException();

        //this.commandLog = new LogCommand(nullPointerException);// Создаем экземпляр команды

        this.command1 = Mockito.mock(Command1.class);
        this.command2 = new Command2();

        this.q = new LinkedList<>();


        //ExceptionHandler.RegisterHandler(command2, runtimeException, commandLog );


    }

    @Test
    public void testLogCommand() {

        //Реализовать Команду, которая записывает информацию о выброшенном исключении в лог.

        LogCommand commandLog = new LogCommand(runtimeException);

        ExceptionHandler.RegisterHandler(command2, runtimeException, commandLog );

        q.add(command2);

        assertEquals(1, q.size());
        assertTrue(q.contains(command2));

        new CommandProcessor(q).runProcess();

    }

    @Test
    public void testAddQueueLogCommand() {
        // Реализовать обработчик исключения, который ставит Команду, пишущую в лог в очередь Команд.

        LogCommand commandLog = new LogCommand(runtimeException);
        AddQueueLogCommand addQueueLogCommand = new AddQueueLogCommand(q, commandLog);

        ExceptionHandler.RegisterHandler(command1, runtimeException, addQueueLogCommand );

        q.add(addQueueLogCommand);

        assertEquals(1, q.size());
        assertTrue(q.contains(addQueueLogCommand));

        new CommandProcessor(q).runProcess();

    }

    @Test
    public void testRetryCommand() {
        //Реализовать Команду, которая повторяет Команду, выбросившую исключение.

        ICommand command3 = new Command3();

        Retry retry = new Retry(command3);

        ExceptionHandler.RegisterHandler(command3, nullPointerException, retry );

        q.add(command3);

        assertEquals(1, q.size());
        assertTrue(q.contains(command3));

        new CommandProcessor(q).runProcess();

    }

    @Test
    public void testRetryRetry() {
        // Реализовать обработчик исключения, который ставит в очередь Команду - повторитель команды, выбросившей исключение.

        ICommand command3 = new Command3();

        Retry retry = new Retry(command3);

        ExceptionHandler.RegisterHandler(retry, nullPointerException, retry );

        q.add(retry);

        assertEquals(1, q.size());
        assertTrue(q.contains(retry));

        new CommandProcessor(q).runProcess();

    }

    @Test
    public void testRetryAndLogCommand() {
        // при первом выбросе исключения повторить команду, при повторном выбросе исключения записать информацию в лог.

        ICommand command3 = new Command3();

        AddQueueLogCommand addQueueLogCommand = new AddQueueLogCommand(q, command3);

        Retry retry = new Retry(command3);

        ExceptionHandler.RegisterHandler(retry, nullPointerException, retry );

        q.add(retry);

        assertEquals(1, q.size());
        assertTrue(q.contains(retry));

        new CommandProcessor(q).runProcess();

    }

}
