package exceptionModule1L3;

public class Command5 implements ICommand {
    @Override
    public void execute() {

        System.out.println("Command5 executed");
        throw new RuntimeException();
    }
}
