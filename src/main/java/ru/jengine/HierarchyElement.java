package ru.jengine;

public interface HierarchyElement {
    Class<?> getCurrentElement();
    Class<?>[] getElementTypeParameters();
}
