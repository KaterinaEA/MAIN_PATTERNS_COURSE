package exceptionModule1L3;

import java.util.Queue;

import org.slf4j.LoggerFactory;

public class CommandProcessor {

    private static final org.slf4j.Logger LOGGER = LoggerFactory.getLogger(LogCommand.class);

    Queue<ICommand> q;

    public CommandProcessor(Queue<ICommand> queue) {  this.q = queue;  }

    public void runProcess()  {

        while (!q.isEmpty()) {

            var c = q.poll();

            try {

                c.execute();

            } catch (Exception e) {
                try {
                    ExceptionHandler exceptionHandler = ExceptionHandler.getInstance(q);
                    exceptionHandler.handle(c, e);
                    //IoC.Resolve<ICommand>("ExceptionHandler.Handle", c, e).Execute();
                } catch (Exception ex) {
                        LOGGER.error(ex.getMessage(), ex);

                }

            }

        }

    }

}
