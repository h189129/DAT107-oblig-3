package no.hvl.dat107.entitet;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "ansatt")
public class Ansatt {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "brukernavn", unique = true, nullable = false, length = 4)
    private String brukernavn;

    @Column(name = "fornavn", nullable = false, length = 50)
    private String fornavn;

    @Column(name = "etternavn", nullable = false, length = 50)
    private String etternavn;

    @Column(name = "ansettelsesdato", nullable = false)
    private LocalDate ansettelsesdato;

    @Column(name = "stilling", nullable = false, length = 50)
    private String stilling;

    @Column(name = "maanedslonn", nullable = false)
    private double maanedslonn;

    @ManyToOne
    @JoinColumn(name = "avdeling_id", referencedColumnName = "id", nullable = false)
    private Avdeling avdeling;

    // Constructors
    public Ansatt() {}

    public Ansatt(
            String brukernavn,
            String fornavn,
            String etternavn,
            LocalDate ansettelsesdato,
            String stilling,
            double maanedslonn,
            Avdeling avdeling) {
        this.brukernavn = brukernavn;
        this.fornavn = fornavn;
        this.etternavn = etternavn;
        this.ansettelsesdato = ansettelsesdato;
        this.stilling = stilling;
        this.maanedslonn = maanedslonn;
        this.avdeling = avdeling;
    }

    // Getters and setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getBrukernavn() { return brukernavn; }
    public void setBrukernavn(String brukernavn) { this.brukernavn = brukernavn; }

    public String getFornavn() { return fornavn; }
    public void setFornavn(String fornavn) { this.fornavn = fornavn; }

    public String getEtternavn() { return etternavn; }
    public void setEtternavn(String etternavn) { this.etternavn = etternavn; }

    public LocalDate getAnsettelsesdato() { return ansettelsesdato; }
    public void setAnsettelsesdato(LocalDate ansettelsesdato) { this.ansettelsesdato = ansettelsesdato; }

    public String getStilling() { return stilling; }
    public void setStilling(String stilling) { this.stilling = stilling; }

    public double getMaanedslonn() { return maanedslonn; }
    public void setMaanedslonn(double maanedslonn) { this.maanedslonn = maanedslonn; }

    public Avdeling getAvdeling() { return avdeling; }
    public void setAvdeling(Avdeling avdeling) { this.avdeling = avdeling; }

    // Skriver ut all ansatt informasjon
    public void skrivUt() {
        System.out.println();
        System.out.println("Ansatt ID: " + id);
        System.out.println("Brukernavn: " + brukernavn);
        System.out.println("Fornavn: " + fornavn);
        System.out.println("Etternavn: " + etternavn);
        System.out.println("Ansettelsesdato: " + ansettelsesdato);
        System.out.println("Stilling: " + stilling);
        System.out.println("Månedslønn: " + maanedslonn);
        System.out.println("Avdeling ID: " + avdeling.getId());
        System.out.println("Avdeling Navn: " + avdeling.getNavn());

    }
}