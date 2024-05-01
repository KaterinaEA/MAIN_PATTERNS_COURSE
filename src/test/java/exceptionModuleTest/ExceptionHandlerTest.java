package exceptionModuleTest;

import exceptionModule1L3.*;
import exceptionModule1L3.LogCommand;
import org.junit.Test;

import java.util.*;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertTrue;
import static org.mockito.Mockito.verify;

public class ExceptionHandlerTest {

    private final NullPointerException nullPointerException;
    private final RuntimeException runtimeException;


    public ExceptionHandlerTest(){

        this.nullPointerException = new NullPointerException();
        this.runtimeException = new RuntimeException();

        ExceptionHandler exceptionHandler = ExceptionHandler.getInstance();

    }

    @Test
    public void testLogCommand() {
        //Реализовать Команду, которая записывает информацию о выброшенном исключении в лог.
        Queue<ICommand> q = new LinkedList<>();

        ICommand command1 = new Command1();

        LogCommand commandLog = new LogCommand(nullPointerException);

        ExceptionHandler.RegisterHandler(command1, nullPointerException, commandLog );

        q.add(command1);

        assertEquals(1, q.size());
        assertTrue(q.contains(command1));

        new CommandProcessor(q).runProcess();

    }

    @Test
    public void testAddQueueLogCommand() {
        // Реализовать обработчик исключения, который ставит Команду, пишущую в лог в очередь Команд.
        Queue<ICommand> q = new LinkedList<>();

        ICommand command2 = new Command2();

        LogCommand commandLog = new LogCommand(runtimeException);
        AddQueue addQueueLogCommand = new AddQueue(q, commandLog);

        ExceptionHandler.RegisterHandler(command2, runtimeException, addQueueLogCommand );

        q.add(command2);

        assertEquals(1, q.size());
        assertTrue(q.contains(command2));

        new CommandProcessor(q).runProcess();

    }

    @Test
    public void testRetryCommand() {
        //Реализовать Команду, которая повторяет Команду, выбросившую исключение.
        Queue<ICommand> q = new LinkedList<>();

        ICommand command3 = new Command3();

        Retry retry = new Retry(command3);

        ExceptionHandler.RegisterHandler(command3, new ArithmeticException(), retry );

        q.add(command3);

        assertEquals(1, q.size());
        assertTrue(q.contains(command3));

        new CommandProcessor(q).runProcess();

    }

    @Test
    public void testRetryRetry() {
        // Реализовать обработчик исключения, который ставит в очередь Команду - повторитель команды, выбросившей исключение.
        Queue<ICommand> q = new LinkedList<>();

        ICommand command3 = new Command3();

        Retry retry = new Retry(command3);

        ExceptionHandler.RegisterHandler(retry, new ArithmeticException(), retry );

        q.add(retry);

        assertEquals(1, q.size());
        assertTrue(q.contains(retry));

        new CommandProcessor(q).runProcess();

    }

    @Test
    public void testRetryAndLogCommand() {
        // при первом выбросе исключения повторить команду, при повторном выбросе исключения записать информацию в лог.
        Queue<ICommand> q = new LinkedList<>();

        ICommand command4 = new Command4();

        Retry retry = new Retry(command4);

        LogCommand logCommand = new LogCommand(nullPointerException);

        AddQueue addQueueLogCommand = new AddQueue(q, logCommand);
        AddQueue addQueueRetry = new AddQueue(q, retry);

        ExceptionHandler.RegisterHandler(command4, nullPointerException, addQueueRetry );

        ExceptionHandler.RegisterHandler(retry, nullPointerException, addQueueLogCommand );

        q.add(command4);

        assertEquals(1, q.size());
        assertTrue(q.contains(command4));

        new CommandProcessor(q).runProcess();

    }

    @Test
    public void testRetryAndLogCommand2() {
        // повторить два раза, потом записать в лог.

        Queue<ICommand> q = new LinkedList<>();

        ICommand command5 = new Command5();

        Retry retry = new Retry(command5);

        Retry2 retry2 = new Retry2(retry);

        LogCommand logCommand = new LogCommand(runtimeException);

        AddQueue addQueueLogCommand = new AddQueue(q, logCommand);
        AddQueue addQueueRetry = new AddQueue(q, retry);
        AddQueue addQueueRetry2 = new AddQueue(q, retry2);

        ExceptionHandler.RegisterHandler(command5, runtimeException, addQueueRetry );

        ExceptionHandler.RegisterHandler(retry, runtimeException, addQueueRetry2 );

        ExceptionHandler.RegisterHandler(retry2, runtimeException, addQueueLogCommand );

        q.add(command5);

        assertEquals(1, q.size());
        assertTrue(q.contains(command5));

        new CommandProcessor(q).runProcess();
    }

}
