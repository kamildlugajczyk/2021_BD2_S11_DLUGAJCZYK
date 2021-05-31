package pl.polsl.tab.fleetmanagement.vehicle;


import lombok.Getter;
import pl.polsl.tab.fleetmanagement.vehicle.brandmodel.BrandModelDTO;
import pl.polsl.tab.fleetmanagement.vehicle.brandmodel.BrandModelEntity;
import pl.polsl.tab.fleetmanagement.vehicle.purpose.PurposeDTO;
import pl.polsl.tab.fleetmanagement.vehicle.purpose.PurposeEntity;
import pl.polsl.tab.fleetmanagement.vehicle.type.TypeDTO;
import pl.polsl.tab.fleetmanagement.vehicle.type.TypeEntity;

import java.math.BigDecimal;

@Getter
public class VehicleDTO {

    private long id;
    private String vin;
    private String plates;
    private String equipmentLevel;
    private int mileage;
    private BigDecimal avgFuelConsumption;
    //    private Collection<KeepingEntity> keepingsById;
//    private Collection<OperationCostsEntity> operationCostsById;
//    private Collection<VehicleUnavailabilityEntity> vehicleUnavailabilitiesById;
    private TypeDTO type;
    private PurposeDTO purpose;
    private BrandModelDTO brandmodel;


    public VehicleDTO(long id, String vin, String plates, String equipmentLevel, int mileage, BigDecimal avgFuelConsumption,
                      TypeEntity type, PurposeEntity purpose, BrandModelEntity brandmodel) {
        this.id = id;
        this.vin = vin;
        this.plates = plates;
        this.equipmentLevel = equipmentLevel;
        this.mileage = mileage;
        this.avgFuelConsumption = avgFuelConsumption;
        this.type = new TypeDTO(type);
        this.purpose = new PurposeDTO(purpose);
        this.brandmodel = new BrandModelDTO(brandmodel);
    }

    public VehicleDTO(VehicleEntity vehicleEntity) {
        this.id = vehicleEntity.getId();
        this.vin = vehicleEntity.getVin();
        this.plates = vehicleEntity.getPlates();
        this.equipmentLevel = vehicleEntity.getEquipmentLevel();
        this.mileage = vehicleEntity.getMileage();
        this.avgFuelConsumption = vehicleEntity.getAvgFuelConsumption();
        this.type = new TypeDTO(vehicleEntity.getTypesByTypesId());
        this.purpose = new PurposeDTO(vehicleEntity.getPurposesByPurposesId());
        this.brandmodel = new BrandModelDTO(vehicleEntity.getBrandsModelsByBrandsModelsId());
    }
}
