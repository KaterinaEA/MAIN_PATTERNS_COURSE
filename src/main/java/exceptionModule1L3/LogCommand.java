package exceptionModule1L3;

import org.slf4j.LoggerFactory;

public class LogCommand {
    private static final org.slf4j.Logger LOGGER = LoggerFactory.getLogger(Logging.class);

    String cText;
    String eText;

    ICommand _ct;
    Exception _e;

    public LogCommand(ICommand ct, Exception e) {

        cText = ct.toString();
        eText = e.getMessage();

        _ct = ct;
        _e = e;

        LOGGER.info("Error command: {} , exception:{}", cText, eText);

    }

}
