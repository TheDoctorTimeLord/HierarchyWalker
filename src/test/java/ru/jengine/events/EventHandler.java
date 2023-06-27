package ru.jengine.events;

import ru.jengine.HierarchyWalking;

public interface EventHandler<E extends Event> {
    default Class<?> getHandledEventType() {
        return HierarchyWalking.getGenericType(getClass(), EventHandler.class, 0);
    }

    void handle(E event);
}
