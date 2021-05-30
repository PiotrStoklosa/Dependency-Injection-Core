package Core;

import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;

/**
 * The annotation that clarify DI core about which constructor should be taken to create objects.
 * In case of ambiguity (two constructors have @DependencyConstructor annotation), there will be thrown AmbiguityOfDependencyConstructorException.
 *
 * @author Piotr Stoklosa
 * @see AmbiguityOfDependencyConstructorException
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.CONSTRUCTOR)
public @interface DependencyConstructor {
}