package pl.polsl.tab.fleetmanagement.vehicletype;

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

    public TypeDTO(TypeEntity typeEntity) {
        this.id = typeEntity.getId();
        this.name = typeEntity.getName();
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
