package exceptionModuleTest;

import exceptionModule1L3.*;
import exceptionModule1L3.LogCommand;
import org.junit.Test;

import java.util.*;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertTrue;

public class ExceptionHandlerTest {

    private final NullPointerException nullPointerException;
    private final RuntimeException runtimeException;


    public ExceptionHandlerTest(){

        this.nullPointerException = new NullPointerException();
        this.runtimeException = new RuntimeException();

    }

    @Test
    public void testLogCommand() {
        //Реализовать Команду, которая записывает информацию о выброшенном исключении в лог.
        Queue<ICommand> q = new LinkedList<>();

        ICommand command1 = new Command1();

        LogCommand commandLog = new LogCommand(nullPointerException);

        ExceptionHandler exceptionHandler = ExceptionHandler.getInstance(q);

        exceptionHandler.RegisterHandler(command1, nullPointerException, commandLog );

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

        ExceptionHandler exceptionHandler = ExceptionHandler.getInstance(q);

        exceptionHandler.RegisterHandler(command2, runtimeException, addQueueLogCommand );

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

        ExceptionHandler exceptionHandler = ExceptionHandler.getInstance(q);

        exceptionHandler.RegisterHandler(command3, new ArithmeticException(), retry );

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

        ExceptionHandler exceptionHandler = ExceptionHandler.getInstance(q);

        exceptionHandler.RegisterHandler(retry, new ArithmeticException(), retry );

        q.add(command3);

        assertEquals(1, q.size());
        assertTrue(q.contains(command3));

        new CommandProcessor(q).runProcess();

    }

    @Test
    public void testRetryAndLogCommand() {
        // при первом выбросе исключения повторить команду, при повторном выбросе исключения записать информацию в лог.
        Queue<ICommand> q = new LinkedList<>();

        ICommand command4 = new Command4();

        ICommand repeat = new RepeatLogStrategyHandler(command4, nullPointerException, q);

        ExceptionHandler exceptionHandler = ExceptionHandler.getInstance(q);

        exceptionHandler.RegisterHandler(command4, nullPointerException, repeat);

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

        ICommand repeat2 = new Repeat2LogStrategyHandler(command5, runtimeException, q);

        ExceptionHandler exceptionHandler = ExceptionHandler.getInstance(q);

        exceptionHandler.RegisterHandler(command5, runtimeException, repeat2 );

        q.add(command5);

        assertEquals(1, q.size());
        assertTrue(q.contains(command5));

        new CommandProcessor(q).runProcess();
    }

}
