package by.it.academy.adorop.web.config.handlers.annotations;

import java.lang.annotation.*;

@Target(ElementType.PARAMETER)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface ModelById {
    String nameOfIdParameter();
}
