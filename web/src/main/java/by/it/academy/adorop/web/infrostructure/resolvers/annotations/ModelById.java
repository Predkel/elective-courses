package by.it.academy.adorop.web.infrostructure.resolvers.annotations;

import java.lang.annotation.*;

@Target(ElementType.PARAMETER)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface ModelById {
    String nameOfIdParameter();
}
