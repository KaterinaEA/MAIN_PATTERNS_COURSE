package iocModule2L9;

import exceptionModule1L3.ICommand;

import java.util.HashMap;
import java.util.function.Function;

public class Init {

    public void init() {

        ICommand refCommandMove;

        refCommandMove = () -> {

            ICommand commandMove = new CommandMove();

            commandMove.execute();
        };

        IoC.register( "CommandMove"
                , refCommandMove);

    }
}
