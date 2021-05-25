package pl.polsl.tab.fleetmanagement.vehicle;


import lombok.Getter;
import pl.polsl.tab.fleetmanagement.brandmodel.BrandModelDTO;
import pl.polsl.tab.fleetmanagement.brandmodel.BrandModelEntity;
import pl.polsl.tab.fleetmanagement.vehiclepurpose.PurposeDTO;
import pl.polsl.tab.fleetmanagement.vehiclepurpose.PurposeEntity;
import pl.polsl.tab.fleetmanagement.vehicletype.TypeDTO;
import pl.polsl.tab.fleetmanagement.vehicletype.TypeEntity;

import java.math.BigDecimal;

@Getter
public class VehicleDTO {

    private long id;
    private String vin;
    private String equipmentlevel;
    private int mileage;
    private BigDecimal avgfuelconsumption;
//    private Collection<KeepingEntity> keepingsById;
//    private Collection<OperationCostsEntity> operationCostsById;
//    private Collection<VehicleUnavailabilityEntity> vehicleUnavailabilitiesById;
    private TypeDTO type;
    private PurposeDTO purpose;
    private BrandModelDTO brandmodel;


    public VehicleDTO(long id, String vin, String equipmentlevel, int mileage, BigDecimal avgfuelconsumption,
                      TypeEntity type, PurposeEntity purpose, BrandModelEntity brandmodel) {
        this.id = id;
        this.vin = vin;
        this.equipmentlevel = equipmentlevel;
        this.mileage = mileage;
        this.avgfuelconsumption = avgfuelconsumption;
        this.type = new TypeDTO(type);
        this.purpose = new PurposeDTO(purpose);
        this.brandmodel = new BrandModelDTO(brandmodel);
    }
}
