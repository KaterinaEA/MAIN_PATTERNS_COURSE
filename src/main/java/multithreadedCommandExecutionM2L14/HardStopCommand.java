package multithreadedCommandExecutionM2L14;

import exceptionModule1L3.ICommand;

/**
 * Останавливает выполнение команд в потоке немедленно, не дожидаясь
 * выполнения команд, оставшихся в очереди.
 */
public class HardStopCommand implements ICommand {

    private final CommandExecutor executor;

    public HardStopCommand(CommandExecutor executor) {
        this.executor = executor;
    }

    @Override
    public void execute() {
        executor.hardStop();
    }
}
