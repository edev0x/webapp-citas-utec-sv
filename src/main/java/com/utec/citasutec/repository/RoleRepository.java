package com.utec.citasutec.repository;

import com.utec.citasutec.model.entity.Rol;
import com.utec.citasutec.repository.factory.CrudRepository;
import jakarta.ejb.Stateless;

import java.util.Optional;

@Stateless
public class RoleRepository extends CrudRepository<Rol> {

    public RoleRepository() {
        super(Rol.class);
    }

    public Optional<Rol> findByName(String name) {
        try {
            return Optional.ofNullable(em.createQuery("FROM Rol r WHERE r.name = :name", Rol.class)
                .setParameter("name", name)
                .getSingleResult());
        } catch (Exception ex) {
            return Optional.empty();
        }
    }
}
