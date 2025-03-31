package no.hvl.dat107.dao;

import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.NoResultException;
import no.hvl.dat107.entitet.Ansatt;
import no.hvl.dat107.entitet.Avdeling;
import no.hvl.dat107.util.JpaUtil;
import java.util.List;

public class AvdelingDAO {
    private final EntityManagerFactory emf;

    public AvdelingDAO(EntityManagerFactory emf) {
        this.emf = emf;
    }

    // Finn avdeling med ID
    public Avdeling finnAvdelingMedId(int id) {
        return JpaUtil.executeInTransaction(emf, em -> {
            try {
                return em.createQuery(
                        "SELECT a FROM Avdeling a LEFT JOIN FETCH a.ansatte WHERE a.id = :id",
                        Avdeling.class
                ).setParameter("id", id).getSingleResult();
            } catch (NoResultException e) {
                return null;
            }
        });
    }

    // Hent alle avdelinger
    public List<Avdeling> hentAlleAvdelinger() {
        return JpaUtil.executeInTransaction(emf, em ->
                em.createQuery(
                        "SELECT DISTINCT a FROM Avdeling a LEFT JOIN FETCH a.ansatte",
                        Avdeling.class
                ).getResultList()
        );
    }

    // Oppdater avdeling
    public void oppdaterAvdeling(Avdeling avdeling) {
        JpaUtil.executeInTransaction(emf, em -> {
            em.merge(avdeling);
            return null;
        });
    }

    // Slett avdeling
    public void slettAvdeling(int id) {
        JpaUtil.executeInTransaction(emf, em -> {
            Avdeling avdeling = em.find(Avdeling.class, id);
            if (avdeling != null) {
                em.remove(avdeling);
            }
            return null;
        });
    }

    // Hjelpemetode for å sjekke om sjef finnes
    public boolean erSjefIAnnenAvdeling(int sjefId, int unntattAvdelingId) {
        return JpaUtil.executeInTransaction(emf, em -> {
            Long antall = em.createQuery(
                            "SELECT COUNT(a) FROM Avdeling a WHERE a.sjef.id = :sjefId AND a.id <> :unntattId",
                            Long.class
                    )
                    .setParameter("sjefId", sjefId)
                    .setParameter("unntattId", unntattAvdelingId)
                    .getSingleResult();
            return antall > 0;
        });
    }

    public void leggTilNyAvdelingMedSjef(String navn, int sjefId) {
        JpaUtil.executeInTransaction(emf, em -> {
            Ansatt sjef = em.find(Ansatt.class, sjefId);
            if (sjef == null) {
                throw new IllegalArgumentException("Ingen ansatt funnet med ID " + sjefId);
            }
            // Sjekk om sjef allerede er sjef i en annen avdeling
            if (erSjefIAnnenAvdeling(sjefId, -1)) {
                throw new IllegalStateException(sjef.getFornavn() + " er allerede sjef i en annen avdeling!");
            }
            Avdeling nyAvdeling = new Avdeling(navn, sjef);
            em.persist(nyAvdeling);
            // Oppdater sjefens avdeling
            sjef.setAvdeling(nyAvdeling);

            // Legg til sjef i ansatte-listen
            nyAvdeling.getAnsatte().add(sjef);
            return null;
        });
    }

    // Flytter sjef til ny avdeling
    public void flyttSjefTilNyAvdeling(int gammelAvdelingId, int nyAvdelingId, int nySjefId) {
        JpaUtil.executeInTransaction(emf, em -> {
            Avdeling gammelAvdeling = em.find(Avdeling.class, gammelAvdelingId);
            Avdeling nyAvdeling = em.find(Avdeling.class, nyAvdelingId);
            Ansatt nySjef = em.find(Ansatt.class, nySjefId);

            // Oppdater relasjoner
            Ansatt gammelSjef = gammelAvdeling.getSjef();
            gammelAvdeling.setSjef(nySjef);
            nyAvdeling.setSjef(gammelSjef);

            // Flytt sjefen til ny avdeling
            gammelSjef.setAvdeling(nyAvdeling);
            gammelAvdeling.getAnsatte().remove(gammelSjef);
            nyAvdeling.leggTilAnsatt(gammelSjef);

            return null;
        });
    }
}