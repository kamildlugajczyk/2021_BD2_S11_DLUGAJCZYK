package pl.polsl.tab.fleetmanagement.function;


public class FunctionDTO {

    private long id;
    private String name;

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
