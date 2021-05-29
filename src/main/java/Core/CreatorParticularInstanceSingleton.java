package Core;

/**
 * Creator that create particular instance for every request.
 *
 * @author Piotr Stoklosa
 */
public class CreatorParticularInstanceSingleton implements Creator {

    Object instance;

    public CreatorParticularInstanceSingleton(Object instance) {
        this.instance = instance;
    }

    @Override
    public Object createObject() {
        return instance;
    }


}
