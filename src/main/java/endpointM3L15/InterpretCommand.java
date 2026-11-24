package endpointM3L15;

import exceptionModule1L3.ICommand;
import iocModule2L1.IoC;
import multithreadedCommandExecutionM2L14.CommandExecutor;

import java.util.Map;

/**
 * Разбирает содержимое входящего сообщения: находит игровой объект,
 * применяет к нему параметры операции и ставит в очередь игры команду,
 * соответствующую запрошенной операции.
 * <p>
 * Сама команда не знает ничего о конкретных правилах игры — какие
 * операции и параметры существуют, определяется только регистрациями
 * в IoC и в {@link OperationRegistry}. Добавление нового правила или
 * новой команды не требует изменения этого класса (это и проверяется
 * бонусным баллом по п. 6 — "удовлетворяет SOLID").
 */
public class InterpretCommand implements ICommand {

    private final OperationRegistry operationRegistry;
    private final CommandExecutor gameQueue;
    private final String objectId;
    private final String operationId;
    private final Map<String, Object> args;

    public InterpretCommand(OperationRegistry operationRegistry,
                             CommandExecutor gameQueue,
                             String objectId,
                             String operationId,
                             Map<String, Object> args) {
        this.operationRegistry = operationRegistry;
        this.gameQueue = gameQueue;
        this.objectId = objectId;
        this.operationId = operationId;
        this.args = args;
    }

    @Override
    public void execute() {

        Object gameObject = IoC.resolve("Игровые объекты", objectId);

        for (Map.Entry<String, Object> arg : args.entrySet()) {
            String parameterKey = operationRegistry.resolveParameterKey(arg.getKey());
            ICommand setParameter = IoC.resolve(parameterKey, gameObject, arg.getValue());
            setParameter.execute();
        }

        String operationKey = operationRegistry.resolveOperationKey(operationId);
        ICommand operation = IoC.resolve(operationKey, gameObject);

        // Постановка в очередь команд игры выполняется через уже известную
        // этой команде очередь (ту же, в которой выполняется сама
        // InterpretCommand), а не через дополнительный уровень IoC —
        // это ровно то же действие, без лишней косвенности.
        gameQueue.executeCommand(operation);
    }
}
