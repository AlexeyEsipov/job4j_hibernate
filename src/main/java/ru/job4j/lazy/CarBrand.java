package ru.job4j.lazy;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "car_brand")
public class CarBrand {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String name;

    @OneToMany(mappedBy = "carBrand")
    private List<CarModel> cars = new ArrayList<>();

    public static CarBrand of(String name) {
        CarBrand carBrand = new CarBrand();
        carBrand.name = name;
        return carBrand;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<CarModel> getCars() {
        return cars;
    }

    public void setCars(List<CarModel> cars) {
        this.cars = cars;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof CarBrand)) {
            return false;
        }
        CarBrand carBrand = (CarBrand) o;
        return id == carBrand.id && Objects.equals(name, carBrand.name) && Objects.equals(cars, carBrand.cars);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, cars);
    }

    @Override
    public String toString() {
        return String.format("CarBrand{id=%s, name=%s}",
                id, name);
    }
}
