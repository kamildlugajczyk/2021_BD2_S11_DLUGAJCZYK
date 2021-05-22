package pl.polsl.tab.fleetmanagement.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.sql.Date;
import java.util.Objects;

@NoArgsConstructor
public class VehicleUnavailabilityDto {
    @Getter @Setter private Date startDate;
    @Getter @Setter private Date endDate;
    @Getter @Setter private String business;
    @Getter @Setter private Long vehiclesId;
    @Getter @Setter private Long peopleId;

    public VehicleUnavailabilityDto(Date startDate, Date endDate, String business, Long vehiclesId, Long peopleId) {
        this.startDate = startDate;
        this.endDate = endDate;
        this.business = business;
        this.vehiclesId = vehiclesId;
        this.peopleId = peopleId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        VehicleUnavailabilityDto that = (VehicleUnavailabilityDto) o;
        return startDate.equals(that.startDate) && endDate.equals(that.endDate) && business.equals(that.business) && vehiclesId.equals(that.vehiclesId) && peopleId.equals(that.peopleId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(startDate, endDate, business, vehiclesId, peopleId);
    }

    @Override
    public String toString() {
        return "VehicleUnavailabilityDto{" +
                "startDate=" + startDate +
                ", endDate=" + endDate +
                ", business='" + business + '\'' +
                ", vehiclesId=" + vehiclesId +
                ", peopleId=" + peopleId +
                '}';
    }
}
