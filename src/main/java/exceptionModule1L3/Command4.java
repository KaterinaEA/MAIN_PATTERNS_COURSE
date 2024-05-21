package exceptionModule1L3;

public class Command4 implements ICommand {
    @Override
    public void execute() {

        System.out.println("Command4 executed");
        throw new NullPointerException();
    }
}

