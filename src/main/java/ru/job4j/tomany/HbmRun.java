package ru.job4j.tomany;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

public class HbmRun {
    public static void main(String[] args) {
        final StandardServiceRegistry registry = new StandardServiceRegistryBuilder()
                .configure().build();
        try {
            SessionFactory sf = new MetadataSources(registry).buildMetadata().buildSessionFactory();
            Session session = sf.openSession();
            session.beginTransaction();

            Car one = Car.of("Niva");
            session.save(one);
            Car two = Car.of("Vesta");
            session.save(two);
            Car three = Car.of("Niva");
            session.save(three);
            Car four = Car.of("Vesta");
            session.save(four);
            Car five = Car.of("Niva");
            session.save(five);

            Brand brand = Brand.of("ADMIN");

            brand.addCar(session.load(Car.class, 1));
            brand.addCar(session.load(Car.class, 2));
            brand.addCar(session.load(Car.class, 3));
            brand.addCar(session.load(Car.class, 4));
            brand.addCar(session.load(Car.class, 5));

            session.save(brand);

            session.getTransaction().commit();
            session.close();
        }  catch (Exception e) {
            e.printStackTrace();
        } finally {
            StandardServiceRegistryBuilder.destroy(registry);
        }
    }
}
