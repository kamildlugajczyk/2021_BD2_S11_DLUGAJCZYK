package pl.polsl.tab.fleetmanagement.model;

import pl.polsl.tab.fleetmanagement.vehicle.VehiclesEntity;

import javax.persistence.*;
import java.math.BigDecimal;
import java.sql.Date;
import java.util.Objects;

@Entity
@Table(name = "operation_costs", schema = "public", catalog = "testdb")
public class OperationCostsEntity {
    private long id;
    private Date date;
    private BigDecimal price;
    private String description;
    private int vehiclesId;
    private int operationTypeId;
    private int vehicleRentingsId;
    private int keepingId;
    private VehiclesEntity vehiclesByVehiclesId;
    private OperationTypeEntity operationTypeByOperationTypeId;
    private VehicleRentingsEntity vehicleRentingsByVehicleRentingsId;
    private KeepingEntity keepingByKeepingId;

    @Id
    @Column(name = "id", nullable = false)
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    @Basic
    @Column(name = "date", nullable = false)
    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    @Basic
    @Column(name = "price", nullable = false, precision = 2)
    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    @Basic
    @Column(name = "description", nullable = false, length = 100)
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Basic
    @Column(name = "vehicles_id", nullable = false)
    public int getVehiclesId() {
        return vehiclesId;
    }

    public void setVehiclesId(int vehiclesId) {
        this.vehiclesId = vehiclesId;
    }

    @Basic
    @Column(name = "operation_type_id", nullable = false)
    public int getOperationTypeId() {
        return operationTypeId;
    }

    public void setOperationTypeId(int operationTypeId) {
        this.operationTypeId = operationTypeId;
    }

    @Basic
    @Column(name = "vehicle_rentings_id", nullable = false)
    public int getVehicleRentingsId() {
        return vehicleRentingsId;
    }

    public void setVehicleRentingsId(int vehicleRentingsId) {
        this.vehicleRentingsId = vehicleRentingsId;
    }

    @Basic
    @Column(name = "keeping_id", nullable = false)
    public int getKeepingId() {
        return keepingId;
    }

    public void setKeepingId(int keepingId) {
        this.keepingId = keepingId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        OperationCostsEntity that = (OperationCostsEntity) o;
        return id == that.id && vehiclesId == that.vehiclesId && operationTypeId == that.operationTypeId && vehicleRentingsId == that.vehicleRentingsId && keepingId == that.keepingId && Objects.equals(date, that.date) && Objects.equals(price, that.price) && Objects.equals(description, that.description);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, date, price, description, vehiclesId, operationTypeId, vehicleRentingsId, keepingId);
    }

    @ManyToOne
    @JoinColumn(name = "vehicles_id", referencedColumnName = "id", nullable = false, insertable = false, updatable = false)
    public VehiclesEntity getVehiclesByVehiclesId() {
        return vehiclesByVehiclesId;
    }

    public void setVehiclesByVehiclesId(VehiclesEntity vehiclesByVehiclesId) {
        this.vehiclesByVehiclesId = vehiclesByVehiclesId;
    }

    @ManyToOne
    @JoinColumn(name = "operation_type_id", referencedColumnName = "id", nullable = false, insertable = false, updatable = false)
    public OperationTypeEntity getOperationTypeByOperationTypeId() {
        return operationTypeByOperationTypeId;
    }

    public void setOperationTypeByOperationTypeId(OperationTypeEntity operationTypeByOperationTypeId) {
        this.operationTypeByOperationTypeId = operationTypeByOperationTypeId;
    }

    @ManyToOne
    @JoinColumn(name = "vehicle_rentings_id", referencedColumnName = "id", nullable = false, insertable = false, updatable = false)
    public VehicleRentingsEntity getVehicleRentingsByVehicleRentingsId() {
        return vehicleRentingsByVehicleRentingsId;
    }

    public void setVehicleRentingsByVehicleRentingsId(VehicleRentingsEntity vehicleRentingsByVehicleRentingsId) {
        this.vehicleRentingsByVehicleRentingsId = vehicleRentingsByVehicleRentingsId;
    }

    @ManyToOne
    @JoinColumn(name = "keeping_id", referencedColumnName = "id", nullable = false, insertable = false, updatable = false)
    public KeepingEntity getKeepingByKeepingId() {
        return keepingByKeepingId;
    }

    public void setKeepingByKeepingId(KeepingEntity keepingByKeepingId) {
        this.keepingByKeepingId = keepingByKeepingId;
    }
}
