package iocModule2L1.Scope;

import exceptionModule1L3.ICommand;

public class SetCurrentScopeCommand implements ICommand {
    private final Object _scopeId;

    public SetCurrentScopeCommand(Object scopeId) {
        _scopeId = scopeId;
    }

    @Override
    public void execute() {
        Object scope = InitCommand.dictionaryScope.getOrDefault(_scopeId, InitCommand.rootScope);
        InitCommand.currentScopes.set(scope);
    }
}
