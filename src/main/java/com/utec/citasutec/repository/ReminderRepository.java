package com.utec.citasutec.repository;

import com.utec.citasutec.model.entity.Reminder;
import com.utec.citasutec.repository.factory.CrudRepository;
import jakarta.ejb.Stateless;

@Stateless
public class ReminderRepository extends CrudRepository<Reminder> {
    public ReminderRepository() {
        super(Reminder.class);
    }

    public Reminder findByUserId(Integer userId) {
        return em.createQuery("SELECT r FROM Reminder r WHERE r.user.id = :userId", Reminder.class)
                .setParameter("userId", userId)
                .getSingleResult();
    }

    public void saveReminder(Reminder reminder) {
        em.persist(reminder);
    }

    public void deleteReminder(Integer reminderId) {
        em.createQuery("DELETE FROM Reminder r WHERE r.id = :reminderId")
            .setParameter("reminderId", reminderId)
            .executeUpdate();
    }

    public void updateReminder(Reminder reminder) {
        em.merge(reminder);
    }
}
