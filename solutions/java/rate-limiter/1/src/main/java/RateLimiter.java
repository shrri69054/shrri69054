import java.time.Duration;
import java.time.Instant;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class RateLimiter<K> {

    private final int limit;
    private final Duration windowSize;
    private final TimeSource timeSource;
    private int requestCount;
    private Instant windowStart;

    private static class ClientWindow {
        int requestCount;
        Instant windowStart;
    }

    private final Map<K, ClientWindow> clients = new ConcurrentHashMap<>();

    public RateLimiter(int limit, Duration windowSize, TimeSource timeSource) {
        this.limit = limit;
        this.windowSize = windowSize;
        this.timeSource = timeSource;


    }

    public boolean allow(K clientId) {

        Instant now = timeSource.now(); //timeSource.now() gives current time

        ClientWindow window = clients.computeIfAbsent(clientId, id -> {
            ClientWindow w = new ClientWindow();
            w.requestCount = 0;
            w.windowStart = now;
            return w;
        });



        if(!now.isBefore(window.windowStart.plus(windowSize))){
            window.windowStart = now;
            window.requestCount = 0;
        }

        if(window.requestCount<limit) {
            window.requestCount++;
            return true;
        }

        return false;
    }

}