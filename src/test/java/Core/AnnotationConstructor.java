package Core;

/**
 * Test class
 *
 * @author Piotr Stoklosa
 */
public class AnnotationConstructor {

    String s;

    public AnnotationConstructor(int a){}

    @DependencyConstructor
    public AnnotationConstructor(String s){
        this.s = s;
    }

}
