package iocModule2L1.Scope;

import exceptionModule1L3.ICommand;

import java.util.HashMap;
import java.util.function.Function;

public class NewScopeCommand implements ICommand {

    private final String _scopeId;

    public NewScopeCommand(String scopeId) {
        _scopeId = scopeId;
    }

    @Override
    public void execute() {
        InitCommand.dictionaryScope.put(_scopeId, new HashMap<String, Function<Object[], Object>>());
    }
}
