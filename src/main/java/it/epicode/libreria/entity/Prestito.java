package it.epicode.libreria.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;

@Data
@Entity
@Table(name = "prestiti")
@NamedQuery(name = "Trova_tutto_Prestito", query = "SELECT a FROM Prestito a")
public class Prestito {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "utente_id")
    private Utente utente;

    @ManyToOne
    @JoinColumn(name = "pubblicazione_id")
    private Catalogo elementoPrestato;

    @Column(name = "data_inizio_prestito",nullable = false)
    private LocalDate dataInizioPrestiro;

    @Column(name = "data_restituzione_prestito",nullable = false)
    private LocalDate dataRestituzionePrestito;

    @Column(name = "data_restituzione_effettiva",nullable = false)
    private String  dataRestituzioneEffettiva;


}