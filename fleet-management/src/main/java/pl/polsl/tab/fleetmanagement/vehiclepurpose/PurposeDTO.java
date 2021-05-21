package pl.polsl.tab.fleetmanagement.vehiclepurpose;

//@Getter
public class PurposeDTO {

    private long id;
    private String name;

    public PurposeDTO(long id, String name) {
        this.id = id;
        this.name = name;
    }

    public PurposeDTO(String name) {
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
