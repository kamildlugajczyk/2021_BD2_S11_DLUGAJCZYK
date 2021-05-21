package pl.polsl.tab.fleetmanagement.brandmodel;

//@Getter
public class BrandModelDTO {

    private long id;
    private String brand;
    private String model;
    private String modelYear;

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

    public BrandModelDTO(BrandsModelsEntity brandsModelsEntity) {
        this.id = brandsModelsEntity.getId();
        this.brand = brandsModelsEntity.getBrand();
        this.model = brandsModelsEntity.getModel();
        this.modelYear = brandsModelsEntity.getModelYear();
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getBrand() {
        return brand;
    }

    public String getModel() {
        return model;
    }

    public String getModelYear() {
        return modelYear;
    }
}
