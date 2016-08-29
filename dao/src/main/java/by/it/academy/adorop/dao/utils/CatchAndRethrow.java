package by.it.academy.adorop.dao.utils;

import by.it.academy.adorop.dao.exceptions.DaoException;

import java.lang.annotation.*;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface CatchAndRethrow {
    Class<? extends RuntimeException> exceptionToCatch();
    Class<? extends RuntimeException> rethrow();
}
