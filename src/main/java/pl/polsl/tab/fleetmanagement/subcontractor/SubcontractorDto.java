package pl.polsl.tab.fleetmanagement.subcontractor;

import lombok.Getter;
import lombok.Setter;

import java.util.Objects;

public class SubcontractorDto {

    @Getter @Setter private String name;
    @Getter @Setter private String address;
    @Getter @Setter private String phoneNumber;

    public SubcontractorDto(String name, String address, String phoneNumber) {
        this.name = name;
        this.address = address;
        this.phoneNumber = phoneNumber;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SubcontractorDto that = (SubcontractorDto) o;
        return name.equals(that.name) && address.equals(that.address) && phoneNumber.equals(that.phoneNumber);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, address, phoneNumber);
    }

    @Override
    public String toString() {
        return "SubcontractorsDto{" +
                "name='" + name + '\'' +
                ", address='" + address + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                '}';
    }
}
