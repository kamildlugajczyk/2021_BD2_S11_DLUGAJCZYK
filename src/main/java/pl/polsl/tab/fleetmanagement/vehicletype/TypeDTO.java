package pl.polsl.tab.fleetmanagement.vehicletype;

import lombok.Getter;
import lombok.Setter;

public class TypeDTO {

    @Getter @Setter private long id;
    @Getter private String name;

    public TypeDTO(long id, String name) {
        this.id = id;
        this.name = name;
    }

    public TypeDTO(String name) {
        this.name = name;
    }

    public TypeDTO(TypeEntity typeEntity) {
        this.id = typeEntity.getId();
        this.name = typeEntity.getName();
    }
}
