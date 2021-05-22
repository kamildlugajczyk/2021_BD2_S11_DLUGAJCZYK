package pl.polsl.tab.fleetmanagement.dto;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.sql.Date;
import java.util.Objects;

public class ServicingDto {
    @Getter @Setter private BigDecimal price;
    @Getter @Setter private String description;
    @Getter @Setter private Date startDate;
    @Getter @Setter private Date predictEndDate;
    @Getter @Setter private Long subcontractorsId;
    @Getter @Setter private Long serviceTypesId;

    public ServicingDto(BigDecimal price, String description, Date startDate, Date predictEndDate, Long subcontractorsId, Long serviceTypesId) {
        this.price = price;
        this.description = description;
        this.startDate = startDate;
        this.predictEndDate = predictEndDate;
        this.subcontractorsId = subcontractorsId;
        this.serviceTypesId = serviceTypesId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ServicingDto that = (ServicingDto) o;
        return price.equals(that.price) && description.equals(that.description) && startDate.equals(that.startDate) && predictEndDate.equals(that.predictEndDate) && subcontractorsId.equals(that.subcontractorsId) && serviceTypesId.equals(that.serviceTypesId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(price, description, startDate, predictEndDate, subcontractorsId, serviceTypesId);
    }

    @Override
    public String toString() {
        return "ServicingDto{" +
                "price=" + price +
                ", description='" + description + '\'' +
                ", startDate=" + startDate +
                ", predictEndDate=" + predictEndDate +
                ", subcontractorsId=" + subcontractorsId +
                ", serviceTypesId=" + serviceTypesId +
                '}';
    }
}