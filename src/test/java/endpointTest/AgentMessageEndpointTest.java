package endpointTest;

import endpointM3L15.AgentMessageEndpoint;
import endpointM3L15.GameRegistry;
import endpointM3L15.OperationRegistry;
import exceptionModule1L3.ICommand;
import iocModule2L1.IoC;
import iocModule2L1.Scope.InitCommand;
import multithreadedCommandExecutionM2L14.CommandExecutor;
import org.java_websocket.client.WebSocketClient;
import org.java_websocket.handshake.ServerHandshake;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.net.URI;
import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.function.Function;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class AgentMessageEndpointTest {

    private AgentMessageEndpoint endpoint;
    private int port;

    /** Минимальный тестовый игровой объект — танк со скоростью и позицией. */
    private static class TankState {
        volatile double velocity;
        volatile double position;
    }

    @Before
    public void setUp() {
        new InitCommand().execute();
        port = 18000 + new Random().nextInt(2000);
    }

    @After
    public void tearDown() throws InterruptedException {
        if (endpoint != null) {
            endpoint.stop(1000);
        }
    }

    private static void register(String key, Function<Object[], Object> strategy) {
        ICommand command = IoC.resolve("IoC.register", key, strategy);
        command.execute();
    }

    private WebSocketClient connectClient() throws Exception {
        CountDownLatch connected = new CountDownLatch(1);
        WebSocketClient client = new WebSocketClient(new URI("ws://localhost:" + port)) {
            @Override
            public void onOpen(ServerHandshake handshakedata) {
                connected.countDown();
            }

            @Override
            public void onMessage(String message) {
            }

            @Override
            public void onClose(int code, String reason, boolean remote) {
            }

            @Override
            public void onError(Exception ex) {
            }
        };
        assertTrue("Клиент должен подключиться к endpoint", client.connectBlocking(5, TimeUnit.SECONDS));
        assertTrue(connected.await(5, TimeUnit.SECONDS));
        return client;
    }

    @Test
    public void incomingMessageSetsVelocityAndMovesTheObject() throws Exception {

        ConcurrentHashMap<String, TankState> tanks = new ConcurrentHashMap<>();
        TankState tank = new TankState();
        tanks.put("548", tank);

        CountDownLatch moved = new CountDownLatch(1);

        register("Игровые объекты", (args) -> tanks.get((String) args[0]));

        register("Test.SetVelocity", (args) -> (ICommand) () ->
                ((TankState) args[0]).velocity = ((Number) args[1]).doubleValue());

        register("Test.Move", (args) -> (ICommand) () -> {
            TankState t = (TankState) args[0];
            t.position += t.velocity;
            moved.countDown();
        });

        OperationRegistry operationRegistry = new OperationRegistry();
        operationRegistry.allowParameter("velocity", "Test.SetVelocity");
        operationRegistry.allowOperation("move", "Test.Move");

        GameRegistry gameRegistry = new GameRegistry();

        endpoint = new AgentMessageEndpoint(port, gameRegistry, operationRegistry);
        endpoint.setReuseAddr(true);
        endpoint.start();
        assertTrue("Сервер должен запуститься", endpoint.awaitStart(5, TimeUnit.SECONDS));

        WebSocketClient client = connectClient();

        String json = "{"
                + "\"gameId\":\"game-1\","
                + "\"objectId\":\"548\","
                + "\"operationId\":\"move\","
                + "\"args\":{\"velocity\":2}"
                + "}";
        client.send(json);

        assertTrue("Команда движения должна выполниться после обработки сообщения",
                moved.await(2, TimeUnit.SECONDS));
        assertEquals(2.0, tank.velocity, 0.0001);
        assertEquals(2.0, tank.position, 0.0001);

        client.closeBlocking();
    }

    @Test
    public void unknownOperationIsRejectedAndDoesNotCrashTheGameWorker() throws Exception {

        ConcurrentHashMap<String, TankState> tanks = new ConcurrentHashMap<>();
        tanks.put("548", new TankState());

        register("Игровые объекты", (args) -> tanks.get((String) args[0]));

        // "move" намеренно не разрешена этой игре — проверяем, что сообщение,
        // в котором агент попытался бы обратиться к произвольному ключу IoC
        // (например, к "IoC.register"), будет отклонено, а не выполнено.
        OperationRegistry operationRegistry = new OperationRegistry();

        GameRegistry gameRegistry = new GameRegistry();

        endpoint = new AgentMessageEndpoint(port, gameRegistry, operationRegistry);
        endpoint.setReuseAddr(true);
        endpoint.start();
        assertTrue(endpoint.awaitStart(5, TimeUnit.SECONDS));

        WebSocketClient client = connectClient();

        String maliciousJson = "{"
                + "\"gameId\":\"game-2\","
                + "\"objectId\":\"548\","
                + "\"operationId\":\"IoC.register\","
                + "\"args\":{}"
                + "}";
        client.send(maliciousJson);

        CommandExecutor executor = gameRegistry.getOrCreateGame("game-2");

        // Доказываем, что поток игры пережил отклонённую операцию и
        // продолжает нормально обрабатывать очередь.
        CountDownLatch survived = new CountDownLatch(1);
        executor.executeCommand(survived::countDown);

        assertTrue("Поток игры должен продолжать работать после отклонённой операции",
                survived.await(2, TimeUnit.SECONDS));

        client.closeBlocking();
    }
}
