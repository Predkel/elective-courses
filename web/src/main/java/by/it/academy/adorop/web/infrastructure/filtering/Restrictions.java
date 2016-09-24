package by.it.academy.adorop.web.infrastructure.filtering;

import by.it.academy.adorop.model.Mark;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.PARAMETER)
@Retention(RetentionPolicy.RUNTIME)
public @interface Restrictions {
    Class<?> retrievedClass();
}
