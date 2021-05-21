package pl.polsl.tab.fleetmanagement.function;

//@Getter
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

    public FunctionDTO(FunctionsEntity functionsEntity) {
        this.id = functionsEntity.getId();
        this.name = functionsEntity.getName();
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
