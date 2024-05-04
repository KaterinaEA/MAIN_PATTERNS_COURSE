package exceptionModule1L3;

import com.google.common.base.Function;
import com.google.common.collect.TreeBasedTable;
import com.google.common.base.Optional;
import lspIspModule1L2.move.Move;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.function.Supplier;

public class ExceptionHandler {

    private final Map<Class<? extends ICommand>, Map<Class<? extends Exception>, ICommand>> store;

    public ExceptionHandler() {
        store = new HashMap<>();

        Map<Class<? extends Exception>, ICommand> map = new HashMap<>();

        map.put(NullPointerException.class, new ICommand() {
            @Override
            public void execute() {

                ILogger l = new ILogger() {
                    @Override
                    public void log(LogCommand logCommand) {

                    }

                    @Override
                    public LogCommand getLogCommand() {
                        return null;
                    }
                };

                ICommand command = new Logging(l); // Создаем экземпляр команды



            }
        });

/*        map.put(NullPointerException.class, new ICommand() {
            @Override
            public void execute() {
                System.out.println("asdasdasdasdad");
            }
        });*/

        store.put(Move.class, map);

    }

    public ICommand handle(ICommand command, Exception e) {
        return store.getOrDefault(command.getClass(), Collections.emptyMap()).getOrDefault(e.getClass(), null);
        //return store.get(command.getClass()).get(e.getClass());
    }


}
