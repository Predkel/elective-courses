package by.it.academy.adorop.web.infrastructure;

import org.springframework.stereotype.Component;

import java.lang.reflect.Field;
import java.util.*;

@Component
public class RestrictionsParser {
    public Map<String, Object> parse(Map<String, String> parameters, Class retrievedClass) {
        Map<String, Object> restrictions = new HashMap<>();
        List<Field> allDeclaredFields = getAllFieldsOf(retrievedClass);
        Set<String> parametersNames = parameters.keySet();
        for (String parameterName : parametersNames) {
            for (Field declaredField : allDeclaredFields) {
                String fieldName = declaredField.getName();
                if (parameterName.contains(fieldName)) {
                    if (parameterName.equals(fieldName)) {
                        Class<?> fieldType = declaredField.getType();
                        Object parameterValue = null;
                        if (fieldType == String.class) {
                            parameterValue = parameters.get(parameterName);
                        } else if (fieldType == Long.class) {
                            parameterValue = Long.valueOf(parameters.get(parameterName));
                        } else if(fieldType == Integer.class) {
                            parameterValue = Integer.valueOf(parameters.get(parameterName));
                        }
                        restrictions.put(parameterName, parameterValue);
                    }
                }
            }
        }

        return restrictions;
    }

    private List<Field> getAllFieldsOf(Class clazz) {
        List<Field> fields = new LinkedList<>();

        while (clazz != Object.class) {
            fields.addAll(0, Arrays.asList(clazz.getDeclaredFields()));
            clazz = clazz.getSuperclass();
        }
        return fields;
    }
}
