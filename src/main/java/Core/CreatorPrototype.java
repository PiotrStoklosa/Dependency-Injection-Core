package Core;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

/**
 * Creator that create new instance every time
 *
 * @author Piotr Stoklosa
 */
class CreatorPrototype implements Creator{

    Constructor<?> constructor;
    Object[] parameters;

    CreatorPrototype(Constructor<?> constructor,Object[] parameters ) {
        this.constructor = constructor;
        this.parameters = parameters;
    }

    @Override
    public Object createObject() throws InvocationTargetException, InstantiationException, IllegalAccessException {

            return constructor.newInstance(parameters);

    }

}
