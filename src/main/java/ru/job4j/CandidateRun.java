package ru.job4j;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.query.Query;

public class CandidateRun {
    public static void main(String[] args) {
        final StandardServiceRegistry registry = new StandardServiceRegistryBuilder()
                .configure().build();
        try {
            SessionFactory sf = new MetadataSources(registry).buildMetadata().buildSessionFactory();
            Session session = sf.openSession();
            session.beginTransaction();

            Candidate one = Candidate.of("Alex", 11, 12);
            Candidate two = Candidate.of("Nikolay", 12, 15);
            Candidate three = Candidate.of("Nikita", 5, 35);
            Candidate four = Candidate.of("Tom", 21, 32);
            Candidate five = Candidate.of("Jon", 18, 25);
            Candidate six = Candidate.of("Rob", 7, 11);

            session.save(one);
            session.save(two);
            session.save(three);
            session.save(four);
            session.save(five);
            session.save(six);

            Query<Candidate> query = session.createQuery("from Candidate", Candidate.class);
            for (Candidate candidate : query.list()) {
                System.out.println(candidate);
            }
            query = session.createQuery("from Candidate can where can.id = :Id", Candidate.class);
            query.setParameter("Id", 3);
            System.out.println(query.uniqueResult());

            query = session.createQuery("from Candidate can where can.name = :Name", Candidate.class);
            query.setParameter("Name", "Alex");
            for (Candidate candidate : query.list()) {
                System.out.println(candidate);
            }

            session.createQuery(
                    "update Candidate can "
                        + "set can.experience = :newExperience, can.salary = :newSalary "
                        + "where can.id = :Id")
                    .setParameter("newExperience", 22)
                    .setParameter("newSalary", 15)
                    .setParameter("Id", 5)
                    .executeUpdate();

            session.createQuery("delete from Candidate where id = :Id")
                    .setParameter("Id", 6)
                    .executeUpdate();

            session.createQuery(
                     "insert into Candidate (name, experience, salary) "
                        + "select concat(can.name, 'NEW'), can.experience + 5, can.salary + 20  "
                        + "from Candidate can where can.id = :Id")
                    .setParameter("Id", 2)
                    .executeUpdate();

            session.getTransaction().commit();
            session.close();
        }  catch (Exception e) {
            e.printStackTrace();
        } finally {
            StandardServiceRegistryBuilder.destroy(registry);
        }
    }
}
