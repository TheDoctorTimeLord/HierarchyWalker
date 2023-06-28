# HierarchyWalker
This project provides classes to traverse the Java class hierarchy. Traversal order: current class, current class's implemented interfaces (including interface hierarchy), current class's superclass. Traversal uses modified DFS.

Using of iterable:
```
Class<?> startHierarchy = ...

for (HierarchyElement element : new WalkerIterable(startHierarchy)) {
    System.out.println(element.getCurrentElement());
    System.out.println(element.getElementTypeParameters());
}
```

Using of iterator:
```
Class<?> startHierarchy = ...

Iterator<HierarchyElement> hierarchyIteraror = new WalkerIterator(startHierarchy);
while (hierarchyIteraror.hasNext()) {
    HierarchyElement element = hierarchyIteraror.next();

    System.out.println(element.getCurrentElement());
    System.out.println(element.getElementTypeParameters());
}
```

Using of special utility methods:
```
interface A<T> { }

interface B<E> extends A<E> { }

class C implements B<C> { }

...
Class<?> generic = HierarchyWalking.getGenericType(C.class, A.class, 0);
//returned class C:
//C implements B<C> -> B<C> extends A<C> -> class A is target and generic parameter by index 0 from A = C.
```
