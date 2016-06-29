package by.it.academy.adorop.service.implementations;

import by.it.academy.adorop.dao.api.DAO;
import by.it.academy.adorop.service.api.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;
import java.util.List;

@Transactional(propagation = Propagation.REQUIRED, readOnly = true, rollbackFor = Exception.class)
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
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    public T persist(T entity) {
        return getDAO().persist(entity);
    }

    protected abstract DAO<T, ID> getDAO();
}
