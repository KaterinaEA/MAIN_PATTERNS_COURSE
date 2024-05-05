package exceptionModule1L3;

import java.util.Queue;

public class AddQueueLogCommand implements ICommand {

    private final Queue<ICommand> q;

    private final ICommand c;

    public AddQueueLogCommand(Queue<ICommand> queue, ICommand commandLog) {

        this.q = queue;
        this.c = commandLog;

    }

    @Override
    public void execute() {

        q.add(c);

    }

}
