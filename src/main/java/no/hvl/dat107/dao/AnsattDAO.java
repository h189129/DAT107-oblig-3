package no.hvl.dat107.dao;

import jakarta.persistence.*;
import no.hvl.dat107.entitet.Ansatt;
import no.hvl.dat107.entitet.Avdeling;
import no.hvl.dat107.util.JpaUtil;
import java.util.List;


public class AnsattDAO {
    private final EntityManagerFactory emf;

    public AnsattDAO(EntityManagerFactory emf) {
        this.emf = emf;
    }

    // Finn ansatt med ID
    public Ansatt finnAnsattMedId(int id) {
        return JpaUtil.executeInTransaction(emf, em -> {
            try {
                return em.createQuery("SELECT a FROM Ansatt a WHERE a.id = :id", Ansatt.class)
                        .setParameter("id", id)
                        .getSingleResult();
            } catch (NoResultException e) {
                return null;
            }
        });
    }

    // Finn ansatt med brukernavn
    public Ansatt finnAnsattMedBrukernavn(String brukernavn) {
        return JpaUtil.executeInTransaction(emf, em -> {
            try {
                return em.createQuery("SELECT a FROM Ansatt a WHERE a.brukernavn = :bn", Ansatt.class)
                        .setParameter("bn", brukernavn)
                        .getSingleResult();
            } catch (NoResultException e) {
                return null;
            }
        });
    }

    // Hent alle ansatte
    public List<Ansatt> hentAlleAnsatte() {
        return JpaUtil.executeInTransaction(emf, em ->
                em.createQuery("SELECT DISTINCT a FROM Ansatt a LEFT JOIN FETCH a.avdeling", Ansatt.class)
                        .getResultList()
        );
    }

    // Oppdater stilling og lønn på en ansatt
    public void oppdaterStilling(int id, String nyStilling) {
        JpaUtil.executeInTransaction(emf, em -> {
            Ansatt ansatt = em.find(Ansatt.class, id);
            if (ansatt != null) {
                ansatt.setStilling(nyStilling);
            }
            return null;
        });
    }

    // Oppdater lønn på en ansatt
    public void oppdaterLonn(int id, double nyLonn) {
        JpaUtil.executeInTransaction(emf, em -> {
            Ansatt ansatt = em.find(Ansatt.class, id);
            if (ansatt != null) {
                ansatt.setMaanedslonn(nyLonn);
            }
            return null;
        });
    }

    // Legg til ny ansatt
    public void leggTilNyAnsatt(Ansatt nyAnsatt) {
        JpaUtil.executeInTransaction(emf, em -> {
            em.persist(nyAnsatt);
            return null;
        });
    }

    // Oppdater avdeling
    public void oppdaterAvdelingPaaAnsatt(int ansattId, Avdeling nyAvdeling) {
        JpaUtil.executeInTransaction(emf, em -> {
            Ansatt ansatt = em.find(Ansatt.class, ansattId);
            Avdeling gammelAvdeling = ansatt.getAvdeling();

            if (gammelAvdeling != null) {
                // Fjern fra gammel avdeling (både database og in-memory)
                gammelAvdeling.getAnsatte().remove(ansatt);
                em.merge(gammelAvdeling); // Tving gjennom endringer
            }

            // Legg til i ny avdeling (både database og in-memory)
            nyAvdeling.getAnsatte().add(ansatt);
            ansatt.setAvdeling(nyAvdeling);
            em.merge(nyAvdeling); // Tving gjennom endringer

            return null;
        });
    }

    // Slett ansatt
    public void fjernAnsatt(int id) {
        JpaUtil.executeInTransaction(emf, em -> {
            Ansatt ansatt = em.find(Ansatt.class, id);
            if (ansatt != null) {
                em.remove(ansatt);
            }
            return null;
        });
    }
}