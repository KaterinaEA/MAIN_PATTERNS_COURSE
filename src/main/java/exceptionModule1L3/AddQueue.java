package exceptionModule1L3;

import java.util.Queue;

public class AddQueue implements ICommand {

    private final Queue<ICommand> q;

    private final ICommand c;

    public AddQueue(Queue<ICommand> queue, ICommand command) {

        this.q = queue;
        this.c = command;

    }

    @Override
    public void execute() {

        q.add(c);

    }

}
