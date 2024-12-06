package it.epicode.libreria.entity;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name ="riviste")
@NamedQuery(name = "Trova_tutto_Rivista", query = "SELECT a FROM Rivista a")
public class Rivista  extends Catalogo{

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Periodo periodo;

    @Override
    public String toString() {
        return "Rivista{" +
                "titolo='" + getTitolo() + '\'' +
                ", codiceISBN='" + getCodiceISBN() + '\'' +
                ", annoDiPubblicazione=" + getAnnoDiPubblicazione() +
                ", numeroPagine=" + getNumeroPagine() +
                ", periodo=" + periodo +
                '}';
    }

}