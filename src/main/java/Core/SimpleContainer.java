package Core;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;

/**
 * Container is responsible for holding and returning objects
 *
 * @author Piotr Stoklosa
 */
public class SimpleContainer {

    /**
     * Bind types with their constructors
     */
    private final Map<String, Creator> creators;
    {
        creators = new HashMap<>();
    }

    /**
     * A helper method that fills parameter table with specific objects.
     *
     * @param parameters an empty table that will be filled with the specified objects.
     * @param constructor constructor from which the parameters will be retrieved.
     *
     * @throws RecursionParameterException when the method finds infinite recursion.
     */
    private void fillParameters(Object[] parameters, Constructor<?> constructor) throws RecursionParameterException, InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {

        Class<?> aClass = constructor.getDeclaringClass();

        int i = 0;
        for (var parameter : constructor.getParameterTypes()){

            if (parameter.isAssignableFrom(aClass))
                throw new RecursionParameterException();

            parameters[i] = resolve(parameter);
            i++;
        }

    }

    /**
     * Registers new type with particular scope
     *
     * @param type type to register
     * @param singleton if true container will return only one instance
     *                  if false container will return new instances every time
     */
    public void registerType(Type type, boolean singleton) throws NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {

        registerType(type, type, singleton);

    }

    /**
     * Binds a type to its more specific implementation.
     *
     * @param type1 interface/abstract class/parent class
     * @param type2 subclass of type1
     * @param singleton if true container will return only one instance
     *                  if false container will return new instances every time
     */
    public void registerType(Type type1, Type type2, boolean singleton) throws NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {

        Class<?> aClass = null;
        try {
            aClass = Class.forName(type2.getTypeName());
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        assert aClass != null;
        Constructor<?>[] constructors = aClass.getConstructors();
        Object[] parameters = null;

        boolean annotationSingleton = false;
        Constructor<?> annotatedConstructor = null;

        int longestConstructorParameter = 0;
        Constructor<?> longestConstructor = null;

        Constructor<?> finalConstructor;

        if (constructors.length != 0) {

            for (var constructorTemp : constructors) {
                if (constructorTemp.isAnnotationPresent(DependencyConstructor.class)) {

                    if (annotationSingleton)
                        throw new AmbiguityOfDependencyConstructorException();

                    annotationSingleton = true;
                    annotatedConstructor = constructorTemp;
                }

                if (constructorTemp.getParameterCount() >= longestConstructorParameter) {

                    longestConstructor = constructorTemp;
                    longestConstructorParameter = constructorTemp.getParameterCount();

                }
            }

            if (annotationSingleton){

                parameters = new Object[annotatedConstructor.getParameterCount()];

                fillParameters(parameters, annotatedConstructor);

                finalConstructor = annotatedConstructor;

            }
            else{

                assert longestConstructor != null;
                parameters = new Object[longestConstructor.getParameterCount()];

                boolean unambiguousConstructor = false;

                for (var constructorTemp : constructors) {
                    if (constructorTemp.getParameterCount() == longestConstructorParameter) {
                        if (unambiguousConstructor)
                            throw new AmbiguityOfDependencyConstructorException();
                        unambiguousConstructor = true;
                    }
                }

                fillParameters(parameters, longestConstructor);

                finalConstructor = longestConstructor;

            }
        }
        else{
            finalConstructor = aClass.getConstructor((Class<?>[]) null);
        }

        if (singleton)
            creators.put(type1.getTypeName(), new CreatorSingleton(finalConstructor, parameters));
        else
            creators.put(type1.getTypeName(), new CreatorPrototype(finalConstructor, parameters));

    }

    /**
     * Binds a type to its particular instance
     *
     * @param type type to register
     * @param instance instance of the type or its more specific implementation
     */
    public <T> void registerInstance(Type type, T instance ){

        creators.put(type.getTypeName(), new CreatorParticularInstanceSingleton(instance));

    }

    /**
     * Create an object using HashMap creators
     *
     * @param type requested type of the class
     * @return requested object
     */
    @SuppressWarnings("unchecked")
    public <T> T resolve(Type type) throws InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException, NoInterfaceImplementationFoundException {

        Class<?> aClass = null;
        String classType = type.getTypeName();
        try {
            aClass = Class.forName(classType);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        assert aClass != null;
        
        if (!creators.containsKey(classType)){
            if (aClass.isInterface())
                throw new NoInterfaceImplementationFoundException();

            registerType(aClass,false);

        }

        return (T) creators.get(classType).createObject();

    }

}