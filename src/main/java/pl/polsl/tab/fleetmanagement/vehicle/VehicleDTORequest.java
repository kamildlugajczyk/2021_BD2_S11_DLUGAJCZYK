package pl.polsl.tab.fleetmanagement.vehicle;


import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

public class VehicleDTORequest {

    @Getter @Setter private long id;
    @Getter @Setter private String vin;
    @Getter @Setter private String plates;
    @Getter @Setter private String equipmentLevel;
    @Getter @Setter private int mileage;
    @Getter @Setter private BigDecimal avgFuelConsumption;
    @Getter @Setter private long typeId;
    @Getter @Setter private long purposeId;
    @Getter @Setter private long brandModelId;


    public VehicleDTORequest(long id, String vin, String plates, String equipmentLevel, int mileage, BigDecimal avgFuelConsumption,
                             long typeId, long purposeId, long brandModelId) {
        this.id = id;
        this.vin = vin;
        this.plates = plates;
        this.equipmentLevel = equipmentLevel;
        this.mileage = mileage;
        this.avgFuelConsumption = avgFuelConsumption;
        this.typeId = typeId;
        this.purposeId = purposeId;
        this.brandModelId = brandModelId;
    }
}
