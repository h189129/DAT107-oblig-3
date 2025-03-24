package no.hvl.dat107.dao;

import jakarta.persistence.EntityManagerFactory;
import no.hvl.dat107.entitet.Prosjektdeltagelse;
import no.hvl.dat107.util.JpaUtil;

public class ProsjektdeltagelseDAO {
    private final EntityManagerFactory emf;
    public ProsjektdeltagelseDAO(EntityManagerFactory emf) { this.emf = emf; }

    public void registrerDeltagelse(Prosjektdeltagelse prosjektdeltagelse) {
        JpaUtil.executeInTransaction(emf, em -> {
            em.persist(prosjektdeltagelse);
            return null;
        });
    }

    public Prosjektdeltagelse finnProsjektdeltagelse(int ansattId, int prosjektId) {
        JpaUtil.executeInTransaction(emf, em -> {

            Prosjektdeltagelse deltagelse;
            deltagelse = em.createQuery(
                            "SELECT pd FROM Prosjektdeltagelse pd WHERE pd.ansatt.id = :ansattId AND pd.prosjekt.id = :prosjektId", Prosjektdeltagelse.class)
                    .setParameter("ansattId", ansattId)
                    .setParameter("prosjektId", prosjektId)
                    .getSingleResult();
            return deltagelse;
        });
        return null;
    }

    public void oppdaterTimer(int ansattId, int prosjektId, int nyeTimer) {
        JpaUtil.executeInTransaction(emf, em -> {

            Prosjektdeltagelse deltagelse = em.createQuery(
                            "SELECT pd FROM Prosjektdeltagelse pd WHERE pd.ansatt.id = :ansattId AND pd.prosjekt.id = :prosjektId", Prosjektdeltagelse.class)
                    .setParameter("ansattId", ansattId)
                    .setParameter("prosjektId", prosjektId)
                    .getSingleResult();

            deltagelse.setTimer(deltagelse.getTimer() + nyeTimer);

            em.merge(deltagelse);
            return null;
        });
    }
}
