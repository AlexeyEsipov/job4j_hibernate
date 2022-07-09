package ru.job4j.manytomany;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

public class AuthorRun {

        public static void main(String[] args) {
            final StandardServiceRegistry registry = new StandardServiceRegistryBuilder()
                    .configure().build();
            try (SessionFactory sf = new MetadataSources(registry).buildMetadata().buildSessionFactory();
                 Session session = sf.openSession()) {
                session.beginTransaction();
                Book one = Book.of("Book First");
                Book two = Book.of("Book Second");
                Author first = Author.of("Author Nikolay");
                first.getBooks().add(one);
                first.getBooks().add(two);
                Author second = Author.of("Author Anatoliy");
                second.getBooks().add(two);
                session.persist(first);
                session.persist(second);
                Author author = session.get(Author.class, 1);
                session.remove(author);
                session.getTransaction().commit();
            }  catch (Exception e) {
                e.printStackTrace();
            } finally {
                StandardServiceRegistryBuilder.destroy(registry);
            }
        }
    }



