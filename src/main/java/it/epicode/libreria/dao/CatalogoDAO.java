package it.epicode.libreria.dao;

import it.epicode.libreria.entity.Catalogo;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityManager;
import lombok.AllArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@AllArgsConstructor
public class CatalogoDAO {
    private EntityManager em;

    public void save(Catalogo oggetto) {
        em.getTransaction().begin();
        em.persist(oggetto);
        em.getTransaction().commit();
    }

    public Catalogo findById(Long id) {
        return em.find(Catalogo.class, id);
    }

    public List<Catalogo> findAll() {
        return em.createNamedQuery("Trova_tutto_Catalogo", Catalogo.class).getResultList();
    }

    public void update(Catalogo oggetto) {
        em.getTransaction().begin();
        em.merge(oggetto);
        em.getTransaction().commit();
    }

    public void delete(Catalogo oggetto) {
        em.getTransaction().begin();
        em.remove(oggetto);
        em.getTransaction().commit();
    }



    public Catalogo findByISBN(String codiceISBN) {
        return em.createQuery("SELECT c FROM Catalogo c WHERE c.codiceISBN = :isbn", Catalogo.class)
                .setParameter("isbn", codiceISBN)
                .getSingleResult();
    }


    public void deleteByISBN(String codiceISBN) {
        em.getTransaction().begin();
        Catalogo elemento = findByISBN(codiceISBN);
        em.remove(elemento);
        em.getTransaction().commit();
    }

    public List<Catalogo> findByAnnoDiPubblicazione(LocalDate anno) {
        return em.createQuery("SELECT c FROM Catalogo c WHERE c.annoDiPubblicazione = :annoPubblicazione", Catalogo.class)
                .setParameter("annoPubblicazione", anno)
                .getResultList();
    }

    public List<Catalogo> findByTitoloParziale(String titoloParziale) {
        return em.createQuery("SELECT c FROM Catalogo c WHERE LOWER(c.titolo) LIKE LOWER(:titolo)", Catalogo.class)
                .setParameter("titolo", "%" + titoloParziale + "%")
                .getResultList();
    }


}