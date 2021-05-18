package pl.polsl.tab.fleetmanagement.models;

import javax.persistence.*;
import java.sql.Date;
import java.util.Objects;

@Entity
@Table(name = "service_request", schema = "public", catalog = "testdb")
public class ServiceRequestEntity {
    private long id;
    private Date date;
    private String description;
    private int serviceTypesId;
    private int vehicleUnavailabilityId;

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
    @Column(name = "description")
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Basic
    @Column(name = "service_types_id")
    public int getServiceTypesId() {
        return serviceTypesId;
    }

    public void setServiceTypesId(int serviceTypesId) {
        this.serviceTypesId = serviceTypesId;
    }

    @Basic
    @Column(name = "vehicle_unavailability_id")
    public int getVehicleUnavailabilityId() {
        return vehicleUnavailabilityId;
    }

    public void setVehicleUnavailabilityId(int vehicleUnavailabilityId) {
        this.vehicleUnavailabilityId = vehicleUnavailabilityId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ServiceRequestEntity that = (ServiceRequestEntity) o;
        return id == that.id && serviceTypesId == that.serviceTypesId && vehicleUnavailabilityId == that.vehicleUnavailabilityId && Objects.equals(date, that.date) && Objects.equals(description, that.description);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, date, description, serviceTypesId, vehicleUnavailabilityId);
    }
}
