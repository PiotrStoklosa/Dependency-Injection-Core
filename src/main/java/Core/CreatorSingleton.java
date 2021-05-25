package Core;

import java.lang.reflect.InvocationTargetException;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Creator that create one instance for one process
 *
 * @author Piotr Stoklosa
 */
class CreatorSingleton implements Creator{

    Class<?> constructor;
    Object instance = null;

    private static final Lock lock;
    static {
        lock = new ReentrantLock();
    }

    CreatorSingleton(Class<?> constructor) {
        this.constructor = constructor;
    }

    @Override
    public Object createObject() throws NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {

        if (instance == null){

            lock.lock();

            if (instance == null){
                instance = constructor.getConstructor(null).newInstance();
            }

            lock.unlock();
        }
        return instance;
    }
}
