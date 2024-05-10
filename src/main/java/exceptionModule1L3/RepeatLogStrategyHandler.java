package exceptionModule1L3;

import java.util.Queue;

public class RepeatLogStrategyHandler implements IStrategyHandler {

    Queue<ICommand> queue;

    ICommand command;
    Exception exception;

    public RepeatLogStrategyHandler(ICommand c, Exception e, Queue<ICommand> q) {

        command = c;
        exception = e;
        queue = q;

    }

    @Override
    public ICommand strategyHandler() {
        // при первом выбросе исключения повторить команду, при повторном выбросе исключения записать информацию в лог.

        Retry retry = new Retry(command);

        AddQueue addQueueRetry = new AddQueue(queue, retry);

        LogCommand logCommand = new LogCommand(exception);

        queue.add(retry);

        ExceptionHandler.RegisterHandler(retry, exception, logCommand );

        return addQueueRetry;

    }


}
