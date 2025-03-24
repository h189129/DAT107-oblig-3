package no.hvl.dat107.dao;

import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.NoResultException;
import no.hvl.dat107.entitet.Prosjekt;
import no.hvl.dat107.util.JpaUtil;

import java.util.List;

public class ProsjektDAO {
    private final EntityManagerFactory emf;
    public ProsjektDAO(EntityManagerFactory emf) {
        this.emf = emf;
    }

    //
    public void leggTilProsjekt(Prosjekt nyttProsjekt) {
        JpaUtil.executeInTransaction(emf, em -> {
            em.persist(nyttProsjekt);
            return null;
        });
    }

    public Prosjekt finnProsjektMedId(int id) {
        return JpaUtil.executeInTransaction(emf, em -> {
            try {
                return em.createQuery("SELECT a FROM Prosjekt a WHERE a.id = :id", Prosjekt.class)
                        .setParameter("id", id)
                        .getSingleResult();
            } catch (NoResultException e) {
                return null;
            }
        });
    }



}
