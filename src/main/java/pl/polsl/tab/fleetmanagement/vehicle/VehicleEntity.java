package pl.polsl.tab.fleetmanagement.vehicle;

import com.fasterxml.jackson.annotation.JsonIgnore;
import pl.polsl.tab.fleetmanagement.exploitation.OperationCostEntity;
import pl.polsl.tab.fleetmanagement.keeping.KeepingEntity;
import pl.polsl.tab.fleetmanagement.vehicle.brandmodel.BrandModelEntity;
import pl.polsl.tab.fleetmanagement.vehicle.purpose.PurposeEntity;
import pl.polsl.tab.fleetmanagement.vehicle.type.TypeEntity;
import pl.polsl.tab.fleetmanagement.vehicleunavailability.VehicleUnavailabilityEntity;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Collection;
import java.util.Objects;

@Entity
@Table(name = "vehicles", schema = "public", catalog = "testdb")
public class VehicleEntity {
    private long id;
    private String vin;
    private String plates;
    private String equipmentLevel;
    private int mileage;
    private BigDecimal avgFuelConsumption;
    private long brandsModelsId;
    private long typesId;
    private long purposesId;
    private Collection<KeepingEntity> keepingsById;
    private Collection<OperationCostEntity> operationCostsById;
    private Collection<VehicleUnavailabilityEntity> vehicleUnavailabilitiesById;
    private TypeEntity typesByTypesId;
    private PurposeEntity purposesByPurposesId;
    private BrandModelEntity brandsModelsByBrandsModelsId;

    public VehicleEntity() {
    }

    public VehicleEntity(String vin, String plates, String equipmentLevel, int mileage, BigDecimal avgFuelConsumption,
                         BrandModelEntity brandModelEntity, TypeEntity typeEntity, PurposeEntity purposeEntity) {
        this.vin = vin;
        this.plates = plates;
        this.equipmentLevel = equipmentLevel;
        this.mileage = mileage;
        this.avgFuelConsumption = avgFuelConsumption;
        this.brandsModelsId = brandModelEntity.getId();
        this.brandsModelsByBrandsModelsId = brandModelEntity;
        this.typesId = typeEntity.getId();
        this.typesByTypesId = typeEntity;
        this.purposesId = purposeEntity.getId();
        this.purposesByPurposesId = purposeEntity;
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
    @Column(name = "vin", nullable = false, length = 50)
    public String getVin() {
        return vin;
    }

    public void setVin(String vin) {
        this.vin = vin;
    }

    @Basic
    @Column(name = "plates", nullable = false, length = 50)
    public String getPlates() {
        return plates;
    }

    public void setPlates(String plates) {
        this.plates = plates;
    }

    @Basic
    @Column(name = "equipmentlevel", nullable = false, length = 8)
    public String getEquipmentLevel() {
        return equipmentLevel;
    }

    public void setEquipmentLevel(String equipmentlevel) {
        this.equipmentLevel = equipmentlevel;
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
    public BigDecimal getAvgFuelConsumption() {
        return avgFuelConsumption;
    }

    public void setAvgFuelConsumption(BigDecimal avgfuelconsumption) {
        this.avgFuelConsumption = avgfuelconsumption;
    }

    @Basic
    @Column(name = "brands_models_id", nullable = false)
    public long getBrandsModelsId() {
        return brandsModelsId;
    }

    public void setBrandsModelsId(long brandsModelsId) {
        this.brandsModelsId = brandsModelsId;
    }

    @Basic
    @Column(name = "types_id", nullable = false)
    public long getTypesId() {
        return typesId;
    }

    public void setTypesId(long typesId) {
        this.typesId = typesId;
    }

    @Basic
    @Column(name = "purposes_id", nullable = false)
    public long getPurposesId() {
        return purposesId;
    }

    public void setPurposesId(long purposesId) {
        this.purposesId = purposesId;
    }

    @OneToMany(mappedBy = "vehiclesByVehiclesId"/*, cascade = CascadeType.ALL*/)
    public Collection<KeepingEntity> getKeepingsById() {
        return keepingsById;
    }

    public void setKeepingsById(Collection<KeepingEntity> keepingsById) {
        this.keepingsById = keepingsById;
    }

    @OneToMany(mappedBy = "vehiclesByVehiclesId")
    public Collection<OperationCostEntity> getOperationCostsById() {
        return operationCostsById;
    }

    public void setOperationCostsById(Collection<OperationCostEntity> operationCostsById) {
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
    @JsonIgnore
    @JoinColumn(name = "brands_models_id", referencedColumnName = "id", nullable = false, insertable = false, updatable = false)
    public BrandModelEntity getBrandsModelsByBrandsModelsId() {
        return brandsModelsByBrandsModelsId;
    }

    public void setBrandsModelsByBrandsModelsId(BrandModelEntity brandsModelsByBrandsModelsId) {
        this.brandsModelsByBrandsModelsId = brandsModelsByBrandsModelsId;
    }

    @ManyToOne
    @JsonIgnore
    @JoinColumn(name = "types_id", referencedColumnName = "id", nullable = false, insertable = false, updatable = false)
    public TypeEntity getTypesByTypesId() {
        return typesByTypesId;
    }

    public void setTypesByTypesId(TypeEntity typesByTypesId) {
        this.typesByTypesId = typesByTypesId;
    }

    @ManyToOne
    @JsonIgnore
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
        return id == that.id && mileage == that.mileage && brandsModelsId == that.brandsModelsId &&
                typesId == that.typesId && purposesId == that.purposesId && Objects.equals(vin, that.vin) &&
                Objects.equals(plates, that.plates) && Objects.equals(equipmentLevel, that.equipmentLevel) &&
                Objects.equals(avgFuelConsumption, that.avgFuelConsumption);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, vin, plates, equipmentLevel, mileage, avgFuelConsumption, brandsModelsId, typesId, purposesId);
    }

    @Override
    public String toString() {
        return "VehicleEntity{" +
                "id=" + id +
                ", vin='" + vin + '\'' +
                ", plates='" + plates + '\'' +
                ", equipmentLevel='" + equipmentLevel + '\'' +
                ", mileage=" + mileage +
                ", avgFuelConsumption=" + avgFuelConsumption +
                ", brandsModelsId=" + brandsModelsId +
                ", typesId=" + typesId +
                ", purposesId=" + purposesId +
                ", keepingsById=" + keepingsById +
                ", operationCostsById=" + operationCostsById +
                ", vehicleUnavailabilitiesById=" + vehicleUnavailabilitiesById +
                ", typesByTypesId=" + typesByTypesId +
                ", purposesByPurposesId=" + purposesByPurposesId +
                ", brandsModelsByBrandsModelsId=" + brandsModelsByBrandsModelsId +
                '}';
    }
}
