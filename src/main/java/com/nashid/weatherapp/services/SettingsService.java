package com.nashid.weatherapp.services;

import com.nashid.weatherapp.domain.Settings;
import jakarta.enterprise.context.ApplicationScoped;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import jakarta.persistence.TypedQuery;
import java.util.List;

@ApplicationScoped
public class SettingsService {

    public Settings getSettings() {
        /*CoreEntityManager coreEntityManager = new CoreEntityManager();
        EntityManager entityManager = coreEntityManager.create();
        TypedQuery<Settings> query = entityManager.createQuery("SELECT s FROM Settings s where s.user_id=1", Settings.class);
        List<Settings> settings = query.getResultList();
        coreEntityManager.close(entityManager);

        return settings.get(0);*/
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("mysqlResource");
        EntityManager em = emf.createEntityManager();

        TypedQuery<Settings> query = em.createQuery("SELECT s FROM Settings s where s.user_id=1", Settings.class);
        List<Settings> settings = query.getResultList();
        em.close();
        emf.close();
        return settings.get(0);
    }
}
