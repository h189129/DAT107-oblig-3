package no.hvl.dat107.util;

import jakarta.persistence.*;
import java.util.function.Function;

public class JpaUtil {

    // Håndterer både returverdier og void-operasjoner
    public static <T> T executeInTransaction(
            EntityManagerFactory emf,
            Function<EntityManager, T> operation
    ) {
        try (EntityManager em = emf.createEntityManager()) {
            EntityTransaction tx = em.getTransaction();
            try {
                tx.begin();
                T result = operation.apply(em);
                tx.commit();
                return result;
            } catch (Exception e) {
                if (tx.isActive()) {
                    tx.rollback();
                }
                throw new PersistenceException("Transaksjon feilet", e);
            }
        }
    }
}