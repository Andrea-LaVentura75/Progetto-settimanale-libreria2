package it.epicode.libreria.dao;

import it.epicode.libreria.entity.Catalogo;
import it.epicode.libreria.entity.Libro;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityManager;
import lombok.AllArgsConstructor;

import java.time.LocalDate;
import java.util.List;
import java.util.Scanner;

@AllArgsConstructor
public class LibroDAO {
    private EntityManager em;

    public void save(Libro oggetto) {
        em.getTransaction().begin();
        em.persist(oggetto);
        em.getTransaction().commit();
    }

    public Libro findById(Long id) {
        return em.find(Libro.class, id);
    }

    public List<Libro> findAll() {
        return em.createNamedQuery("Trova_tutto_Libro", Libro.class).getResultList();
    }

    public void update(Libro oggetto) {
        em.getTransaction().begin();
        em.merge(oggetto);
        em.getTransaction().commit();
    }

    public void delete(Libro oggetto) {
        em.getTransaction().begin();
        em.remove(oggetto);
        em.getTransaction().commit();
    }

    public void aggiungiLibro(Scanner scanner) {
        Libro libro = new Libro();

        System.out.println("Inserisci il codice ISBN del libro:");
        libro.setCodiceISBN(scanner.nextLine());

        System.out.println("Inserisci il titolo del libro:");
        libro.setTitolo(scanner.nextLine());

        System.out.println("Inserisci l'autore del libro:");
        libro.setAutore(scanner.nextLine());

        System.out.println("Inserisci il genere del libro:");
        libro.setGenere(scanner.nextLine());

        System.out.println("Inserisci l'anno di pubblicazione del libro:");
        int anno = scanner.nextInt();

        System.out.println("Inserisci il mese di pubblicazione del libro:");
        int mese = scanner.nextInt();

        System.out.println("Inserisci il giorno di pubblicazione del libro:");
        int giorno = scanner.nextInt();

        libro.setAnnoDiPubblicazione(LocalDate.of(anno, mese, giorno));

        System.out.println("Inserisci il numero di pagine del libro:");
        libro.setNumeroPagine(scanner.nextInt());

        scanner.nextLine();

        save(libro);
        System.out.println("Libro salvato con successo!");
    }


    public List<Libro> findByNomeAutore(String nomeAutore) {
        return em.createQuery("SELECT l FROM Libro l WHERE LOWER(l.autore) = LOWER(:nomeAutore)", Libro.class)
                .setParameter("nomeAutore", nomeAutore)
                .getResultList();
    }


}