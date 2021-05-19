package pl.polsl.tab.fleetmanagement.models;

import javax.persistence.*;
import java.math.BigDecimal;
import java.sql.Date;
import java.util.Objects;

@Entity
@Table(name = "servicing", schema = "public", catalog = "testdb")
public class ServicingEntity {
    private long id;
    private BigDecimal price;
    private int subcontractorsId;
    private Date startdate;
    private Date enddate;
    private String isfinished;
    private String description;
    private int serviceRequestId;
    private SubcontractorsEntity subcontractorsBySubcontractorsId;
    private ServiceRequestEntity serviceRequestByServiceRequestId;

    @Id
    @Column(name = "id", nullable = false)
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    @Basic
    @Column(name = "price", nullable = false, precision = 2)
    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    @Basic
    @Column(name = "subcontractors_id", nullable = false)
    public int getSubcontractorsId() {
        return subcontractorsId;
    }

    public void setSubcontractorsId(int subcontractorsId) {
        this.subcontractorsId = subcontractorsId;
    }

    @Basic
    @Column(name = "startdate", nullable = false)
    public Date getStartdate() {
        return startdate;
    }

    public void setStartdate(Date startdate) {
        this.startdate = startdate;
    }

    @Basic
    @Column(name = "enddate", nullable = false)
    public Date getEnddate() {
        return enddate;
    }

    public void setEnddate(Date enddate) {
        this.enddate = enddate;
    }

    @Basic
    @Column(name = "isfinished", nullable = false, length = 1)
    public String getIsfinished() {
        return isfinished;
    }

    public void setIsfinished(String isfinished) {
        this.isfinished = isfinished;
    }

    @Basic
    @Column(name = "description", nullable = false, length = 100)
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Basic
    @Column(name = "service_request_id", nullable = false)
    public int getServiceRequestId() {
        return serviceRequestId;
    }

    public void setServiceRequestId(int serviceRequestId) {
        this.serviceRequestId = serviceRequestId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ServicingEntity that = (ServicingEntity) o;
        return id == that.id && subcontractorsId == that.subcontractorsId && serviceRequestId == that.serviceRequestId && Objects.equals(price, that.price) && Objects.equals(startdate, that.startdate) && Objects.equals(enddate, that.enddate) && Objects.equals(isfinished, that.isfinished) && Objects.equals(description, that.description);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, price, subcontractorsId, startdate, enddate, isfinished, description, serviceRequestId);
    }

    @ManyToOne
    @JoinColumn(name = "subcontractors_id", referencedColumnName = "id", nullable = false, insertable = false, updatable = false)
    public SubcontractorsEntity getSubcontractorsBySubcontractorsId() {
        return subcontractorsBySubcontractorsId;
    }

    public void setSubcontractorsBySubcontractorsId(SubcontractorsEntity subcontractorsBySubcontractorsId) {
        this.subcontractorsBySubcontractorsId = subcontractorsBySubcontractorsId;
    }

    @ManyToOne
    @JoinColumn(name = "service_request_id", referencedColumnName = "id", nullable = false, insertable = false, updatable = false)
    public ServiceRequestEntity getServiceRequestByServiceRequestId() {
        return serviceRequestByServiceRequestId;
    }

    public void setServiceRequestByServiceRequestId(ServiceRequestEntity serviceRequestByServiceRequestId) {
        this.serviceRequestByServiceRequestId = serviceRequestByServiceRequestId;
    }
}
