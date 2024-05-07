package exceptionModule1L3;

import java.util.Queue;

public class AddQueue implements ICommand {

    private final Queue<ICommand> q;

    private final ICommand c;

    public AddQueue(Queue<ICommand> queue, ICommand commandLog) {

        this.q = queue;
        this.c = commandLog;

    }

    @Override
    public void execute() {

        q.add(c);

    }

}
