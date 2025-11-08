package com.utec.citasutec.repository;

import java.util.Optional;

public interface ICrudRepository<T> {
    void save(T entity);
    T update(T entity);
    void delete(T entity);
    void deleteById(Integer id);
    Iterable<T> findAll();
    boolean exists(Integer id);
    long count();
    Optional<T> findById(Integer id);
}
