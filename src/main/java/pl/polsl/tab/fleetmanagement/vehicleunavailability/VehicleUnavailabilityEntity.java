package pl.polsl.tab.fleetmanagement.vehicleunavailability;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;
import pl.polsl.tab.fleetmanagement.person.PersonEntity;
import pl.polsl.tab.fleetmanagement.rentings.VehicleRentingEntity;
import pl.polsl.tab.fleetmanagement.servicing.ServicingEntity;
import pl.polsl.tab.fleetmanagement.vehicle.VehicleEntity;

import javax.persistence.*;
import java.util.Date;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "vehicle_unavailability", schema = "public", catalog = "testdb")
public class VehicleUnavailabilityEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    @Getter private Long id;

    @Basic
    @Column(name = "startdate", nullable = false)
    @Getter @Setter private Date startDate;

    @Basic
    @Column(name = "predictenddate")
    @Getter @Setter private Date predictEndDate = null;

    @Basic
    @Column(name = "enddate")
    @Getter @Setter private Date endDate = null;

    @Basic
    @Column(name = "vehicles_id", nullable = false)
    // TODO @JsonIgnore
    @Getter @Setter private Long vehiclesId;

    @Basic
    @Column(name = "people_id", nullable = false)
    // TODO @JsonIgnore
    @Getter @Setter private Long peopleId;

    @OneToMany(mappedBy = "vehicleUnavailability")
    @JsonIgnoreProperties("vehicleUnavailability")
    @Getter @Setter private Set<ServicingEntity> servicings;

    @OneToMany(mappedBy = "vehicleUnavailabilityByVehicleUnavailabilityId")
    @JsonIgnore
    @Getter @Setter private Set<VehicleRentingEntity> vehicleRentingsById;

    @ManyToOne
    @JoinColumn(name = "vehicles_id", referencedColumnName = "id", nullable = false, insertable = false, updatable = false)
    @JsonIgnore
    @Getter @Setter private VehicleEntity vehiclesByVehiclesId;

    @ManyToOne
    @JoinColumn(name = "people_id", referencedColumnName = "id", nullable = false, insertable = false, updatable = false)
    @JsonIgnore
    @Getter @Setter private PersonEntity peopleByPeopleId;

    public VehicleUnavailabilityEntity(Date startDate, Date predictEndDate, Date endDate, Long vehiclesId, Long peopleId) {
        this.startDate = startDate;
        this.predictEndDate = predictEndDate;
        this.endDate = endDate;
        this.vehiclesId = vehiclesId;
        this.peopleId = peopleId;
    }

    public VehicleUnavailabilityEntity() {
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        VehicleUnavailabilityEntity that = (VehicleUnavailabilityEntity) o;
        return id.equals(that.id) && startDate.equals(that.startDate) && predictEndDate.equals(that.predictEndDate) && Objects.equals(endDate, that.endDate) && vehiclesId.equals(that.vehiclesId) && peopleId.equals(that.peopleId) && Objects.equals(servicings, that.servicings) && Objects.equals(vehicleRentingsById, that.vehicleRentingsById) && Objects.equals(vehiclesByVehiclesId, that.vehiclesByVehiclesId) && Objects.equals(peopleByPeopleId, that.peopleByPeopleId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

//    @Override
//    public String toString() {
//        return "VehicleUnavailabilityEntity{" +
//                "id=" + id +
//                ", startDate=" + startDate +
//                ", predictEndDate=" + predictEndDate +
//                ", endDate=" + endDate +
//                ", vehiclesId=" + vehiclesId +
//                ", peopleId=" + peopleId +
//                ", servicings=" + servicings +
//                ", vehicleRentingsById=" + vehicleRentingsById +
//                ", vehiclesByVehiclesId=" + vehiclesByVehiclesId +
//                ", peopleByPeopleId=" + peopleByPeopleId +
//                '}';
//    }
}
