package endpointM3L15;

import multithreadedCommandExecutionM2L14.CommandExecutor;
import multithreadedCommandExecutionM2L14.StartExecutionCommand;

import java.util.concurrent.ConcurrentHashMap;

/**
 * Хранит очередь команд (см. ДЗ "Многопоточное выполнение команд") для
 * каждой идущей игры и создаёт новую при первом обращении.
 * <p>
 * {@link ConcurrentHashMap#computeIfAbsent} гарантирует, что для одного
 * gameId очередь будет создана и запущена ровно один раз, даже если
 * сообщения для новой игры придут одновременно из нескольких
 * соединений — без явных synchronized-блоков или блокировок.
 */
public class GameRegistry {

    private final ConcurrentHashMap<String, CommandExecutor> games = new ConcurrentHashMap<>();

    public CommandExecutor getOrCreateGame(String gameId) {
        return games.computeIfAbsent(gameId, id -> {
            CommandExecutor executor = new CommandExecutor();
            new StartExecutionCommand(executor).execute();
            return executor;
        });
    }
}
