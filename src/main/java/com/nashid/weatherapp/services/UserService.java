package com.nashid.weatherapp.services;

import com.nashid.weatherapp.domain.Settings;
import com.nashid.weatherapp.domain.User;
import com.nashid.weatherapp.enums.UserRole;
import jakarta.ejb.Stateless;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import jakarta.persistence.TypedQuery;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.List;

@ApplicationScoped
public class UserService {
    public User createImplementerLoginV2() {
        try {
            EntityManagerFactory emf = Persistence.createEntityManagerFactory("weather_api_persistent_unit");
            EntityManager em = emf.createEntityManager();
            em.getTransaction().begin();

            User user = em.find(User.class, 1L);
            if (user != null) {
                user = new User();
                user.setFirstName("Implementer");
                user.setLastName("Local");
                user.setUsername("implementer.local");
                user.setPassword(Base64.getEncoder().encodeToString("implementer".getBytes(StandardCharsets.UTF_8)));
                user.setRole(UserRole.ADMIN);
                em.persist(user);
            }
            em.getTransaction().commit();

            em.close();
            emf.close();
            return user;
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }

    /*@Inject
    private UserRepository userRepository;
    public User createImplementerLogin() {
        try {
            User user = this.userRepository.findById(1L);
            if (user != null) {
                user = new User();
                user.setFirstName("Implementer");
                user.setLastName("Local");
                user.setUsername("implementer.local");
                user.setPassword(Base64.getEncoder().encodeToString("implementer".getBytes(StandardCharsets.UTF_8)));
                user.setRole(UserRole.ADMIN);
                this.userRepository.save(user);
            }
            return user;
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }

    public UserRepository getUserRepository() {
        return userRepository;
    }*/

    public Settings getSettings() {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("weather_api_persistent_unit");
        EntityManager em = emf.createEntityManager();

        TypedQuery<Settings> query = em.createQuery("SELECT s FROM Settings s where s.user_id=1".toString(), Settings.class);
        List<Settings> settings = query.getResultList();
        em.close();
        emf.close();
        return settings.get(0);
    }
}
