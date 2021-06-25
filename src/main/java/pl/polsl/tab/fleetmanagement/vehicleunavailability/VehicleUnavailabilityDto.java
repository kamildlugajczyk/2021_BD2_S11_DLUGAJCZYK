package pl.polsl.tab.fleetmanagement.vehicleunavailability;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Basic;
import javax.persistence.Column;
import java.util.Date;
import java.util.Objects;

@NoArgsConstructor
public class VehicleUnavailabilityDto {
    @Getter @Setter private Date startDate;
    @Getter @Setter private Date endDate  = null;
    @Getter @Setter private Long vehiclesId;
    @Getter @Setter private Long peopleId;
    @Getter @Setter private Date predictEndDate;
    @Getter private Long id;

    public VehicleUnavailabilityDto(Date startDate, Date predictEndDate, Date endDate, Long vehiclesId, Long peopleId, Long id) {
        this.startDate = startDate;
        this.predictEndDate = predictEndDate;
        this.endDate = endDate;
        this.vehiclesId = vehiclesId;
        this.peopleId = peopleId;
        this.id = id;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        VehicleUnavailabilityDto that = (VehicleUnavailabilityDto) o;
        return startDate.equals(that.startDate) && endDate.equals(that.endDate) && vehiclesId.equals(that.vehiclesId) && peopleId.equals(that.peopleId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(startDate, endDate, vehiclesId, peopleId);
    }

    @Override
    public String toString() {
        return "VehicleUnavailabilityDto{" +
                "startDate=" + startDate +
                ", endDate=" + endDate +
                ", vehiclesId=" + vehiclesId +
                ", peopleId=" + peopleId +
                '}';
    }
}
