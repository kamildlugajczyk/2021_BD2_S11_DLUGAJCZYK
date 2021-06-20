package pl.polsl.tab.fleetmanagement.servicerequest;

import lombok.Getter;
import lombok.Setter;

import java.util.Objects;

public class ServiceRequestDto {
    @Getter @Setter private String description;
    @Getter @Setter private Long serviceTypesId;
    @Getter @Setter private Long vehiclesId;

    public ServiceRequestDto(String description, Long serviceTypesId, Long vehiclesId) {
        this.description = description;
        this.serviceTypesId = serviceTypesId;
        this.vehiclesId = vehiclesId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ServiceRequestDto that = (ServiceRequestDto) o;
        return description.equals(that.description) && serviceTypesId.equals(that.serviceTypesId) && vehiclesId.equals(that.vehiclesId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(description, serviceTypesId, vehiclesId);
    }

    @Override
    public String toString() {
        return "ServiceRequestDto{" +
                ", description='" + description + '\'' +
                ", serviceTypesId=" + serviceTypesId +
                ", vehiclesId=" + vehiclesId +
                '}';
    }
}
