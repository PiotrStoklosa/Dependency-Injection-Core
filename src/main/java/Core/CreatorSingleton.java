package Core;

import java.lang.reflect.InvocationTargetException;

/**
 * Creator that create one instance for one process
 *
 * @author Piotr Stoklosa
 */
public class CreatorSingleton implements Creator{

    Class<?> constructor;
    Object instance = null;

    public CreatorSingleton(Class<?> constructor) {
        this.constructor = constructor;
    }

    @Override
    public Object createObject() throws NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {

        if (instance == null)
            instance = constructor.getConstructor(null).newInstance();

        return instance;

    }
}
