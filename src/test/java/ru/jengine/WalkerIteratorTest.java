package ru.jengine;

import org.junit.Assert;
import org.junit.Test;
import ru.jengine.hierarchy.*;
import ru.jengine.typechecking.*;

import java.lang.reflect.Type;
import java.util.Map;

public class WalkerIteratorTest
{
    private static final Map.Entry[] HIERARCHY_WITH_GENERIC = {
            Map.entry(Impl.class, new Class[0]),
            Map.entry(D.class, new Class[0]),
            Map.entry(C.class, new Class[] {Object.class}),
            Map.entry(B.class, new Class[] {Object.class}),
            Map.entry(A.class, new Class[] {Object.class, Base.class}),
            Map.entry(E.class, new Class[] {Base.class}),
            Map.entry(H.class, new Class[0]),
            Map.entry(G.class, new Class[] {Base.class}),
            Map.entry(B.class, new Class[] {Object.class}),
            Map.entry(A.class, new Class[] {Object.class, Base.class}),
            Map.entry(F.class, new Class[] {Base.class, Base.class}),
            Map.entry(Object.class, new Class[0])
    };

    @Test
    public void testWalkAroundHierarchy() {
        int currentHierarchyIndex = -1;

        WalkerIterator walkerIterator = new WalkerIterator(Impl.class);
        while (walkerIterator.hasNext()) {
            currentHierarchyIndex++;

            if (currentHierarchyIndex >= HIERARCHY_WITH_GENERIC.length) {
                Assert.fail("Iterator take steps over hierarchy depth");
            }

            HierarchyElement currentElement = walkerIterator.next();
            Map.Entry<Class<?>, Class<?>[]> expectedElement = HIERARCHY_WITH_GENERIC[currentHierarchyIndex];

            Assert.assertEquals(currentElement.getCurrentElement(), expectedElement.getKey());
            Assert.assertArrayEquals(
                    currentElement.getElementTypeParameters(),
                    expectedElement.getValue()
            );
        }
        Assert.assertEquals(currentHierarchyIndex, HIERARCHY_WITH_GENERIC.length - 1);
    }

    @Test
    public void testClassWithInitialsGenerics() {
        Class<?>[] generics = new Class[] {Impl4.class, Impl3.class, Impl2.class, I12.class};
        WalkerIterator walkerIterator = new WalkerIterator(Checked.class, true, generics);

        Assert.assertTrue(walkerIterator.hasNext());
        Assert.assertArrayEquals(generics, walkerIterator.next().getElementTypeParameters());
        Assert.assertTrue(walkerIterator.hasNext());
        Assert.assertArrayEquals(new Class<?>[] {Impl4.class}, walkerIterator.next().getElementTypeParameters());
        Assert.assertTrue(walkerIterator.hasNext());
        Assert.assertEquals(Object.class, walkerIterator.next().getCurrentElement());
        Assert.assertFalse(walkerIterator.hasNext());
    }

    @Test
    public void testClassWithFlatHierarchy() {
        Class<?>[] generics = new Class[] {Impl2.class, Impl2.class, Impl2.class, I3Impl.class};
        WalkerIterator walkerIterator = new WalkerIterator(Checked.class, true, generics);

        Assert.assertTrue(walkerIterator.hasNext());
        Assert.assertArrayEquals(generics, walkerIterator.next().getElementTypeParameters());
        Assert.assertTrue(walkerIterator.hasNext());
        Assert.assertArrayEquals(new Class<?>[] {Impl2.class}, walkerIterator.next().getElementTypeParameters());
        Assert.assertTrue(walkerIterator.hasNext());
        Assert.assertEquals(Object.class, walkerIterator.next().getCurrentElement());
        Assert.assertFalse(walkerIterator.hasNext());
    }

    @Test(expected = WalkingException.class)
    public void testClassWithNonMatchedGenerics() {
        Class<?>[] generics = new Class[] {Impl2.class, Impl2.class, Impl3.class, I3Impl.class};
        new WalkerIterator(Checked.class, true, generics);
    }
}
