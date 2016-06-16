package by.it.academy.adorop.web.utils;

import by.it.academy.adorop.service.api.Service;
import by.it.academy.adorop.service.exceptions.ServiceException;

public class IdValidator<T> {

    private final Service<T, Long> service;
    private T model;

    public IdValidator(Service<T, Long> service) {
        this.service = service;
    }

    public boolean isValid(String idParam) throws ServiceException {
        if (RequestParamValidator.isEmpty(idParam)
                || !RequestParamValidator.isPositiveInt(idParam)) {
            return false;
        } else {
            model = service.find(Long.valueOf(idParam));
            return model != null;
        }
    }

    public T getValidModel() {
        return model;
    }
}
