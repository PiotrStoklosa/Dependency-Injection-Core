package Core;

/**
 * Test class
 *
 * @author Piotr Stoklosa
 */
public class RecursionClass {

    RecursionClass recursionClass;

    public RecursionClass(RecursionClass recursionClass) {
        this.recursionClass = new RecursionClass(recursionClass);
    }
}