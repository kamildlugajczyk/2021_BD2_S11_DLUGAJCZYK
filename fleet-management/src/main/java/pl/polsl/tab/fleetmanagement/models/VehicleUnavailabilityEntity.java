package pl.polsl.tab.fleetmanagement.models;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.sql.Date;
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
    @Column(name = "enddate", nullable = false)
    @Getter @Setter private Date endDate;

    @Basic
    @Column(name = "business", nullable = false, length = 1)
    @Getter @Setter private String business;

    @Basic
    @Column(name = "vehicles_id", nullable = false)
    @Getter @Setter private Long vehiclesId;

    @Basic
    @Column(name = "people_id", nullable = false)
    @Getter @Setter private Long peopleId;

    @OneToMany(mappedBy = "vehicleUnavailabilityByVehicleUnavailabilityId")
    @Getter @Setter private Set<ServicingEntity> servicingById;

    @OneToMany(mappedBy = "vehicleUnavailabilityByVehicleUnavailabilityId")
    @Getter @Setter private Set<VehicleRentingsEntity> vehicleRentingsById;

    @ManyToOne
    @JoinColumn(name = "vehicles_id", referencedColumnName = "id", nullable = false, insertable = false, updatable = false)
    @Getter @Setter private VehiclesEntity vehiclesByVehiclesId;

    @ManyToOne
    @JoinColumn(name = "people_id", referencedColumnName = "id", nullable = false, insertable = false, updatable = false)
    @Getter @Setter private PeopleEntity peopleByPeopleId;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        VehicleUnavailabilityEntity that = (VehicleUnavailabilityEntity) o;
        return id == that.id && vehiclesId == that.vehiclesId && peopleId == that.peopleId && Objects.equals(startDate, that.startDate) && Objects.equals(endDate, that.endDate) && Objects.equals(business, that.business);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, startDate, endDate, business, vehiclesId, peopleId);
    }
}
