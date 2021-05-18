package pl.polsl.tab.fleetmanagement.models;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Objects;

@Entity
@Table(name = "vehicles", schema = "public", catalog = "testdb")
public class VehiclesEntity {
    private long id;
    private String vin;
    private String equipmentlevel;
    private int mileage;
    private BigDecimal avgfuelconsumption;
    private int brandsModelsId;
    private int typesId;
    private int purposesId;

    @Id
    @Column(name = "id")
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    @Basic
    @Column(name = "vin")
    public String getVin() {
        return vin;
    }

    public void setVin(String vin) {
        this.vin = vin;
    }

    @Basic
    @Column(name = "equipmentlevel")
    public String getEquipmentlevel() {
        return equipmentlevel;
    }

    public void setEquipmentlevel(String equipmentlevel) {
        this.equipmentlevel = equipmentlevel;
    }

    @Basic
    @Column(name = "mileage")
    public int getMileage() {
        return mileage;
    }

    public void setMileage(int mileage) {
        this.mileage = mileage;
    }

    @Basic
    @Column(name = "avgfuelconsumption")
    public BigDecimal getAvgfuelconsumption() {
        return avgfuelconsumption;
    }

    public void setAvgfuelconsumption(BigDecimal avgfuelconsumption) {
        this.avgfuelconsumption = avgfuelconsumption;
    }

    @Basic
    @Column(name = "brands_models_id")
    public int getBrandsModelsId() {
        return brandsModelsId;
    }

    public void setBrandsModelsId(int brandsModelsId) {
        this.brandsModelsId = brandsModelsId;
    }

    @Basic
    @Column(name = "types_id")
    public int getTypesId() {
        return typesId;
    }

    public void setTypesId(int typesId) {
        this.typesId = typesId;
    }

    @Basic
    @Column(name = "purposes_id")
    public int getPurposesId() {
        return purposesId;
    }

    public void setPurposesId(int purposesId) {
        this.purposesId = purposesId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        VehiclesEntity that = (VehiclesEntity) o;
        return id == that.id && mileage == that.mileage && brandsModelsId == that.brandsModelsId && typesId == that.typesId && purposesId == that.purposesId && Objects.equals(vin, that.vin) && Objects.equals(equipmentlevel, that.equipmentlevel) && Objects.equals(avgfuelconsumption, that.avgfuelconsumption);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, vin, equipmentlevel, mileage, avgfuelconsumption, brandsModelsId, typesId, purposesId);
    }
}
