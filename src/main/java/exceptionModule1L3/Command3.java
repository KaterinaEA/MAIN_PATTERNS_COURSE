package exceptionModule1L3;

public class Command3 implements ICommand {
    @Override
    public void execute() {

        System.out.println("Command3 executed");
        throw new ArithmeticException();
    }
}
