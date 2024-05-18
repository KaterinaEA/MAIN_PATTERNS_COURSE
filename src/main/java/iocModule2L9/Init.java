package iocModule2L9;

import exceptionModule1L3.ICommand;

import java.util.HashMap;
import java.util.function.Function;

public class Init implements ICommand {

    public HashMap<String, Function<String, Object> > mapDependency = new HashMap<>();

    @Override
    public void execute() {

        IoC.resolve("IoC.Register"
                , "CommandMove"
                , (args) -> {
                    return new CommandMove();
                }).execute();

/*        IoC.Resolve<ICommand>(
                "IoC.Register"
                , "A"
                , (object[] args) => {new B();}
        ).Execute;*/
    }
}
