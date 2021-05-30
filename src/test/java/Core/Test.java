package Core;

import org.junit.Assert;
import java.lang.reflect.InvocationTargetException;
/**
 * @author Piotr Stoklosa
 */
public class Test {

    @org.junit.Test
    public void testConcatenate() throws InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {

        SimpleContainer c = new SimpleContainer();

        c.registerType( FirstImplementationOfInterfaceTest.class, true );
        FirstImplementationOfInterfaceTest f1 = c.resolve( FirstImplementationOfInterfaceTest.class);
        FirstImplementationOfInterfaceTest f2 = c.resolve( FirstImplementationOfInterfaceTest.class);

        Assert.assertEquals(f1, f2);

    }

    @org.junit.Test
    public void testInterfaces() throws InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {

        SimpleContainer c = new SimpleContainer();

        c.registerType(InterfaceTest.class, FirstImplementationOfInterfaceTest.class, false );
        InterfaceTest f = c.resolve(InterfaceTest.class);

        Assert.assertTrue(f instanceof FirstImplementationOfInterfaceTest);

        c.registerType( InterfaceTest.class, SecondImplementationOfInterfaceTest.class, false );
        InterfaceTest g = c.resolve(InterfaceTest.class);

        Assert.assertTrue(g instanceof SecondImplementationOfInterfaceTest);

    }

    @org.junit.Test(expected = NoInterfaceImplementationFoundException.class)
    public void testNoInterfaceImplementation() throws NoInterfaceImplementationFoundException, InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {

        SimpleContainer c = new SimpleContainer();

        InterfaceTest f = c.resolve(InterfaceTest.class);

    }

    @org.junit.Test
    public void testNoConcreteClass() throws InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {

        SimpleContainer c = new SimpleContainer();

        FirstImplementationOfInterfaceTest f = c.resolve( FirstImplementationOfInterfaceTest.class);

    }

    @org.junit.Test
    public void testParticularInstance() throws InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {

        SimpleContainer c = new SimpleContainer();

        InterfaceTest f1 = new FirstImplementationOfInterfaceTest();
        c.registerInstance( InterfaceTest.class, f1);

        InterfaceTest f2 = c.resolve(InterfaceTest.class);

        Assert.assertEquals(f1, f2);

    }

    @org.junit.Test
    public void testDependencyInjection() throws InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {

        SimpleContainer c = new SimpleContainer();
        A a = c.resolve(A.class);
        Assert.assertNotNull(a.b);

    }

    @org.junit.Test
    public void testStringDependencyInjection() throws InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {

        SimpleContainer c = new SimpleContainer();

        c.registerInstance( String.class, "text" );
        X x = c.resolve(X.class);

        Assert.assertEquals(x.string, "text");

    }

    @org.junit.Test
    public void testDependencyInjectionRecursion() throws InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {

        SimpleContainer c = new SimpleContainer();
        c.registerType(InterfaceTest.class, FirstImplementationOfInterfaceTest.class, false );
        c.registerType(Tree.class, TreeImplementation.class, false);
        RecursionTree a = c.resolve(RecursionTree.class);

        Assert.assertNotNull(a.t);

    }

}