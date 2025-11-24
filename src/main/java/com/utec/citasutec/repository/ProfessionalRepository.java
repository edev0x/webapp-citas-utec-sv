package com.utec.citasutec.repository;

import com.utec.citasutec.model.entity.Professional;
import com.utec.citasutec.model.entity.Service;
import com.utec.citasutec.repository.factory.CrudRepository;
import jakarta.ejb.Stateless;

import java.util.List;

@Stateless
public class ProfessionalRepository extends CrudRepository<Professional> {

    public ProfessionalRepository() {
        super(Professional.class);
    }

    public List<Service> findServicesByProfessional(String email) {
        return em.createQuery("SELECT s FROM Service s JOIN FETCH s.professional WHERE s.professional.email = :email", Service.class)
                .setParameter("email", email)
                .getResultList();
    }
}
