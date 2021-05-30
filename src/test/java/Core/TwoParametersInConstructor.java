package Core;

/**
 * Test class
 *
 * @author Piotr Stoklosa
 */
public class TwoParametersInConstructor {

    String string;
    RandomObject randomObject;

    public TwoParametersInConstructor( RandomObject randomObject, String string ) {
        this.string = string;
        this.randomObject = randomObject;
    }
}