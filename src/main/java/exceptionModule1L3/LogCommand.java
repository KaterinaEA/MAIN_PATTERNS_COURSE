package exceptionModule1L3;

import org.slf4j.LoggerFactory;

public class LogCommand implements ICommand {
    private static final org.slf4j.Logger LOGGER = LoggerFactory.getLogger(LogCommand.class);

    String eText;

    Exception _e;

    public LogCommand(Exception e) {

        eText = e.getMessage();
        _e = e;

    }

    @Override
    public void execute() {

        System.out.println("Execute LogCommand");
        LOGGER.info("exception:{}", eText);

    }

}
