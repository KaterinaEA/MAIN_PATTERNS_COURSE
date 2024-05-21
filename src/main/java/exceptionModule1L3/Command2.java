package exceptionModule1L3;

public class Command2 implements ICommand {
    @Override
    public void execute() {
        System.out.println("Command2 executed");

        throw new RuntimeException();
    }
}
