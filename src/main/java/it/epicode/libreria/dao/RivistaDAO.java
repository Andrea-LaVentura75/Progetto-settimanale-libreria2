package it.epicode.libreria.dao;

import it.epicode.libreria.entity.Periodo;
import it.epicode.libreria.entity.Rivista;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityManager;
import lombok.AllArgsConstructor;

import java.time.LocalDate;
import java.util.List;
import java.util.Scanner;

@AllArgsConstructor
public class RivistaDAO {
    private EntityManager em;

    public void save(Rivista oggetto) {
        em.getTransaction().begin();
        em.persist(oggetto);
        em.getTransaction().commit();
    }

    public Rivista findById(Long id) {
        return em.find(Rivista.class, id);
    }

    public List<Rivista> findAll() {
        return em.createNamedQuery("Trova_tutto_Rivista", Rivista.class).getResultList();
    }

    public void update(Rivista oggetto) {
        em.getTransaction().begin();
        em.merge(oggetto);
        em.getTransaction().commit();
    }

    public void delete(Rivista oggetto) {
        em.getTransaction().begin();
        em.remove(oggetto);
        em.getTransaction().commit();
    }

    public  void aggiungiRivista(Scanner scanner) {
        Rivista rivista = new Rivista();

        System.out.println("Inserisci il codice ISBN della rivista:");
        rivista.setCodiceISBN(scanner.nextLine());

        System.out.println("Inserisci il titolo della rivista:");
        rivista.setTitolo(scanner.nextLine());

        System.out.println("Inserisci l'anno di pubblicazione della rivista:");
        int anno = scanner.nextInt();

        System.out.println("Inserisci il mese di pubblicazione della rivista:");
        int mese = scanner.nextInt();

        System.out.println("Inserisci il giorno di pubblicazione della rivista:");
        int giorno = scanner.nextInt();

        rivista.setAnnoDiPubblicazione(LocalDate.of(anno, mese, giorno));

        System.out.println("Inserisci il numero di pagine della rivista:");
        rivista.setNumeroPagine(scanner.nextInt());
        scanner.nextLine();

        System.out.println("Seleziona il periodo della rivista: (1 per GIORNALIERA, 2 per SETTIMANALE, 3 per MENSILE, 4 per ANNUALE)");
        int periodoScelto = scanner.nextInt();
        scanner.nextLine();

        switch (periodoScelto) {
            case 1 -> rivista.setPeriodo(Periodo.SETTIMANALE);
            case 2 -> rivista.setPeriodo(Periodo.MENSILE);
            case 3 -> rivista.setPeriodo(Periodo.SEMESTRALE);
            default -> System.out.println("Periodo non valido. Impostato su SETTIMANALE.");
        }

        save(rivista);
        System.out.println("Libro salvato con successo!");
    }

}