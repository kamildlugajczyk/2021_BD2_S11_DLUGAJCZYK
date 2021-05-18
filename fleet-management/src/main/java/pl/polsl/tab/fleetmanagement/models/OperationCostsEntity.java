package pl.polsl.tab.fleetmanagement.models;

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

    @Id
    @Column(name = "id")
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    @Basic
    @Column(name = "date")
    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    @Basic
    @Column(name = "price")
    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    @Basic
    @Column(name = "description")
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Basic
    @Column(name = "vehicles_id")
    public int getVehiclesId() {
        return vehiclesId;
    }

    public void setVehiclesId(int vehiclesId) {
        this.vehiclesId = vehiclesId;
    }

    @Basic
    @Column(name = "operation_type_id")
    public int getOperationTypeId() {
        return operationTypeId;
    }

    public void setOperationTypeId(int operationTypeId) {
        this.operationTypeId = operationTypeId;
    }

    @Basic
    @Column(name = "vehicle_rentings_id")
    public int getVehicleRentingsId() {
        return vehicleRentingsId;
    }

    public void setVehicleRentingsId(int vehicleRentingsId) {
        this.vehicleRentingsId = vehicleRentingsId;
    }

    @Basic
    @Column(name = "keeping_id")
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
}
