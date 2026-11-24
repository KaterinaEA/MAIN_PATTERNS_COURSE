package multithreadedCommandExecutionM2L14;

import exceptionModule1L3.ICommand;

public class StartExecutionCommand implements ICommand {

    private final CommandExecutor executor;

    public StartExecutionCommand(CommandExecutor executor) {
        this.executor = executor;
    }

    @Override
    public void execute() {
        executor.startExecution();
    }
}
