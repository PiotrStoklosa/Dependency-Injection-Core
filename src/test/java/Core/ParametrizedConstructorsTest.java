package Core;

import org.junit.Assert;

import java.lang.reflect.InvocationTargetException;

/**
 * Test class
 *
 * @author Piotr Stoklosa
 */
public class ParametrizedConstructorsTest {


    @org.junit.Test
    public void testDependencyInjection() throws InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {

        SimpleContainer c = new SimpleContainer();
        InclusionObjectInConstructor a = c.resolve(InclusionObjectInConstructor.class);
        Assert.assertNotNull(a.randomObject);

    }

    @org.junit.Test
    public void testStringDependencyInjection() throws InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {

        SimpleContainer c = new SimpleContainer();

        c.registerInstance( String.class, "text" );
        TwoParametersInConstructor x = c.resolve(TwoParametersInConstructor.class);

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
    @org.junit.Test(expected = AmbiguityOfDependencyConstructorException.class)
    public void testAmbiguousConstructor() throws InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {

        SimpleContainer c = new SimpleContainer();
        c.registerType(AmbiguousConstructor.class, false );

    }

    @org.junit.Test
    public void testAnnotatedConstructor() throws InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {

        SimpleContainer c = new SimpleContainer();
        c.registerInstance(String.class, "text");
        c.registerType(AnnotationConstructor.class, false );
        AnnotationConstructor x = c.resolve(AnnotationConstructor.class);

        Assert.assertEquals(x.s, "text");

    }

}
