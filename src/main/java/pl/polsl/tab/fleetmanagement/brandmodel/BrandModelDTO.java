package pl.polsl.tab.fleetmanagement.brandmodel;

import lombok.Getter;
import lombok.Setter;

public class BrandModelDTO {

    @Getter @Setter private long id;
    @Getter private String brand;
    @Getter private String model;
    @Getter private String modelYear;

    public BrandModelDTO() {
    }

    public BrandModelDTO(String brand, String model, String modelYear) {
        this.brand = brand;
        this.model = model;
        this.modelYear = modelYear;
    }

    public BrandModelDTO(long id, String brand, String model, String modelYear) {
        this.id = id;
        this.brand = brand;
        this.model = model;
        this.modelYear = modelYear;
    }

    public BrandModelDTO(BrandModelEntity brandModelEntity) {
        this.id = brandModelEntity.getId();
        this.brand = brandModelEntity.getBrand();
        this.model = brandModelEntity.getModel();
        this.modelYear = brandModelEntity.getModelYear();
    }
}
