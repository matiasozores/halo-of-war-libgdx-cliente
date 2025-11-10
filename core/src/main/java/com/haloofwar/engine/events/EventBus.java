package com.haloofwar.engine.events;

import java.util.*;
import java.util.function.Consumer;

public class EventBus {
    private final Map<Class<?>, List<Consumer<?>>> listeners = new HashMap<>();

    public <T> void subscribe(Class<T> eventType, Consumer<T> listener) {
        if (eventType == null || listener == null) return;

        this.listeners.computeIfAbsent(eventType, k -> new ArrayList<>()).add(listener);
    }

    public <T> void unsubscribe(Class<T> eventType, Consumer<T> listener) {
        List<Consumer<?>> list = this.listeners.get(eventType);
        if (list != null) {
            list.remove(listener);

            if (list.isEmpty()) {
                this.listeners.remove(eventType);
            }
        }
    }

    public <T> void publish(T event) {
        if (event == null) return;
        Class<?> eventClass = event.getClass();

        List<Map.Entry<Class<?>, List<Consumer<?>>>> snapshot = new ArrayList<>(this.listeners.entrySet());

        snapshot.stream()
            .filter(entry -> entry.getKey().isAssignableFrom(eventClass))
            .forEach(entry -> notifyListeners(entry.getKey(), entry.getValue(), event));
    }

    @SuppressWarnings("unchecked")
    private <T> void notifyListeners(Class<?> type, List<Consumer<?>> rawListeners, T event) {
        for (Consumer<?> rawListener : new ArrayList<>(rawListeners)) {
            try {
                ((Consumer<T>) rawListener).accept(event);
            } catch (Exception ex) {
                System.err.println("⚠️ Error en listener para " + type.getSimpleName() + ": " + ex);
                ex.printStackTrace();
            }
        }
    }
    
    public int getTotalListenersCount() {
        return listeners.values().stream().mapToInt(List::size).sum();
    }
    
    public void clear() {
        this.listeners.clear();
    }
}
