package by.it.academy.adorop.service.api;

import by.it.academy.adorop.model.users.User;

public interface UserService<T extends User> extends Service<T, Long> {
}
