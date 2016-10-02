package by.it.academy.adorop.web.infrastructure.filtering;

import by.it.academy.adorop.web.infrastructure.exceptions.RequestedPropertyDoesNotExistException;
import by.it.academy.adorop.web.utils.RequestParamValidator;
import org.springframework.beans.PropertyAccessException;
import org.springframework.beans.TypeMismatchException;
import org.springframework.stereotype.Component;
import org.springframework.validation.BindException;
import org.springframework.web.bind.MethodArgumentNotValidException;

import java.lang.reflect.Field;
import java.util.*;

@Component
public class RestrictionsParserImpl implements RestrictionsParser{
    public Map<String, Object> parse(Map<String, String> parameters, Class retrievedClass) {
        Map<String, Object> restrictions = new HashMap<>();
        List<Field> allDeclaredFields = getAllFieldsOf(retrievedClass);
        Set<String> parametersNames = parameters.keySet();
        for (String parameterName : parametersNames) {
            for (Field declaredField : allDeclaredFields) {
                String fieldName = declaredField.getName();
                String[] propertiesChain = parameterName.split("\\.");
                if (propertiesChain[0].equals(fieldName)) {
                    if (propertiesChain.length == 1) {
                        putScalarValue(parameters, restrictions, parameterName, declaredField);
                    } else {
                        for (int i = 0; i < propertiesChain.length - 1; i++) {
                            try {
                                Class<?> typeOfNestedField = declaredField.getType();
                                fieldName = propertiesChain[i + 1];
                                declaredField = findDeclaredFieldByName(typeOfNestedField, fieldName);
                            } catch (NoSuchFieldException e) {
                                throw new RequestedPropertyDoesNotExistException(e);
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
        if (clazz == Object.class) {
            throw new NoSuchFieldException();
        }
        Field[] declaredFields = clazz.getDeclaredFields();

        for (Field declaredField : declaredFields) {
            String fieldName = declaredField.getName();
            if (fieldName.equals(nameOfSearchedField)) {
                return clazz.getDeclaredField(nameOfSearchedField);
            }
        }
        clazz = clazz.getSuperclass();
        return findDeclaredFieldByName(clazz, nameOfSearchedField);
    }

    private void putScalarValue(Map<String, String> parameters, Map<String, Object> restrictions, String parameterName, Field declaredField) {
        Class<?> fieldType = declaredField.getType();
        Object propertyValue = null;
        String parameterValue = parameters.get(parameterName);
        if (fieldType == String.class) {
            propertyValue = parameterValue;
        } else if (fieldType == Long.class) {
            validateNumericType(parameterValue);
            propertyValue = Long.valueOf(parameterValue);
        } else if(fieldType == Integer.class) {
            validateNumericType(parameterValue);
            propertyValue = Integer.valueOf(parameterValue);
        }
        restrictions.put(parameterName, propertyValue);
    }

    private void validateNumericType(String parameterValue) {
        if (!RequestParamValidator.isNumeric(parameterValue)) {
            throw new TypeMismatchException(parameterValue, Number.class);
        }
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
