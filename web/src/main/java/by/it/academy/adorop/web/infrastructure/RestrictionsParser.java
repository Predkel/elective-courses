package by.it.academy.adorop.web.infrastructure;

import org.springframework.beans.TypeMismatchException;
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
                if (parameterName.split("\\.")[0].equals(fieldName)) {
                    if (parameterName.equals(fieldName)) {
                        putScalarValue(parameters, restrictions, parameterName, declaredField);
                    } else {
                        String[] namesOfNestedFields = parameterName.split("\\.");
                        for (int i = 0; i < namesOfNestedFields.length - 1; i++) {
                            try {
                                Class<?> typeOfNestedField = declaredField.getType();
                                fieldName = namesOfNestedFields[i + 1];
                                declaredField = findDeclaredFieldByName(typeOfNestedField, fieldName);
                                if (declaredField == null) {
                                    //// TODO: 25.09.2016
                                    throw new TypeMismatchException("", Object.class);
                                }
                            } catch (NoSuchFieldException e) {
                                e.printStackTrace();
                            }
                        }
                            putScalarValue(parameters, restrictions, parameterName, declaredField);
                    }
                }
            }
        }
        return restrictions;
    }

    private Field findDeclaredFieldByName(Class<?> clazz, String nameOfSearchedField) throws NoSuchFieldException {
        Field[] declaredFields = clazz.getDeclaredFields();

        for (Field declaredField : declaredFields) {
            String fieldName = declaredField.getName();
            if (fieldName.equals(nameOfSearchedField)) {
                return clazz.getDeclaredField(nameOfSearchedField);
            }
            if (clazz == Object.class) {
                return null;
            }
        }
        clazz = clazz.getSuperclass();
        return findDeclaredFieldByName(clazz, nameOfSearchedField);
    }

    private boolean isLastIteration(String[] namesOfNestedFields, int i) {
        return i + 1 == namesOfNestedFields.length;
    }

    private void putScalarValue(Map<String, String> parameters, Map<String, Object> restrictions, String parameterName, Field declaredField) {
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

    private List<Field> getAllFieldsOf(Class clazz) {
        List<Field> fields = new LinkedList<>();

        while (clazz != Object.class) {
            fields.addAll(0, Arrays.asList(clazz.getDeclaredFields()));
            clazz = clazz.getSuperclass();
        }
        return fields;
    }
}
