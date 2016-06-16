package by.it.academy.adorop.web.utils.pagination;

public interface Paginator {
    int defineMaxResult();
    int defineFirstResult();
    void setTotalNumberOfEntities(Long numberOfEntities);
}
