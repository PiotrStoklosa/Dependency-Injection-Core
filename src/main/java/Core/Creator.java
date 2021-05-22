package Core;

import java.lang.reflect.InvocationTargetException;
/**
 * Base interface for different types of scopes
 *
 * @author Piotr Stoklosa
 */
public interface Creator {

    /**
     * Create requested object
     *
     * @return The object with particular scope
     */
    Object createObject() throws NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException;

}