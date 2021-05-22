package pl.polsl.tab.fleetmanagement.vehicle;


import lombok.Getter;
import pl.polsl.tab.fleetmanagement.brandmodel.BrandsModelsEntity;
import pl.polsl.tab.fleetmanagement.vehiclepurpose.PurposesEntity;
import pl.polsl.tab.fleetmanagement.vehicletype.TypesEntity;

import java.math.BigDecimal;

@Getter
public class VehicleDTO {

    private long id;
    private String vin;
    private String equipmentlevel;
    private int mileage;
    private BigDecimal avgfuelconsumption;
    private String brand;
    private String model;
    private String type;
    private String purpose;
//    private Collection<KeepingEntity> keepingsById;
//    private Collection<OperationCostsEntity> operationCostsById;
//    private Collection<VehicleUnavailabilityEntity> vehicleUnavailabilitiesById;
//    private TypesEntity typesByTypesId;
//    private PurposesEntity purposesByPurposesId;
//    private BrandsModelsEntity brandsModelsByBrandsModelsId;

    public VehicleDTO(long id, String vin, String equipmentlevel, int mileage, BigDecimal avgfuelconsumption,
                      TypesEntity typesByTypesId, PurposesEntity purposesByPurposesId, BrandsModelsEntity brandsModelsByBrandsModelsId) {
        this.id = id;
        this.vin = vin;
        this.equipmentlevel = equipmentlevel;
        this.mileage = mileage;
        this.avgfuelconsumption = avgfuelconsumption;
        this.brand = brandsModelsByBrandsModelsId.getBrand();
        this.model = brandsModelsByBrandsModelsId.getModel();
        this.type = typesByTypesId.getName();
        this.purpose = purposesByPurposesId.getName();
    }

    public VehicleDTO(String vin, String equipmentlevel, int mileage, BigDecimal avgfuelconsumption,
                      TypesEntity typesByTypesId, PurposesEntity purposesByPurposesId, BrandsModelsEntity brandsModelsByBrandsModelsId) {
        this.vin = vin;
        this.equipmentlevel = equipmentlevel;
        this.mileage = mileage;
        this.avgfuelconsumption = avgfuelconsumption;
        this.brand = brandsModelsByBrandsModelsId.getBrand();
        this.model = brandsModelsByBrandsModelsId.getModel();
        this.type = typesByTypesId.getName();
        this.purpose = purposesByPurposesId.getName();
    }

}
