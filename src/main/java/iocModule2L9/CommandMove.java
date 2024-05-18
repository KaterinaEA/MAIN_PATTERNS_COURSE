package iocModule2L9;

import exceptionModule1L3.ICommand;

public class CommandMove implements ICommand {


    @Override
    public void execute() {
        System.out.println("Move command");
    }
}
