package ru.job4j.lazy;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "car_model")
public class CarModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String name;
    @ManyToOne
    @JoinColumn(name = "car_brand_id")
    private CarBrand carBrand;

    public static CarModel of(String name, CarBrand carbrand) {
        CarModel carModel = new CarModel();
        carModel.name = name;
        carModel.carBrand = carbrand;
        return carModel;
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

    public CarBrand getCarBrand() {
        return carBrand;
    }

    public void setCarBrand(CarBrand carBrand) {
        this.carBrand = carBrand;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof CarModel)) {
            return false;
        }
        CarModel carModel = (CarModel) o;
        return id == carModel.id && Objects.equals(name, carModel.name) && Objects.equals(carBrand, carModel.carBrand);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, carBrand);
    }

    @Override
    public String toString() {
        return String.format(
        "CarModel{id=%s, name=%s, %s}", id, name, carBrand);
    }
}
