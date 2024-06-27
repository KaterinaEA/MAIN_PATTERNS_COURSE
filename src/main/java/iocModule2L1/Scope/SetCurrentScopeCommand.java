package iocModule2L1.Scope;

import exceptionModule1L3.ICommand;

public class SetCurrentScopeCommand implements ICommand {
    private Object _scope;

    public SetCurrentScopeCommand(Object scope) {
        _scope = scope;
    }

    @Override
    public void execute() {
        InitCommand.currentScopes.set(_scope);
    }
}
