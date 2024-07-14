package iocModule2L1.Scope;

import exceptionModule1L3.ICommand;

public class ClearCurrentScopeCommand implements ICommand {

    public ClearCurrentScopeCommand () {
        InitCommand.currentScopes.set(null);
    }

    @Override
    public void execute() {
        InitCommand.currentScopes.set(null);
    }
}
