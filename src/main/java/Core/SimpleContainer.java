package Core;

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
     * Registers new type with particular scope
     *
     * @param type type to register
     * @param singleton if true container will return only one instance
     *                  if false container will return new instances every time
     */
    public void registerType(Type type, boolean singleton) {

        registerType(type, type, singleton);

    }

    /**
     * Binds a type to its more specific implementation
     *
     * @param type1 interface/abstract class/parent class
     * @param type2 subclass of type1
     * @param singleton if true container will return only one instance
     *                  if false container will return new instances every time
     */
    public void registerType(Type type1, Type type2, boolean singleton){

        Class<?> aClass = null;
        try {
            aClass = Class.forName(type2.getTypeName());
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        if (singleton)
            creators.put(type1.getTypeName(), new CreatorSingleton(aClass));
        else
            creators.put(type1.getTypeName(), new CreatorPrototype(aClass));

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
    public <T> T resolve(Type type) throws InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException, NoInterfaceImplementationFoundException, ClassNotFoundException {

        Class<?> aClass = null;
        try {
            aClass = Class.forName(type.getTypeName());
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        assert aClass != null;
        if (!creators.containsKey(type.getTypeName())){
            if (aClass.isInterface())
                throw new NoInterfaceImplementationFoundException();

            return (T) Class.forName(type.getTypeName()).getConstructor((Class<?>[]) null).newInstance();
        }

        return (T) creators.get(type.getTypeName()).createObject();

    }

}