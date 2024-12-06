package it.epicode.libreria.main;

import it.epicode.libreria.dao.CatalogoDAO;
import it.epicode.libreria.dao.LibroDAO;
import it.epicode.libreria.dao.PrestitoDAO;
import it.epicode.libreria.dao.RivistaDAO;
import it.epicode.libreria.entity.Catalogo;
import it.epicode.libreria.entity.Libro;
import it.epicode.libreria.entity.Prestito;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

import java.time.LocalDate;
import java.util.List;
import java.util.Scanner;

public class MainUpdate {
    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("unit-jpa");
        EntityManager em = emf.createEntityManager();

        CatalogoDAO catalogoDAO = new CatalogoDAO(em);
        LibroDAO libroDAO = new LibroDAO(em);
        RivistaDAO rivistaDAO = new RivistaDAO(em);
        PrestitoDAO prestitoDAO = new PrestitoDAO(em);

        try (Scanner scanner = new Scanner(System.in)) {
            boolean exit = false;

            while (!exit) {
                System.out.println("==== MENU ====");
                System.out.println("1. Aggiungi un elemento (Libro o Rivista)");
                System.out.println("2. Cerca un elemento tramite codice ISBN");
                System.out.println("3. Elimina un elemento tramite codice ISBN");
                System.out.println("4. Ricerca per anno di pubblicazione");
                System.out.println("5. Ricerca per nome autore");
                System.out.println("6. Ricerca per titolo o parte di esso");
                System.out.println("7. Ricerca per codice tessera utente");
                System.out.println("8. Cerca prestiti scaduti e ancora non restituiti");
                System.out.println("0. Esci");
                System.out.print("Scegli un'opzione: ");

                int scelta = scanner.nextInt();
                scanner.nextLine();

                switch (scelta) {
                    case 1:
                        aggiungiElemento(scanner, libroDAO, rivistaDAO);
                        break;
                    case 2:
                        cercaElemento(scanner, catalogoDAO);
                        break;
                    case 3:
                        eliminaElemento(scanner, catalogoDAO);
                        break;
                    case 4:
                        cercaPerAnno(scanner, catalogoDAO);
                        break;
                    case 5:
                        cercaPerNomeAutore(scanner, libroDAO);
                        break;
                    case 6:
                        cercaPerTitolo(scanner, catalogoDAO);
                        break;
                    case 7:
                        cercaPrestitiPerNumeroTessera(scanner, prestitoDAO);
                        break;
                    case 8:
                        cercaPrestitiScadutiENonRestituiti(prestitoDAO);
                        break;

                    case 0:
                        exit = true;
                        System.out.println("Uscita dal programma...");
                        break;
                    default:
                        System.out.println("Opzione non valida! Riprova.");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            em.close();
            emf.close();
        }
    }

    private static void aggiungiElemento(Scanner scanner, LibroDAO libroDAO, RivistaDAO rivistaDAO) {
        System.out.println("Che tipo di elemento vuoi aggiungere? (1 per Libro, 2 per Rivista)");
        int scelta = scanner.nextInt();
        scanner.nextLine();

        if (scelta == 1) {
            libroDAO.aggiungiLibro(scanner);
        } else if (scelta == 2) {
            rivistaDAO.aggiungiRivista(scanner);
        } else {
            System.out.println("Scelta non valida!");
        }
    }

    private static void cercaElemento(Scanner scanner, CatalogoDAO catalogoDAO) {
        System.out.println("Inserisci il codice ISBN dell'elemento da cercare:");
        String codiceISBN = scanner.nextLine();

        try {
            Catalogo elemento = catalogoDAO.findByISBN(codiceISBN);
            System.out.println("Elemento trovato: " + elemento);
        } catch (Exception e) {
            System.out.println("Elemento non trovato con il codice ISBN: " + codiceISBN);
        }
    }

    private static void eliminaElemento(Scanner scanner, CatalogoDAO catalogoDAO) {
        System.out.println("Inserisci il codice ISBN dell'elemento da eliminare:");
        String codiceISBN = scanner.nextLine();

        try {
            catalogoDAO.deleteByISBN(codiceISBN);
            System.out.println("Elemento eliminato con successo!");
        } catch (Exception e) {
            System.out.println("Elemento non trovato o errore nell'eliminazione.");
        }
    }

    private static void cercaPerAnno(Scanner scanner, CatalogoDAO catalogoDAO) {
        System.out.println("Inserisci l'anno di pubblicazione (formato: YYYY-MM-DD):");
        String inputData = scanner.nextLine();

        try {
            LocalDate data = LocalDate.parse(inputData);
            List<Catalogo> risultati = catalogoDAO.findByAnnoDiPubblicazione(data);

            if (risultati.isEmpty()) {
                System.out.println("Nessun elemento trovato per l'anno di pubblicazione: " + inputData);
            } else {
                System.out.println("Elementi trovati:");
                risultati.forEach(System.out::println);
            }
        } catch (Exception e) {
            System.out.println("Errore: formato della data non valido o nessun elemento trovato.");
        }
    }

    private static void cercaPerNomeAutore(Scanner scanner, LibroDAO libroDAO){
        System.out.println("Inserisci il nome dell'autore:");
        String inputData = scanner.nextLine();

        try {
            List<Libro> risultati = libroDAO.findByNomeAutore(inputData);

            if(risultati.isEmpty()){
                System.out.println("Nessun elemento trovato per nome autore: " + inputData);
            } else {
                System.out.println("Elementi trovati:");
                risultati.forEach(System.out::println);
            }
        }catch (Exception e) {
            System.out.println("Errore: formato della data non valido o nessun elemento trovato.");
        }
    }

    private static void cercaPerTitolo(Scanner scanner, CatalogoDAO catalogoDAO) {
        System.out.println("Inserisci il titolo o parte del titolo:");
        String titoloParziale = scanner.nextLine();

        try {
            List<Catalogo> risultati = catalogoDAO.findByTitoloParziale(titoloParziale);

            if (risultati.isEmpty()) {
                System.out.println("Nessun elemento trovato con il titolo o parte di esso: " + titoloParziale);
            } else {
                System.out.println("Elementi trovati:");
                risultati.forEach(System.out::println);
            }
        } catch (Exception e) {
            System.out.println("Errore durante la ricerca per titolo.");
        }
    }

    private static void cercaPrestitiPerNumeroTessera(Scanner scanner, PrestitoDAO prestitoDAO) {
        System.out.println("Inserisci il numero di tessera dell'utente:");
        Long numeroTessera = scanner.nextLong();
        scanner.nextLine();

        try {
            List<Prestito> prestiti = prestitoDAO.cercaPerNumeroTessera(numeroTessera);

            if (prestiti.isEmpty()) {
                System.out.println("Nessun prestito trovato per il numero di tessera: " + numeroTessera);
            } else {
                System.out.println("Prestiti trovati:");
                prestiti.forEach(prestito -> {
                    System.out.println("Prestito ID: " + prestito.getId());
                    System.out.println("Data inizio prestito: " + prestito.getDataInizioPrestiro());
                    System.out.println("Data restituzione prevista: " + prestito.getDataRestituzionePrestito());
                    System.out.println("Data restituzione effettiva: " + prestito.getDataRestituzioneEffettiva());
                    System.out.println("Elemento prestato ID: " + prestito.getElementoPrestato().getId());
                    System.out.println("---------------------------------------------------");
                });
            }
        } catch (Exception e) {
            System.out.println("Errore durante la ricerca dei prestiti per numero di tessera.");
        }
    }

    private static void cercaPrestitiScadutiENonRestituiti(PrestitoDAO prestitoDAO) {
        System.out.println("Ricerca dei prestiti scaduti e ancora non restituiti...");

        try {
            List<Prestito> prestiti = prestitoDAO.findPrestitiScadutiENonRestituiti();

            if (prestiti.isEmpty()) {
                System.out.println("Non ci sono prestiti scaduti e ancora non restituiti.");
            } else {
                System.out.println("Prestiti trovati:");
                for (Prestito prestito : prestiti) {
                    System.out.println("DEBUG: ID=" + prestito.getId() +
                            ", Utente=" + prestito.getUtente().getId() +
                            ", Data scadenza=" + prestito.getDataRestituzionePrestito() +
                            ", Data effettiva=" + prestito.getDataRestituzioneEffettiva());
                }

                prestiti.forEach(prestito -> {
                    System.out.println("Prestito ID: " + prestito.getId());
                    System.out.println("Utente ID: " + prestito.getUtente().getId());
                    System.out.println("Utente Nome: " + prestito.getUtente().getNome() + " " + prestito.getUtente().getCognome());
                    System.out.println("Elemento Prestato ID: " + prestito.getElementoPrestato().getId());
                    System.out.println("Data di inizio prestito: " + prestito.getDataInizioPrestiro());
                    System.out.println("Data di scadenza prestito: " + prestito.getDataRestituzionePrestito());
                    System.out.println("---------------------------------------------------");
                });
            }
        } catch (Exception e) {
            System.out.println("Errore durante la ricerca dei prestiti scaduti.");
        }
    }
}
