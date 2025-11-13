package com.utec.citasutec.repository;

import com.utec.citasutec.model.entity.Professional;
import com.utec.citasutec.model.entity.Service;
import com.utec.citasutec.repository.factory.CrudRepository;
import com.utec.citasutec.service.ProfessionalService;
import jakarta.ejb.Stateless;

@Stateless
public class ProfessionalServiceRepository extends CrudRepository<Service> {
    public ProfessionalServiceRepository() {
        super(Service.class);
    }
}
