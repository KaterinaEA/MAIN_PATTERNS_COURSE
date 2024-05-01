package exceptionModule1L3;

public interface IExceptionHandler {
    ICommand handle(ICommand command, Exception exception);
}
