package no.hvl.dat107.entitet;

import jakarta.persistence.Entity;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
public class Prosjekt {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "navn", unique = true, nullable=false, length=100)
    private String navn;

    @Column(name = "beskrivelse", nullable=false, length=255)
    private String beskrivelse;

    @OneToMany(mappedBy = "prosjekt")
    private List<Prosjektdeltagelse> deltagelser = new ArrayList<>();

    // Constructors
    public Prosjekt() {}

    public Prosjekt(String navn, String beskrivelse) {
        this.navn = navn;
        this.beskrivelse = beskrivelse;

    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNavn() {
        return navn;
    }

    public void setNavn(String navn) {
        this.navn = navn;
    }

    public String getBeskrivelse() {
        return beskrivelse;
    }

    public void setBeskrivelse(String beskrivelse) {
        this.beskrivelse = beskrivelse;
    }

    public List<Prosjektdeltagelse> getDeltagelser() {
        return deltagelser;
    }

    public void setDeltagelser(List<Prosjektdeltagelse> deltagelser) {
        this.deltagelser = deltagelser;
    }

    @Override
    public String toString() {
        return "Prosjekt{" +
                "id=" + id +
                ", navn='" + navn + '\'' +
                ", beskrivelse='" + beskrivelse + '\'' +
                ", deltagelser=" + deltagelser +
                '}';
    }

    public void skrivUtProsjekt() {
        System.out.println();
        System.out.println("Prosjekt ID: " + id);
        System.out.println("Prosjektnavn: " + navn);
        System.out.println("Prosjektbeskrivelse: " + beskrivelse);
    }
}
