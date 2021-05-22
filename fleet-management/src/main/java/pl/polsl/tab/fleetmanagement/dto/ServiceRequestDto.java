package pl.polsl.tab.fleetmanagement.dto;

import lombok.Getter;
import lombok.Setter;

import java.sql.Date;
import java.util.Objects;

public class ServiceRequestDto {
    @Getter @Setter private Date date;
    @Getter @Setter private String description;
    @Getter @Setter private Long serviceTypesId;
    @Getter @Setter private Long vehiclesId;
    @Getter @Setter private Long peopleId;

    public ServiceRequestDto(Date date, String description, Long serviceTypesId, Long vehiclesId, Long peopleId) {
        this.date = date;
        this.description = description;
        this.serviceTypesId = serviceTypesId;
        this.vehiclesId = vehiclesId;
        this.peopleId = peopleId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ServiceRequestDto that = (ServiceRequestDto) o;
        return date.equals(that.date) && description.equals(that.description) && serviceTypesId.equals(that.serviceTypesId) && vehiclesId.equals(that.vehiclesId) && peopleId.equals(that.peopleId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(date, description, serviceTypesId, vehiclesId, peopleId);
    }

    @Override
    public String toString() {
        return "ServiceRequestDto{" +
                "date=" + date +
                ", description='" + description + '\'' +
                ", serviceTypesId=" + serviceTypesId +
                ", vehiclesId=" + vehiclesId +
                ", peopleId=" + peopleId +
                '}';
    }
}
