package pl.polsl.tab.fleetmanagement.models;

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
    @Getter @Setter private Long serviceRequestId = null;

    @Basic
    @Column(name = "vehicle_unavailability_id", nullable = false)
    @Getter @Setter private Long vehicleUnavailabilityId;

    @Basic
    @Column(name = "service_types_id", nullable = false)
    @Getter @Setter private Long serviceTypesId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "vehicle_unavailability_id", referencedColumnName = "id", nullable = false, insertable = false, updatable = false)
    @Getter @Setter private VehicleUnavailabilityEntity vehicleUnavailabilityByVehicleUnavailabilityId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "service_types_id", referencedColumnName = "id", nullable = false, insertable = false, updatable = false)
    @Getter @Setter private ServiceTypesEntity serviceTypesByServiceTypesId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "subcontractors_id", referencedColumnName = "id", nullable = false, insertable = false, updatable = false)
    @Getter @Setter private SubcontractorsEntity subcontractorsBySubcontractorsId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "service_request_id", referencedColumnName = "id", nullable = false, insertable = false, updatable = false)
    @Getter @Setter private ServiceRequestEntity serviceRequestByServiceRequestId;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ServicingEntity that = (ServicingEntity) o;
        return id.equals(that.id) && price.equals(that.price) && subcontractorsId.equals(that.subcontractorsId) && startDate.equals(that.startDate) && endDate.equals(that.endDate) && finished.equals(that.finished) && description.equals(that.description) && Objects.equals(serviceRequestId, that.serviceRequestId) && vehicleUnavailabilityId.equals(that.vehicleUnavailabilityId) && serviceTypesId.equals(that.serviceTypesId) && vehicleUnavailabilityByVehicleUnavailabilityId.equals(that.vehicleUnavailabilityByVehicleUnavailabilityId) && serviceTypesByServiceTypesId.equals(that.serviceTypesByServiceTypesId) && subcontractorsBySubcontractorsId.equals(that.subcontractorsBySubcontractorsId) && serviceRequestByServiceRequestId.equals(that.serviceRequestByServiceRequestId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
