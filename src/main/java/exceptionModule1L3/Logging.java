package exceptionModule1L3;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class Logging implements ICommand {
    private final ILogger l;

    @Override
    public void execute() {

        l.log(l.getLogCommand());

    }

}
