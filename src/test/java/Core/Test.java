package Core;

import org.junit.Assert;
import java.lang.reflect.InvocationTargetException;
/**
 * @author Piotr Stoklosa
 */
public class Test {

    @org.junit.Test
    public void testConcatenate() throws InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException, NoInterfaceImplementationFoundException, ClassNotFoundException {

        SimpleContainer c = new SimpleContainer();

        c.RegisterType( FirstImplementationOfInterfaceTest.class, true );
        FirstImplementationOfInterfaceTest f1 = c.Resolve( FirstImplementationOfInterfaceTest.class);
        FirstImplementationOfInterfaceTest f2 = c.Resolve( FirstImplementationOfInterfaceTest.class);

        Assert.assertEquals(f1, f2);

    }

    @org.junit.Test
    public void testInterfaces() throws InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException, NoInterfaceImplementationFoundException, ClassNotFoundException {

        SimpleContainer c = new SimpleContainer();

        c.RegisterType(InterfaceTest.class, FirstImplementationOfInterfaceTest.class, false );
        InterfaceTest f = c.Resolve(InterfaceTest.class);

        Assert.assertTrue(f instanceof FirstImplementationOfInterfaceTest);

        c.RegisterType( InterfaceTest.class, SecondImplementationOfInterfaceTest.class, false );
        InterfaceTest g = c.Resolve(InterfaceTest.class);

        Assert.assertTrue(g instanceof SecondImplementationOfInterfaceTest);

    }

    @org.junit.Test(expected = NoInterfaceImplementationFoundException.class)
    public void testNoInterfaceImplementation() throws NoInterfaceImplementationFoundException, InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException, ClassNotFoundException {

        SimpleContainer c = new SimpleContainer();

        InterfaceTest f = c.Resolve(InterfaceTest.class);

    }

    @org.junit.Test
    public void testNoConcreteClass() throws NoInterfaceImplementationFoundException, InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException, ClassNotFoundException {

        SimpleContainer c = new SimpleContainer();

        FirstImplementationOfInterfaceTest f = c.Resolve( FirstImplementationOfInterfaceTest.class);

    }

}