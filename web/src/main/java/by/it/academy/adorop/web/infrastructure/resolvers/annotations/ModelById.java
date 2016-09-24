package by.it.academy.adorop.web.infrastructure.resolvers.annotations;

import by.it.academy.adorop.service.api.Service;

import java.lang.annotation.*;

@Target(ElementType.PARAMETER)
@Retention(RetentionPolicy.RUNTIME)
public @interface ModelById {
    String nameOfIdParameter();
    Class<? extends Service> serviceClass();
}
