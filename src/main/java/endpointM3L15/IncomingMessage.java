package endpointM3L15;

import java.util.Map;

/**
 * Универсальный формат сообщения от Агента Игровому серверу. Формат не
 * зависит от конкретных правил игры и команд: новая операция или новое
 * правило добавляются регистрацией в {@link OperationRegistry}, а не
 * изменением этого класса или endpoint'а.
 *
 * <ul>
 *   <li>{@code gameId} — какую игру обрабатывать (маршрутизация к очереди игры);</li>
 *   <li>{@code objectId} — какой игровой объект внутри игры адресован;</li>
 *   <li>{@code operationId} — внешний идентификатор операции (переводится
 *       в доверенный ключ IoC через {@link OperationRegistry}, напрямую
 *       как ключ IoC никогда не используется);</li>
 *   <li>{@code args} — параметры операции, зависящие только от самой
 *       операции, а не от endpoint'а.</li>
 * </ul>
 */
public class IncomingMessage {

    private String gameId;
    private String objectId;
    private String operationId;
    private Map<String, Object> args;

    public String getGameId() {
        return gameId;
    }

    public String getObjectId() {
        return objectId;
    }

    public String getOperationId() {
        return operationId;
    }

    public Map<String, Object> getArgs() {
        return args;
    }
}
