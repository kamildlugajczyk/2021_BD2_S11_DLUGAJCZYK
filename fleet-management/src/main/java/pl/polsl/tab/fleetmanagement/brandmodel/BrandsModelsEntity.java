package pl.polsl.tab.fleetmanagement.brandmodel;

import net.bytebuddy.dynamic.loading.InjectionClassLoader;
import pl.polsl.tab.fleetmanagement.vehicle.VehiclesEntity;

import javax.persistence.*;
import java.util.Collection;
import java.util.Objects;

@Entity
@Table(name = "brands_models", schema = "public", catalog = "testdb")
public class BrandsModelsEntity {
    private long id;
    private String brand;
    private String model;
    private String modelYear;
    private Collection<VehiclesEntity> vehiclesById;

    public BrandsModelsEntity() {
    }

    public BrandsModelsEntity(String brand, String model, String modelYear) {
        this.brand = brand;
        this.model = model;
        this.modelYear = modelYear;
    }

    public BrandsModelsEntity(long id, String brand, String model, String modelYear) {
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
    public Collection<VehiclesEntity> getVehiclesById() {
        return vehiclesById;
    }

    public void setVehiclesById(Collection<VehiclesEntity> vehiclesById) {
        this.vehiclesById = vehiclesById;
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
