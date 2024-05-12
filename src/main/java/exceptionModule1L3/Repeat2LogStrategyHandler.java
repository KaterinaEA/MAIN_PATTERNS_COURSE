package exceptionModule1L3;

import java.util.Queue;

public class Repeat2LogStrategyHandler implements ICommand{

    Queue<ICommand> queue;

    ICommand command;
    Exception exception;

    public Repeat2LogStrategyHandler(ICommand c, Exception e, Queue<ICommand> q) {

        command = c;
        exception = e;
        queue = q;

    }

    @Override
    public void execute() {
        // повторить два раза, потом записать в лог.

        Retry retry = new Retry(command);

        Retry2 retry2 = new Retry2(retry);

        LogCommand logCommand = new LogCommand(exception);

        AddQueue addQueueRetry2 = new AddQueue(queue, retry2);

        queue.add(retry);

        ExceptionHandler exceptionHandler = ExceptionHandler.getInstance(queue);

        exceptionHandler.RegisterHandler(retry, exception, addQueueRetry2 );

        exceptionHandler.RegisterHandler(retry2, exception, logCommand );

    }
}
