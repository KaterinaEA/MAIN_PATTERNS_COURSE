package exceptionModule1L3;

public class Retry implements ICommand {

    private final ICommand c;

    public Retry(ICommand command) {

        c = command;

    }

    @Override
    public void execute() {

        System.out.println("Retrying...");
        c.execute();

    }

}
