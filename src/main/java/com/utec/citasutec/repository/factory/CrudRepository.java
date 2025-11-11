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
        if (page < 1 || size < 1) {
            throw new IllegalArgumentException("Page and size must be a positive number.");
        }

        String baseQuery = "FROM " + entityClass.getName() + " e";
        String countQuery = "SELECT COUNT(e) FROM " + entityClass.getName() + " e";

        if (Objects.nonNull(searchField) && !searchField.isEmpty() 
                && Objects.nonNull(searchTerm) && !searchTerm.isEmpty()) {
            String whereClause = " WHERE e." + searchField + " LIKE :searchTerm";
            baseQuery += whereClause;
            countQuery += whereClause;
        }

        baseQuery += " ORDER BY e.id ASC";
        countQuery += " ORDER BY e.id ASC";

        TypedQuery<T> query = em.createQuery(baseQuery, entityClass)
                .setFirstResult((page - 1) * size)
                .setMaxResults(size);
        TypedQuery<Long> countQueryObject = em.createQuery(countQuery, Long.class);
        
        if (baseQuery.contains(":searchTerm")) {
            String searchPattern = "%" + searchTerm + "%";
            query.setParameter("searchTerm", searchPattern);
            countQueryObject.setParameter("searchTerm", searchPattern);
        }

        List<T> items = query.getResultList();
        long totalItems = count();
        int totalPages = (int) Math.ceil((double) totalItems / size);
        return new Paginated<>(items, totalItems, page, size, totalPages);
    }
}
