package com.utec.citasutec.repository;

import com.utec.citasutec.model.entity.Log;
import com.utec.citasutec.repository.factory.CrudRepository;
import jakarta.ejb.Stateless;

import java.util.Optional;

@Stateless
public class LogRepository extends CrudRepository<Log> {
    public LogRepository() {
        super(Log.class);
    }

    public void saveLog(Log log) {
        em.persist(log);
    }

    public Optional<Log> findById(Integer id) {
        return Optional.ofNullable(em.find(Log.class, id));
    }

    public Log saveLogAndFlush(Log log) {
        em.persist(log);
        em.flush();
        return log;
    }
}
