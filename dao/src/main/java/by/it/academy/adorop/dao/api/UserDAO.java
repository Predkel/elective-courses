package by.it.academy.adorop.dao.api;

import by.it.academy.adorop.model.users.User;

import java.util.List;
import java.util.Map;

public interface UserDAO<T extends User> extends DAO<T, Long> {
    List<User> getFromAllUsersBy(Map<String, Object> propertiesNameToValues);
    List<User> getFromAllUsersBy(String propertyName, Object value);
}
