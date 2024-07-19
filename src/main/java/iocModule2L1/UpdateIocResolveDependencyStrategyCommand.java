package iocModule2L1;

import exceptionModule1L3.ICommand;

import java.util.function.Function;

public class UpdateIocResolveDependencyStrategyCommand implements ICommand {

    Function<Function<Object[], Object>, Function<Object[],Object>> _updateIocStrategy;

    public UpdateIocResolveDependencyStrategyCommand(Function<Function<Object[], Object>, Function<Object[],Object>> updater) {
        _updateIocStrategy = updater;
    }

    @Override
    public void execute() {
        //IoC.strategy = _updateIocStrategy;
    }



}
