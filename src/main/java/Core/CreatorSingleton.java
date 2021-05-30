package Core;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Creator that create one instance for one process
 *
 * @author Piotr Stoklosa
 */
class CreatorSingleton implements Creator{

    Constructor<?> constructor;
    Object instance = null;
    Object[] parameters;

    private static final Lock lock;
    static {
        lock = new ReentrantLock();
    }

    CreatorSingleton(Constructor<?> constructor, Object[] parameters) {
        this.constructor = constructor;
        this.parameters = parameters;
    }

    @Override
    public Object createObject() throws InvocationTargetException, InstantiationException, IllegalAccessException {

        if (instance == null){

            lock.lock();

            if (instance == null){
                instance = constructor.newInstance(parameters);
            }

            lock.unlock();
        }
        return instance;
    }
}
