package it.epicode.libreria.dao;

import it.epicode.libreria.entity.Prestito;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import lombok.AllArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@AllArgsConstructor
public class PrestitoDAO {
    private EntityManager em;

    public void save(Prestito oggetto) {
        em.getTransaction().begin();
        em.persist(oggetto);
        em.getTransaction().commit();
    }

    public Prestito findById(Long id) {
        return em.find(Prestito.class, id);
    }

    public List<Prestito> findAll() {
        return em.createNamedQuery("Trova_tutto_Prestito", Prestito.class).getResultList();
    }

    public void update(Prestito oggetto) {
        em.getTransaction().begin();
        em.merge(oggetto);
        em.getTransaction().commit();
    }

    public void delete(Prestito oggetto) {
        em.getTransaction().begin();
        em.remove(oggetto);
        em.getTransaction().commit();
    }

    public List<Prestito> cercaPerNumeroTessera(Long numeroTessera) {
        TypedQuery<Prestito> query = em.createQuery(
                "SELECT p FROM Prestito p WHERE p.utente.numeroDiTessera = :numeroTessera", Prestito.class
        );
        query.setParameter("numeroTessera", numeroTessera);
        return query.getResultList();
    }

    public List<Prestito> findPrestitiScadutiENonRestituiti() {
        TypedQuery<Prestito> query = em.createQuery(
                "SELECT p FROM Prestito p " +
                        "WHERE TRIM(p.dataRestituzioneEffettiva) = :nonRestituito " +
                        "AND p.dataRestituzionePrestito < :oggi", Prestito.class
        );
        query.setParameter("nonRestituito", "Ordine ancora non restituito");
        query.setParameter("oggi", LocalDate.now());
        return query.getResultList();
    }





}