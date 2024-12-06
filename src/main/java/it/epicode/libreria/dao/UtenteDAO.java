package it.epicode.libreria.dao;

import it.epicode.libreria.entity.Utente;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityManager;
import lombok.AllArgsConstructor;

import java.util.List;

@AllArgsConstructor
public class UtenteDAO {
    private EntityManager em;

    public void save(Utente oggetto) {
        em.getTransaction().begin();
        em.persist(oggetto);
        em.getTransaction().commit();
    }

    public Utente findById(Long id) {
        return em.find(Utente.class, id);
    }

    public List<Utente> findAll() {
        return em.createNamedQuery("Trova_tutto_Utente", Utente.class).getResultList();
    }

    public void update(Utente oggetto) {
        em.getTransaction().begin();
        em.merge(oggetto);
        em.getTransaction().commit();
    }

    public void delete(Utente oggetto) {
        em.getTransaction().begin();
        em.remove(oggetto);
        em.getTransaction().commit();
    }


}