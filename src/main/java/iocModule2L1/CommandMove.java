package iocModule2L1;

import exceptionModule1L3.ICommand;

public class CommandMove implements ICommand {


    @Override
    public void execute() {
        System.out.println("Move command");
    }
}
