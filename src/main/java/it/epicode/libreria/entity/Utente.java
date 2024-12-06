package it.epicode.libreria.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@Table(name = "utenti")
@NamedQuery(name = "Trova_tutto_Utente", query = "SELECT a FROM Utente a")
public class Utente {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @Column(nullable = false)
    private String nome;

    @Column(nullable = false)
    private String cognome;

    @Column(name = "data_di_nascita",nullable = false)
    private LocalDate dataDiNascita;

    @Column(name = "numero_di_tessera",nullable = false)
    private Long numeroDiTessera;

    @OneToMany(mappedBy = "utente")
    private List<Prestito> prestiti = new ArrayList<>();



}