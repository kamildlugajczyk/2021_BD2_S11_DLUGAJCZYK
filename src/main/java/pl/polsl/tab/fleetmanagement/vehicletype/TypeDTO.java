package pl.polsl.tab.fleetmanagement.vehicletype;

import lombok.AllArgsConstructor;
import lombok.Getter;

//@Getter
public class TypeDTO {

    private long id;
    private String name;

    public TypeDTO(long id, String name) {
        this.id = id;
        this.name = name;
    }

    public TypeDTO(String name) {
        this.name = name;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }
}
