package com.utec.citasutec.repository.factory;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

public abstract class CrudRepository<T> implements ICrudRepository<T> {

    @PersistenceContext(unitName = "citasUtecPU")
    protected EntityManager em;
    private final Class<T> entityClass;

    protected CrudRepository(Class<T> entityClass) {
        this.entityClass = entityClass;
    }

    public Optional<T> findById(Integer id) {
        return Optional.ofNullable(em.find(entityClass, id));
    }

    public List<T> findAll() {
        return em.createQuery("FROM " + entityClass.getName() + " e", entityClass).getResultList();
    }

    public void save(T entity) {
        em.persist(entity);
        em.flush();
    }

    public T update(T entity) {
        return em.merge(entity);
    }

    public void delete(T entity) {
       em.remove(entity);
    }

    public void deleteById(Integer id) {
        T entity = this.findById(id).orElse(null);
        if (Objects.isNull(entity)) {
            throw new IllegalArgumentException("Entity with id " + id + " not found. Thus, cannot be deleted");
        }
        delete(entity);
    }

    public boolean exists(Integer id) {
        return findById(id).isPresent();
    }

    public long count() {
        return em.createQuery("SELECT COUNT(e) FROM " + entityClass.getName() + " e", Long.class).getSingleResult();
    }

    public Paginated<T> getPageable(int page, int size, String searchField, String searchTerm) {
        String baseQuery = "FROM " + entityClass.getName() + " e";

        if (Objects.nonNull(searchField) && !searchField.isEmpty() 
                && Objects.nonNull(searchTerm) && !searchTerm.isEmpty()) {
            baseQuery += " WHERE e." + searchField + " LIKE :searchTerm";
        }

        TypedQuery<T> query = em.createQuery(baseQuery, entityClass)
                .setFirstResult((page - 1) * size)
                .setMaxResults(size);
        
        if (baseQuery.contains(":searchTerm")) {
            query.setParameter("searchTerm", "%" + searchTerm + "%");
        }

        List<T> items = query.getResultList();
        long totalItems = count();
        int totalPages = (int) Math.ceil((double) totalItems / size);
        return new Paginated<>(items, totalItems, page, size, totalPages);
    }

}
