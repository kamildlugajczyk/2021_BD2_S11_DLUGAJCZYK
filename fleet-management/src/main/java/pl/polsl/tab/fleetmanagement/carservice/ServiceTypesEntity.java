package pl.polsl.tab.fleetmanagement.carservice;

import javax.persistence.*;
import java.util.Collection;
import java.util.Objects;

@Entity
@Table(name = "service_types", schema = "public", catalog = "testdb")
public class ServiceTypesEntity {
    private long id;
    private String name;
    private Collection<ServiceRequestEntity> serviceRequestsById;

    @Id
    @Column(name = "id", nullable = false)
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    @Basic
    @Column(name = "name", nullable = false, length = 50)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ServiceTypesEntity that = (ServiceTypesEntity) o;
        return id == that.id && Objects.equals(name, that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name);
    }

    @OneToMany(mappedBy = "serviceTypesByServiceTypesId")
    public Collection<ServiceRequestEntity> getServiceRequestsById() {
        return serviceRequestsById;
    }

    public void setServiceRequestsById(Collection<ServiceRequestEntity> serviceRequestsById) {
        this.serviceRequestsById = serviceRequestsById;
    }
}
