package multithreadedCommandExecutionM2L14;

import exceptionModule1L3.ICommand;

/**
 * Останавливает выполнение команд в потоке только после того, как
 * очередь опустеет и все поставленные в неё команды будут выполнены.
 */
public class SoftStopCommand implements ICommand {

    private final CommandExecutor executor;

    public SoftStopCommand(CommandExecutor executor) {
        this.executor = executor;
    }

    @Override
    public void execute() {
        executor.softStop();
    }
}
