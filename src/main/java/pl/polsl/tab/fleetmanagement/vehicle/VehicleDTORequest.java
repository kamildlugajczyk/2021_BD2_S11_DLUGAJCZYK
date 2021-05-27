package pl.polsl.tab.fleetmanagement.vehicle;


import lombok.Getter;

import java.math.BigDecimal;

@Getter
public class VehicleDTORequest {

    private long id;
    private String vin;
    private String equipmentLevel;
    private int mileage;
    private BigDecimal avgFuelConsumption;
    private long typeId;
    private long purposeId;
    private long brandModelId;


    public VehicleDTORequest(long id, String vin, String equipmentLevel, int mileage, BigDecimal avgFuelConsumption,
                             long typeId, long purposeId, long brandModelId) {
        this.id = id;
        this.vin = vin;
        this.equipmentLevel = equipmentLevel;
        this.mileage = mileage;
        this.avgFuelConsumption = avgFuelConsumption;
        this.typeId = typeId;
        this.purposeId = purposeId;
        this.brandModelId = brandModelId;
    }

    public String getVin() {
        return vin;
    }

    public void setVin(String vin) {
        this.vin = vin;
    }

    public String getEquipmentLevel() {
        return equipmentLevel;
    }

    public void setEquipmentLevel(String equipmentLevel) {
        this.equipmentLevel = equipmentLevel;
    }

    public int getMileage() {
        return mileage;
    }

    public void setMileage(int mileage) {
        this.mileage = mileage;
    }

    public BigDecimal getAvgFuelConsumption() {
        return avgFuelConsumption;
    }

    public void setAvgFuelConsumption(BigDecimal avgFuelConsumption) {
        this.avgFuelConsumption = avgFuelConsumption;
    }

    public long getTypeId() {
        return typeId;
    }

    public void setTypeId(long typeId) {
        this.typeId = typeId;
    }

    public long getPurposeId() {
        return purposeId;
    }

    public void setPurposeId(long purposeId) {
        this.purposeId = purposeId;
    }

    public long getBrandModelId() {
        return brandModelId;
    }

    public void setBrandModelId(long brandModelId) {
        this.brandModelId = brandModelId;
    }
}
