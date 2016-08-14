package by.it.academy.adorop.web.utils.pagination;

import java.lang.annotation.*;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Pagination {
    String bunchAttributeName();
}
