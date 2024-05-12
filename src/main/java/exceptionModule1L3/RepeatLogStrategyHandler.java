package exceptionModule1L3;

import java.util.Queue;

public class RepeatLogStrategyHandler implements ICommand {

    Queue<ICommand> queue;

    ICommand command;
    Exception exception;

    public RepeatLogStrategyHandler(ICommand c, Exception e, Queue<ICommand> q) {

        command = c;
        exception = e;
        queue = q;

    }

    @Override
    public void execute() {

        // при первом выбросе исключения повторить команду, при повторном выбросе исключения записать информацию в лог.

        Retry retry = new Retry(command);

        LogCommand logCommand = new LogCommand(exception);

        queue.add(retry);

        ExceptionHandler exceptionHandler = ExceptionHandler.getInstance(queue);

        exceptionHandler.RegisterHandler(retry, exception, logCommand );

    }
}
