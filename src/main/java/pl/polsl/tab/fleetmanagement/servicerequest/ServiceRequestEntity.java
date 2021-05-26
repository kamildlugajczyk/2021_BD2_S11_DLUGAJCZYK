package pl.polsl.tab.fleetmanagement.servicerequest;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import pl.polsl.tab.fleetmanagement.models.VehiclesEntity;
import pl.polsl.tab.fleetmanagement.people.PersonEntity;
import pl.polsl.tab.fleetmanagement.servicing.ServicingEntity;

import javax.persistence.*;
import java.sql.Date;
import java.util.Objects;
import java.util.Set;

@Entity
@NoArgsConstructor
@Table(name = "service_request", schema = "public", catalog = "testdb")
public class ServiceRequestEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    @Getter private Long id;

    @Basic
    @Column(name = "date", nullable = false)
    @Getter @Setter private Date date = new Date(System.currentTimeMillis());

    @Basic
    @Column(name = "description", nullable = false, length = 100)
    @Getter @Setter private String description;

    @Basic
    @Column(name = "service_types_id", nullable = false)
    @JsonIgnore
    @Getter @Setter private Long serviceTypesId;

    @Basic
    @Column(name = "vehicles_id", nullable = false)
    //TODO @JsonIgnore
    @Getter @Setter private Long vehiclesId;

    @Basic
    @Column(name = "people_id", nullable = false)
    //TODO @JsonIgnore
    @Getter @Setter private Long peopleId;

    @Basic
    @Column(name = "processed", nullable = false)
    @Getter @Setter private Boolean processed = false;

    @OneToMany(mappedBy = "serviceRequest")
    @JsonIgnoreProperties("serviceRequest")
    @Getter @Setter private Set<ServicingEntity> servicings;

    @OneToOne()
    @JoinColumn(name = "vehicles_id", referencedColumnName = "id", nullable = false, insertable = false, updatable = false)
    @JsonIgnore
    @Getter @Setter private VehiclesEntity vehiclesByVehiclesId;

    @OneToOne()
    @JoinColumn(name = "people_Id", referencedColumnName = "id", nullable = false, insertable = false, updatable = false)
    @JsonIgnore
    @Getter @Setter private PersonEntity peopleByPeopleId;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ServiceRequestEntity that = (ServiceRequestEntity) o;
        return id.equals(that.id) && date.equals(that.date) && description.equals(that.description) && Objects.equals(serviceTypesId, that.serviceTypesId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "ServiceRequestEntity{" +
                "id=" + id +
                ", date=" + date +
                ", description='" + description + '\'' +
                ", serviceTypesId=" + serviceTypesId +
                ", vehiclesId=" + vehiclesId +
                ", peopleId=" + peopleId +
                ", processed=" + processed +
                ", servicingById=" + servicings +
                '}';
    }
}
