package no.hvl.dat107.entitet;

import jakarta.persistence.*;

@Entity
@Table(name = "prosjektdeltagelse")
public class Prosjektdeltagelse {
    @Id
    @GeneratedValue
    private int id;

    @ManyToOne
    @JoinColumn(name = "ansatt_id", nullable = false)
    private Ansatt ansatt;

    @ManyToOne
    @JoinColumn(name = "prosjekt_id", nullable = false)
    private Prosjekt prosjekt;

    @Column(name = "rolle", nullable = false, length = 100)
    private String rolle;

    @Column(name = "timer")
    private int timer = 0;

    // Constructors
    public Prosjektdeltagelse() {}

    public Prosjektdeltagelse(
            Ansatt ansatt,
            Prosjekt prosjekt,
            String rolle
    ) {
        this.ansatt = ansatt;
        this.prosjekt = prosjekt;
        this.rolle = rolle;
        this.timer = 0;
    }

    // Getters og setters
    public int getId() {
        return id;
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
                ", ansatt=" + ansatt.getId() +  // Viser kun ID for ansatt
                ", prosjekt=" + prosjekt.getId() + // Viser kun ID for prosjekt
                ", rolle='" + rolle + '\'' +
                ", timer=" + timer +
                '}';
    }
}