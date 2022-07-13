package ru.job4j.lazy;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

import java.util.ArrayList;
import java.util.List;

public class HbnCarRun {
    public static void main(String[] args) {
        List<CarBrand> list = new ArrayList<>();
        final StandardServiceRegistry registry = new StandardServiceRegistryBuilder()
                .configure().build();
        try (SessionFactory sf = new MetadataSources(registry).buildMetadata().buildSessionFactory();
             Session session = sf.openSession()) {
            session.beginTransaction();

            /*
            CarBrand brand = CarBrand.of("VAZ");
            CarModel modelOne = CarModel.of("Vesta", brand);
            CarModel modelTwo = CarModel.of("Granta", brand);
            CarModel modelThree = CarModel.of("Niva", brand);
            session.persist(brand);
            session.persist(modelOne);
            session.persist(modelTwo);
            session.persist(modelThree);
            */

            list = session.createQuery(
                    "select distinct c from CarBrand c join fetch c.cars", CarBrand.class
            ).list();
            System.out.println(list.size());
            session.getTransaction().commit();
        }  catch (Exception e) {
            e.printStackTrace();
        } finally {
            StandardServiceRegistryBuilder.destroy(registry);
        }
        for (CarModel carModel : list.get(0).getCars()) {
            System.out.println(carModel);
        }
    }
}
