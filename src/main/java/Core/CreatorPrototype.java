package Core;

import java.lang.reflect.InvocationTargetException;

/**
 * Creator that create new instance every time
 *
 * @author Piotr Stoklosa
 */
public class CreatorPrototype implements Creator{

    Class<?> constructor;

    public CreatorPrototype(Class<?> constructor) {
        this.constructor = constructor;
    }

    @Override
    public Object createObject() throws NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {

            return constructor.getConstructor( null).newInstance();

    }

}
