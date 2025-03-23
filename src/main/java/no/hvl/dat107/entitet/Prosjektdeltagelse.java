package no.hvl.dat107.entitet;

import jakarta.persistence.*;

@Entity
public class Prosjektdeltagelse {
    @Id @GeneratedValue
    private int id;

    @ManyToOne
    private Ansatt ansatt;

    @ManyToOne
    private Prosjekt prosjekt;

    @Column(name = "rolle", nullable = false, length=100)
    private String rolle;

    @Column(name = "timer", length=4)
    private int timer = 0;

    public Prosjektdeltagelse() {}

    public Prosjektdeltagelse(
            int id,
            Ansatt ansatt,
            Prosjekt prosjekt,
            String rolle,
            int timer
    ) {
        this.id = id;
        this.ansatt = ansatt;
        this.prosjekt = prosjekt;
        this.rolle = rolle;
        this.timer = timer;
    }

    // Getters and setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Ansatt getAnsatt() {
        return ansatt;
    }

    public void setAnsatt(Ansatt ansatt) {
        this.ansatt = ansatt;
    }

    public Prosjekt getProsjekt() {
        return prosjekt;
    }

    public void setProsjekt(Prosjekt prosjekt) {
        this.prosjekt = prosjekt;
    }

    public String getRolle() {
        return rolle;
    }

    public void setRolle(String rolle) {
        this.rolle = rolle;
    }

    public int getTimer() {
        return timer;
    }

    public void setTimer(int timer) {
        this.timer = timer;
    }

    @Override
    public String toString() {
        return "Prosjektdeltagelse{" +
                "id=" + id +
                ", ansatt=" + ansatt +
                ", prosjekt=" + prosjekt +
                ", rolle='" + rolle + '\'' +
                ", timer=" + timer +
                '}';
    }
}
