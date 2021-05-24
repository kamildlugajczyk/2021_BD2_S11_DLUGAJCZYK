package pl.polsl.tab.fleetmanagement.servicing;

import com.fasterxml.jackson.annotation.*;
import lombok.Getter;
import lombok.Setter;
import pl.polsl.tab.fleetmanagement.servicerequest.ServiceRequestEntity;
import pl.polsl.tab.fleetmanagement.servicetype.ServiceTypeEntity;
import pl.polsl.tab.fleetmanagement.subcontractor.SubcontractorEntity;
import pl.polsl.tab.fleetmanagement.vehicleunavailability.VehicleUnavailabilityEntity;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Objects;

@Entity
@Table(name = "servicing", schema = "public", catalog = "testdb")
public class ServicingEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    @Getter private Long id;

    @Basic
    @Column(name = "price", nullable = false, precision = 2)
    @Getter @Setter private BigDecimal price;

    @Basic
    @Column(name = "subcontractors_id", nullable = false)
    @JsonIgnore
    @Getter @Setter private Long subcontractorsId;

    @Basic
    @Column(name = "isfinished", nullable = false, length = 1)
    @Getter @Setter private Boolean finished = false;

    @Basic
    @Column(name = "description", nullable = false, length = 100)
    @Getter @Setter private String description;

    @Basic
    @Column(name = "service_request_id")
    @JsonIgnore
    @Getter @Setter private Long serviceRequestId = null;

    @Basic
    @Column(name = "vehicle_unavailability_id", nullable = false)
    @JsonIgnore
    @Getter @Setter private Long vehicleUnavailabilityId;

    @Basic
    @Column(name = "service_types_id", nullable = false)
    @JsonIgnore
    @Getter @Setter private Long serviceTypesId;

    @ManyToOne
    @JoinColumn(name = "service_types_id", referencedColumnName = "id", nullable = false, insertable = false, updatable = false)
    @JsonIgnoreProperties("servicingEntity")
    @Getter @Setter private ServiceTypeEntity serviceTypes;

    @ManyToOne
    @JoinColumn(name = "subcontractors_id", referencedColumnName = "id", nullable = false, insertable = false, updatable = false)
    @JsonIgnoreProperties("servicingEntities")
    @Getter @Setter private SubcontractorEntity subcontractors;

    @ManyToOne
    @JoinColumn(name = "vehicle_unavailability_id", referencedColumnName = "id", nullable = false, insertable = false, updatable = false)
    @JsonIgnoreProperties("servicings")
    @Getter @Setter private VehicleUnavailabilityEntity vehicleUnavailability;

    @ManyToOne
    @JoinColumn(name = "service_request_id", referencedColumnName = "id", nullable = false, insertable = false, updatable = false)
    @JsonIgnoreProperties("servicings")
    @Getter @Setter private ServiceRequestEntity serviceRequest;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ServicingEntity that = (ServicingEntity) o;
        return id.equals(that.id) && price.equals(that.price) && subcontractorsId.equals(that.subcontractorsId) && finished.equals(that.finished) && description.equals(that.description) && Objects.equals(serviceRequestId, that.serviceRequestId) && vehicleUnavailabilityId.equals(that.vehicleUnavailabilityId) && serviceTypesId.equals(that.serviceTypesId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, price, subcontractorsId, finished, description, serviceRequestId, vehicleUnavailabilityId, serviceTypesId);
    }

    @Override
    public String toString() {
        return "ServicingEntity{" +
                "id=" + id +
                ", price=" + price +
                ", subcontractorsId=" + subcontractorsId +
                ", finished=" + finished +
                ", description='" + description + '\'' +
                ", serviceRequestId=" + serviceRequestId +
                ", vehicleUnavailabilityId=" + vehicleUnavailabilityId +
                ", serviceTypesId=" + serviceTypesId +
                ", serviceTypes=" + serviceTypes +
                ", subcontractors=" + subcontractors +
                ", vehicleUnavailability=" + vehicleUnavailability +
                ", serviceRequest=" + serviceRequest +
                '}';
    }
}
