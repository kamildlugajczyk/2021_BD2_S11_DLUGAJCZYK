package pl.polsl.tab.fleetmanagement.function;


import lombok.Getter;
import lombok.Setter;

public class FunctionDTO {

    @Getter @Setter private long id;
    @Getter private String name;

    public FunctionDTO() {
    }

    public FunctionDTO(String name) {
        this.name = name;
    }

    public FunctionDTO(long id, String name) {
        this.id = id;
        this.name = name;
    }

    public FunctionDTO(FunctionEntity functionEntity) {
        this.id = functionEntity.getId();
        this.name = functionEntity.getName();
    }
}
