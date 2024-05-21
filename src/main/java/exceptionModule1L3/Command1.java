package exceptionModule1L3;

public class Command1 implements ICommand {
    @Override
    public void execute() {

        System.out.println("Command1 executed");
        throw new NullPointerException();
    }
}
