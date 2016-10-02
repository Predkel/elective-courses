package by.it.academy.adorop.web.infrastructure.filtering;

import java.util.Map;

public interface RestrictionsParser {
    Map<String, Object> parse(Map<String, String> parameters, Class retrievedClass);
}
