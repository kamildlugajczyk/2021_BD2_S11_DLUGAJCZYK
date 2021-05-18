package pl.polsl.tab.fleetmanagement.models;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "brands_models", schema = "public", catalog = "testdb")
public class BrandsModelsEntity {
    private long id;
    private String brand;
    private String model;
    private String modelYear;

    @Id
    @Column(name = "id")
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    @Basic
    @Column(name = "brand")
    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    @Basic
    @Column(name = "model")
    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    @Basic
    @Column(name = "model_year")
    public String getModelYear() {
        return modelYear;
    }

    public void setModelYear(String modelYear) {
        this.modelYear = modelYear;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BrandsModelsEntity that = (BrandsModelsEntity) o;
        return id == that.id && Objects.equals(brand, that.brand) && Objects.equals(model, that.model) && Objects.equals(modelYear, that.modelYear);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, brand, model, modelYear);
    }
}
