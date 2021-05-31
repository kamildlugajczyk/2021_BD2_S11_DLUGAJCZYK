package pl.polsl.tab.fleetmanagement.vehicle.brandmodel;

import pl.polsl.tab.fleetmanagement.vehicle.VehicleEntity;

import javax.persistence.*;
import java.util.Collection;
import java.util.Objects;

@Entity
@Table(name = "brands_models", schema = "public", catalog = "testdb")
public class BrandModelEntity {
    private long id;
    private String brand;
    private String model;
    private String modelYear;
    private Collection<VehicleEntity> vehiclesById;

    public BrandModelEntity() {
    }

    public BrandModelEntity(String brand, String model, String modelYear) {
        this.brand = brand;
        this.model = model;
        this.modelYear = modelYear;
    }

    public BrandModelEntity(long id, String brand, String model, String modelYear) {
        this.id = id;
        this.brand = brand;
        this.model = model;
        this.modelYear = modelYear;
    }

    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    @Basic
    @Column(name = "brand", nullable = false, length = 50)
    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    @Basic
    @Column(name = "model", nullable = false, length = 50)
    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    @Basic
    @Column(name = "model_year", length = 50)
    public String getModelYear() {
        return modelYear;
    }

    public void setModelYear(String modelYear) {
        this.modelYear = modelYear;
    }

    @OneToMany(mappedBy = "brandsModelsByBrandsModelsId", fetch = FetchType.LAZY)
    public Collection<VehicleEntity> getVehiclesById() {
        return vehiclesById;
    }

    public void setVehiclesById(Collection<VehicleEntity> vehiclesById) {
        this.vehiclesById = vehiclesById;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BrandModelEntity that = (BrandModelEntity) o;
        return id == that.id && Objects.equals(brand, that.brand) && Objects.equals(model, that.model) && Objects.equals(modelYear, that.modelYear);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, brand, model, modelYear);
    }
}
