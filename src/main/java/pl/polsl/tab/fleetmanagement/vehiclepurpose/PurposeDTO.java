package pl.polsl.tab.fleetmanagement.vehiclepurpose;

import lombok.Getter;
import lombok.Setter;

public class PurposeDTO {

    @Getter @Setter private long id;
    @Getter private String name;

    public PurposeDTO() {
    }

    public PurposeDTO(long id, String name) {
        this.id = id;
        this.name = name;
    }

    public PurposeDTO(String name) {
        this.name = name;
    }

    public PurposeDTO(PurposeEntity purposeEntity) {
        this.id = purposeEntity.getId();
        this.name = purposeEntity.getName();
    }
}
