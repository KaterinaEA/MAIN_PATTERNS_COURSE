package endpointM3L15;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Белый список операций и параметров, которые Агент имеет право
 * запросить снаружи.
 * <p>
 * {@code operationId} и имена параметров приходят из сообщения, то есть
 * от не доверенной стороны. Если использовать их напрямую как ключ
 * IoC.resolve(...), агент сможет обратиться к любой внутренней
 * зависимости контейнера (например, к "IoC.register" или к скоупам) —
 * это и есть инъекция, о которой предупреждает задание. Здесь внешний
 * идентификатор переводится во внутренний ключ IoC только если он
 * заранее явно разрешён; для всего остального — отказ.
 */
public class OperationRegistry {

    private final Map<String, String> allowedOperations = new ConcurrentHashMap<>();
    private final Map<String, String> allowedParameters = new ConcurrentHashMap<>();

    public void allowOperation(String externalOperationId, String iocKey) {
        allowedOperations.put(externalOperationId, iocKey);
    }

    public void allowParameter(String externalParameterName, String iocKey) {
        allowedParameters.put(externalParameterName, iocKey);
    }

    public String resolveOperationKey(String externalOperationId) {
        String key = allowedOperations.get(externalOperationId);
        if (key == null) {
            throw new IllegalArgumentException("Операция не разрешена: " + externalOperationId);
        }
        return key;
    }

    public String resolveParameterKey(String externalParameterName) {
        String key = allowedParameters.get(externalParameterName);
        if (key == null) {
            throw new IllegalArgumentException("Параметр не разрешён: " + externalParameterName);
        }
        return key;
    }
}
