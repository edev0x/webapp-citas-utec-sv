package com.utec.citasutec.repository;

import com.utec.citasutec.model.entity.User;
import com.utec.citasutec.repository.factory.CrudRepository;
import com.utec.citasutec.repository.factory.Paginated;

import jakarta.ejb.Stateless;
import jakarta.persistence.NoResultException;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.Optional;

@Slf4j
@Stateless
public class UserRepository extends CrudRepository<User> {

    public UserRepository() {
        super(User.class);
    }

    public Optional<User> findByEmail(String username) {
        try {
            return Optional.ofNullable(em.createQuery("FROM User u JOIN FETCH u.rol WHERE u.email = :email", User.class)
                    .setParameter("email", username)
                    .getSingleResult());
        } catch (NoResultException ex) {
            return Optional.empty();
        }
    }

    public List<User> findAllWithRoles() {
        return em.createQuery("FROM User u JOIN FETCH u.rol", User.class).getResultList();
    }

    public long countActiveUsers() {
        return em.createQuery("SELECT COUNT(u) FROM User u WHERE u.isActive = true", Long.class).getSingleResult();
    }

    public Paginated<User> getUsersPaginated(int page, int size, String searchField, String searchTerm) {
        return getPageable(page, size, searchField, searchTerm);
    }
}
