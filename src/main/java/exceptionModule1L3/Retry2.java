package exceptionModule1L3;

public class Retry2 implements ICommand {

    private final ICommand c;

    public Retry2(ICommand command) {

        c = command;

    }

    @Override
    public void execute() {

        System.out.println("RetryNotComplete...");
        c.execute();

    }

}