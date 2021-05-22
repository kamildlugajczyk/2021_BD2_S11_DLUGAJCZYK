package pl.polsl.tab.fleetmanagement.models;

import com.fasterxml.jackson.annotation.*;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.math.BigDecimal;
import java.sql.Date;
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
    @Column(name = "startdate", nullable = false)
    @Getter @Setter private Date startDate;

    @Basic
    @Column(name = "enddate", nullable = false)
    @Getter @Setter private Date endDate;

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
    @Getter @Setter private ServiceTypesEntity serviceTypes;

    @ManyToOne
    @JoinColumn(name = "subcontractors_id", referencedColumnName = "id", nullable = false, insertable = false, updatable = false)
    @JsonIgnoreProperties("servicingEntities")
    @Getter @Setter private SubcontractorsEntity subcontractors;

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
        return id.equals(that.id) && price.equals(that.price) && subcontractorsId.equals(that.subcontractorsId) && startDate.equals(that.startDate) && endDate.equals(that.endDate) && finished.equals(that.finished) && description.equals(that.description) && Objects.equals(serviceRequestId, that.serviceRequestId) && vehicleUnavailabilityId.equals(that.vehicleUnavailabilityId) && serviceTypesId.equals(that.serviceTypesId) && vehicleUnavailability.equals(that.vehicleUnavailability) && serviceTypes.equals(that.serviceTypes) && subcontractors.equals(that.subcontractors) && serviceRequest.equals(that.serviceRequest);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
