package com.example.socket.session;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import lombok.Getter;
import org.springframework.stereotype.Component;

@Component
@Getter
public class CustomHttpSessionImpl implements CustomHttpSession, Serializable {

    private static ThreadLocal<String> threadLocal = new ThreadLocal<>();

    private String id;
    private final long creationTime;
    private long lastAccessedTime;
    private int maxInactiveInterval;

    private Map<String, ThreadLocal<String>> sessionStore;
    private boolean isNew;

    public CustomHttpSessionImpl() {
        this.id = UUID.randomUUID().toString();
        this.creationTime = System.currentTimeMillis();
        this.lastAccessedTime = 0L;
        this.maxInactiveInterval = 30;
        this.sessionStore = new ConcurrentHashMap<>();
        this.isNew = true;
    }

    @Override
    public long getCreationTime() {
        return this.creationTime;
    }

    @Override
    public String getId() {
        return this.id;
    }

    @Override
    public long getLastAccessedTime() {
        this.lastAccessedTime = System.currentTimeMillis();
        return this.lastAccessedTime;
    }

    @Override
    public void setMaxInactiveInterval(int interval) {
        this.maxInactiveInterval = interval;
    }

    @Override
    public int getMaxInactiveInterval() {
        return this.maxInactiveInterval;
    }

    @Override
    public Object getAttribute(String name) {
        ThreadLocal<String> threadLocal = sessionStore.get(name);
        String session = threadLocal.get();
        return session;
    }

    @Override
    public void setAttribute(String name, Object value) {
        this.isNew = false;
        threadLocal.set(value.toString());
        sessionStore.put(name, threadLocal);
    }

    @Override
    public void removeAttribute(String name) {
        ThreadLocal<String> threadLocal = sessionStore.get(name);
        threadLocal.remove();
        sessionStore.remove(name);
    }

    public Map<String, ThreadLocal<String>> getSessionStore() {
        return sessionStore;
    }

    @Override
    public String getAttributes() {
        HashMap<String, String> map = new HashMap<>();
        sessionStore.keySet().forEach(m -> map.put(m, sessionStore.get(m).get()));
        return map.toString();
    }

    @Override
    public void invalidate() {
        for (ThreadLocal<String> value : sessionStore.values()) {
            value.remove();
        }
        sessionStore.clear();
    }

    @Override
    public boolean isNew() {
        return this.isNew;
    }
}
