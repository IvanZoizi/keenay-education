package keenay.education.config;

import org.springframework.stereotype.Component;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

@Component
public class WsSessionRegistry {

    private final ConcurrentMap<String, String> sessions = new ConcurrentHashMap<>();

    public boolean tryRegister(String username, String sessionId) {
        return sessions.putIfAbsent(username, sessionId) == null;
    }

    public void release(String username, String sessionId) {
        sessions.remove(username, sessionId);
    }
}