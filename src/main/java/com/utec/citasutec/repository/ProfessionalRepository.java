package com.utec.citasutec.repository;

import com.utec.citasutec.model.entity.Professional;
import com.utec.citasutec.repository.factory.CrudRepository;
import jakarta.ejb.Stateless;

@Stateless
public class ProfessionalRepository extends CrudRepository<Professional> {

    public ProfessionalRepository() {
        super(Professional.class);
    }
}
