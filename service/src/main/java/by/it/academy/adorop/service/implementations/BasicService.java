package by.it.academy.adorop.service.implementations;

import by.it.academy.adorop.dao.api.DAO;
import by.it.academy.adorop.dao.utils.CatchAndRethrow;
import by.it.academy.adorop.service.api.Service;
import by.it.academy.adorop.service.exceptions.ServiceException;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public abstract class BasicService<T, ID extends Serializable> implements Service<T, ID> {

    @Override
    public T find(ID id) {
        return getDAO().get(id);
    }

    @Override
    public Long getTotalCount() {
        return getDAO().getCount();
    }

    @Override
    public List<T> getBunch(int firstResult, int maxResult) {
        return getDAO().getBunch(firstResult, maxResult);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public T persist(T entity) {
        return getDAO().persist(entity);
    }

    @Override
    public T getSingleResultBy(String nameOfUniqueProperty, Object value) {
        return getDAO().getBy(nameOfUniqueProperty, value).get(0);
    }

    @Override
    public T getSingleResultBy(Map<String, Object> namesOfUniquePropertiesToValues) {
        return getDAO().getBy(namesOfUniquePropertiesToValues).get(0);
    }

    @Override
    public void update(T entity) {
        getDAO().update(entity);
    }

    protected abstract DAO<T, ID> getDAO();
}
