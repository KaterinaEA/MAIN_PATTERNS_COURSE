package endpointM3L15;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import org.java_websocket.WebSocket;
import org.java_websocket.handshake.ClientHandshake;
import org.java_websocket.server.WebSocketServer;

import java.net.InetSocketAddress;
import java.util.Collections;
import java.util.Map;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Принимает входящие сообщения от Агентов по WebSocket.
 * <p>
 * Приём соединений и десериализация JSON отданы библиотекам
 * (Java-WebSocket и Gson) — собственный код здесь отвечает только за
 * маршрутизацию (по gameId определить очередь нужной игры) и постановку
 * {@link InterpretCommand} в эту очередь. Какую бы конкретную операцию
 * ни добавили в игру в будущем, этот класс менять не придётся.
 */
public class AgentMessageEndpoint extends WebSocketServer {

    private static final Logger LOGGER = Logger.getLogger(AgentMessageEndpoint.class.getName());

    private final GameRegistry gameRegistry;
    private final OperationRegistry operationRegistry;
    private final Gson gson = new Gson();
    private final CountDownLatch started = new CountDownLatch(1);

    public AgentMessageEndpoint(int port, GameRegistry gameRegistry, OperationRegistry operationRegistry) {
        super(new InetSocketAddress(port));
        this.gameRegistry = gameRegistry;
        this.operationRegistry = operationRegistry;
    }

    @Override
    public void onMessage(WebSocket conn, String message) {

        IncomingMessage incoming;
        try {
            incoming = gson.fromJson(message, IncomingMessage.class);
        } catch (JsonSyntaxException e) {
            LOGGER.log(Level.WARNING, "Не удалось разобрать входящее сообщение от агента", e);
            return;
        }

        if (incoming == null || incoming.getGameId() == null) {
            LOGGER.warning("Сообщение без id игры отклонено");
            return;
        }

        var gameQueue = gameRegistry.getOrCreateGame(incoming.getGameId());

        Map<String, Object> args = incoming.getArgs() != null ? incoming.getArgs() : Collections.emptyMap();

        InterpretCommand interpretCommand = new InterpretCommand(
                operationRegistry,
                gameQueue,
                incoming.getObjectId(),
                incoming.getOperationId(),
                args
        );

        gameQueue.executeCommand(interpretCommand);
    }

    @Override
    public void onOpen(WebSocket conn, ClientHandshake handshake) {
    }

    @Override
    public void onClose(WebSocket conn, int code, String reason, boolean remote) {
    }

    @Override
    public void onError(WebSocket conn, Exception ex) {
        LOGGER.log(Level.SEVERE, "Ошибка WebSocket-соединения с агентом", ex);
    }

    @Override
    public void onStart() {
        started.countDown();
    }

    /**
     * Блокируется до тех пор, пока сервер не начнёт слушать порт, либо
     * пока не истечёт таймаут. Используется в тестах вместо Thread.sleep.
     */
    public boolean awaitStart(long timeout, TimeUnit unit) throws InterruptedException {
        return started.await(timeout, unit);
    }
}
