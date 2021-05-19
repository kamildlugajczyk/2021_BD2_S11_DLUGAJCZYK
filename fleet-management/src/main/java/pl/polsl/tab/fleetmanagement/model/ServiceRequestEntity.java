package pl.polsl.tab.fleetmanagement.model;

import pl.polsl.tab.fleetmanagement.rentings.VehicleUnavailabilityEntity;

import javax.persistence.*;
import java.sql.Date;
import java.util.Collection;
import java.util.Objects;

@Entity
@Table(name = "service_request", schema = "public", catalog = "testdb")
public class ServiceRequestEntity {
    private long id;
    private Date date;
    private String description;
    private int serviceTypesId;
    private int vehicleUnavailabilityId;
    private ServiceTypesEntity serviceTypesByServiceTypesId;
    private VehicleUnavailabilityEntity vehicleUnavailabilityByVehicleUnavailabilityId;
    private Collection<ServicingEntity> servicingsById;

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
    @Column(name = "description", nullable = false, length = 100)
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Basic
    @Column(name = "service_types_id", nullable = false)
    public int getServiceTypesId() {
        return serviceTypesId;
    }

    public void setServiceTypesId(int serviceTypesId) {
        this.serviceTypesId = serviceTypesId;
    }

    @Basic
    @Column(name = "vehicle_unavailability_id", nullable = false)
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

    @ManyToOne
    @JoinColumn(name = "service_types_id", referencedColumnName = "id", nullable = false, insertable = false, updatable = false)
    public ServiceTypesEntity getServiceTypesByServiceTypesId() {
        return serviceTypesByServiceTypesId;
    }

    public void setServiceTypesByServiceTypesId(ServiceTypesEntity serviceTypesByServiceTypesId) {
        this.serviceTypesByServiceTypesId = serviceTypesByServiceTypesId;
    }

    @ManyToOne
    @JoinColumn(name = "vehicle_unavailability_id", referencedColumnName = "id", nullable = false, insertable = false, updatable = false)
    public VehicleUnavailabilityEntity getVehicleUnavailabilityByVehicleUnavailabilityId() {
        return vehicleUnavailabilityByVehicleUnavailabilityId;
    }

    public void setVehicleUnavailabilityByVehicleUnavailabilityId(VehicleUnavailabilityEntity vehicleUnavailabilityByVehicleUnavailabilityId) {
        this.vehicleUnavailabilityByVehicleUnavailabilityId = vehicleUnavailabilityByVehicleUnavailabilityId;
    }

    @OneToMany(mappedBy = "serviceRequestByServiceRequestId")
    public Collection<ServicingEntity> getServicingsById() {
        return servicingsById;
    }

    public void setServicingsById(Collection<ServicingEntity> servicingsById) {
        this.servicingsById = servicingsById;
    }
}
