package pl.polsl.tab.fleetmanagement.vehicle;

import pl.polsl.tab.fleetmanagement.brandmodel.BrandModelEntity;
import pl.polsl.tab.fleetmanagement.keeping.KeepingEntity;
import pl.polsl.tab.fleetmanagement.model.OperationCostsEntity;
import pl.polsl.tab.fleetmanagement.model.VehicleUnavailabilityEntity;
import pl.polsl.tab.fleetmanagement.vehiclepurpose.PurposeEntity;
import pl.polsl.tab.fleetmanagement.vehicletype.TypeEntity;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Collection;
import java.util.Objects;

@Entity
@Table(name = "vehicles", schema = "public", catalog = "testdb")
public class VehicleEntity {
    private long id;
    private String vin;
    private String equipmentlevel;
    private int mileage;
    private BigDecimal avgfuelconsumption;
    private int brandsModelsId;
    private int typesId;
    private int purposesId;
    private Collection<KeepingEntity> keepingsById;
    private Collection<OperationCostsEntity> operationCostsById;
    private Collection<VehicleUnavailabilityEntity> vehicleUnavailabilitiesById;
    private TypeEntity typesByTypesId;
    private PurposeEntity purposesByPurposesId;
    private BrandModelEntity brandsModelsByBrandsModelsId;

    @Id
    @Column(name = "id", nullable = false)
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    @Basic
    @Column(name = "vin", nullable = false, length = 50)
    public String getVin() {
        return vin;
    }

    public void setVin(String vin) {
        this.vin = vin;
    }

    @Basic
    @Column(name = "equipmentlevel", nullable = false, length = 8)
    public String getEquipmentlevel() {
        return equipmentlevel;
    }

    public void setEquipmentlevel(String equipmentlevel) {
        this.equipmentlevel = equipmentlevel;
    }

    @Basic
    @Column(name = "mileage", nullable = false)
    public int getMileage() {
        return mileage;
    }

    public void setMileage(int mileage) {
        this.mileage = mileage;
    }

    @Basic
    @Column(name = "avgfuelconsumption", nullable = false, precision = 2)
    public BigDecimal getAvgfuelconsumption() {
        return avgfuelconsumption;
    }

    public void setAvgfuelconsumption(BigDecimal avgfuelconsumption) {
        this.avgfuelconsumption = avgfuelconsumption;
    }

    @Basic
    @Column(name = "brands_models_id", nullable = false)
    public int getBrandsModelsId() {
        return brandsModelsId;
    }

    public void setBrandsModelsId(int brandsModelsId) {
        this.brandsModelsId = brandsModelsId;
    }

    @Basic
    @Column(name = "types_id", nullable = false)
    public int getTypesId() {
        return typesId;
    }

    public void setTypesId(int typesId) {
        this.typesId = typesId;
    }

    @Basic
    @Column(name = "purposes_id", nullable = false)
    public int getPurposesId() {
        return purposesId;
    }

    public void setPurposesId(int purposesId) {
        this.purposesId = purposesId;
    }

    @OneToMany(mappedBy = "vehiclesByVehiclesId")
    public Collection<KeepingEntity> getKeepingsById() {
        return keepingsById;
    }

    public void setKeepingsById(Collection<KeepingEntity> keepingsById) {
        this.keepingsById = keepingsById;
    }

    @OneToMany(mappedBy = "vehiclesByVehiclesId")
    public Collection<OperationCostsEntity> getOperationCostsById() {
        return operationCostsById;
    }

    public void setOperationCostsById(Collection<OperationCostsEntity> operationCostsById) {
        this.operationCostsById = operationCostsById;
    }

    @OneToMany(mappedBy = "vehiclesByVehiclesId")
    public Collection<VehicleUnavailabilityEntity> getVehicleUnavailabilitiesById() {
        return vehicleUnavailabilitiesById;
    }

    public void setVehicleUnavailabilitiesById(Collection<VehicleUnavailabilityEntity> vehicleUnavailabilitiesById) {
        this.vehicleUnavailabilitiesById = vehicleUnavailabilitiesById;
    }

    @ManyToOne
    @JoinColumn(name = "brands_models_id", referencedColumnName = "id", nullable = false, insertable = false, updatable = false)
    public BrandModelEntity getBrandsModelsByBrandsModelsId() {
        return brandsModelsByBrandsModelsId;
    }

    public void setBrandsModelsByBrandsModelsId(BrandModelEntity brandsModelsByBrandsModelsId) {
        this.brandsModelsByBrandsModelsId = brandsModelsByBrandsModelsId;
    }

    @ManyToOne
    @JoinColumn(name = "types_id", referencedColumnName = "id", nullable = false, insertable = false, updatable = false)
    public TypeEntity getTypesByTypesId() {
        return typesByTypesId;
    }

    public void setTypesByTypesId(TypeEntity typesByTypesId) {
        this.typesByTypesId = typesByTypesId;
    }

    @ManyToOne
    @JoinColumn(name = "purposes_id", referencedColumnName = "id", nullable = false, insertable = false, updatable = false)
    public PurposeEntity getPurposesByPurposesId() {
        return purposesByPurposesId;
    }

    public void setPurposesByPurposesId(PurposeEntity purposesByPurposesId) {
        this.purposesByPurposesId = purposesByPurposesId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        VehicleEntity that = (VehicleEntity) o;
        return id == that.id && mileage == that.mileage && brandsModelsId == that.brandsModelsId && typesId == that.typesId && purposesId == that.purposesId && Objects.equals(vin, that.vin) && Objects.equals(equipmentlevel, that.equipmentlevel) && Objects.equals(avgfuelconsumption, that.avgfuelconsumption);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, vin, equipmentlevel, mileage, avgfuelconsumption, brandsModelsId, typesId, purposesId);
    }
}
