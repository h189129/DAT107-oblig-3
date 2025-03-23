package no.hvl.dat107.entitet;

import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "avdeling")
public class Avdeling {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "navn", nullable = false, length = 50)
    private String navn;

    @OneToOne
    @JoinColumn(name = "sjef_id", referencedColumnName = "id", nullable = false)
    private Ansatt sjef;

    @OneToMany(mappedBy = "avdeling")
    private List<Ansatt> ansatte = new ArrayList<>();

    public Avdeling() {}

    public Avdeling(String navn, Ansatt sjef) {
        this.navn = navn;
        this.sjef = sjef;

    }

    public void setSjef(Ansatt nySjef) {
        this.sjef = nySjef;
    }

    @PreRemove
    private void validerSletting() {
        if (!ansatte.isEmpty()) {
            throw new IllegalStateException("Kan ikke slette avdeling med ansatte");
        }
    }

    public void leggTilAnsatt(Ansatt ansatt) {
        if (!ansatte.contains(ansatt)) {
            ansatte.add(ansatt);
            ansatt.setAvdeling(this); // Oppdaterer den ansattes avdeling
        }
    }
    // Gettere og settere
    public int getId() {
        return id;
    }

    public String getNavn() {
        return navn;
    }

    public Ansatt getSjef() {
        return sjef;
    }

    public List<Ansatt> getAnsatte() {
        return ansatte;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setNavn(String navn) {
        this.navn = navn;
    }

    public void setAnsatte(List<Ansatt> ansatte) {
        this.ansatte = ansatte;
    }

    public void skrivUtAvdeling() {
        System.out.println();
        System.out.println("Avdelings ID: " + id);
        System.out.println("Avdeling: " + navn);
        System.out.println("Avdelingssjef: " + sjef);
    }
}