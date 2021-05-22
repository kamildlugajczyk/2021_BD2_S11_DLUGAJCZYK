package pl.polsl.tab.fleetmanagement.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.sql.Date;
import java.util.Objects;

@NoArgsConstructor
public class VehicleUnavailabilityDto {
    @Getter @Setter private Date startDate;
    @Getter @Setter private Date predictEndDate;
    @Getter @Setter private Long vehiclesId;
    @Getter @Setter private Long peopleId;

    public VehicleUnavailabilityDto(Date startDate, Date predictEndDate, Long vehiclesId, Long peopleId) {
        this.startDate = startDate;
        this.predictEndDate = predictEndDate;
        this.vehiclesId = vehiclesId;
        this.peopleId = peopleId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        VehicleUnavailabilityDto that = (VehicleUnavailabilityDto) o;
        return startDate.equals(that.startDate) && predictEndDate.equals(that.predictEndDate) && vehiclesId.equals(that.vehiclesId) && peopleId.equals(that.peopleId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(startDate, predictEndDate, vehiclesId, peopleId);
    }

    @Override
    public String toString() {
        return "VehicleUnavailabilityDto{" +
                "startDate=" + startDate +
                ", predictEndDate=" + predictEndDate +
                ", vehiclesId=" + vehiclesId +
                ", peopleId=" + peopleId +
                '}';
    }
}
