package org.hibernate.bugs;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.Date;
import java.util.UUID;

/**
 * This template demonstrates how to develop a test case for Hibernate ORM, using the Java Persistence API.
 */
public class JPAUnitTestCase {

    private EntityManagerFactory entityManagerFactory;

    @Before
    public void init() {
        entityManagerFactory = Persistence.createEntityManagerFactory("templatePU");
    }

    @After
    public void destroy() {
        entityManagerFactory.close();
    }

    // Entities are auto-discovered, so just add them anywhere on class-path
    // Add your tests, using standard JUnit.
    @Test
    public void hhh16948() throws Exception {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        entityManager.getTransaction().begin();
        ///
        reproduceHHH16948(entityManager);
        //
        entityManager.getTransaction().commit();
        entityManager.close();
    }

    private static void reproduceHHH16948(EntityManager entityManager) {
        // given
        final Company company = new Company();
        company.setId(UUID.randomUUID());
        company.setEstablishedAt(new Date());
        entityManager.persist(company);
        entityManager.flush();
        entityManager.detach(company);

        //when
        final Company found = entityManager.find(Company.class, company.getId());

        // then
        Assert.assertNotNull("entity found", company.getId());
        Assert.assertEquals("passes, because java.sql.Timestamp extends java.util.Date",
            company.getEstablishedAt(), found.getEstablishedAt());
        Assert.assertEquals("the same type is used. Otherwise, equals() is broken.",
            company.getEstablishedAt().getClass(), found.getEstablishedAt().getClass());
    }
}
