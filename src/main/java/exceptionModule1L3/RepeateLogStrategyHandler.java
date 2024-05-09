package exceptionModule1L3;

import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;

public class RepeateLogStrategyHandler implements IStrategyHandler {

    Queue<ICommand> queue = new LinkedList<>();

    ICommand command;
    Exception exception;

    public RepeateLogStrategyHandler(ICommand c, Exception e, Queue<ICommand> q) {

        command = c;
        exception = e;
        queue = q;

    }

    @Override
    public ICommand strategyHandler() {



    }


}
