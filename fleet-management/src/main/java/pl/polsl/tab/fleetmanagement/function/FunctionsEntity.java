package pl.polsl.tab.fleetmanagement.function;

import pl.polsl.tab.fleetmanagement.people.PeopleEntity;

import javax.persistence.*;
import java.util.Collection;
import java.util.Objects;

@Entity
@Table(name = "functions", schema = "public", catalog = "testdb")
public class FunctionsEntity {
    private long id;
    private String name;
    private Collection<PeopleEntity> peopleById;

    public FunctionsEntity() {
    }

    public FunctionsEntity(String name) {
        this.name = name;
    }

    public FunctionsEntity(long id, String name) {
        this.id = id;
        this.name = name;
    }

    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    @Basic
    @Column(name = "name", nullable = false, length = 50)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @OneToMany(mappedBy = "functionsByFunctionsId", fetch = FetchType.LAZY)
    public Collection<PeopleEntity> getPeopleById() {
        return peopleById;
    }

    public void setPeopleById(Collection<PeopleEntity> peopleById) {
        this.peopleById = peopleById;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        FunctionsEntity that = (FunctionsEntity) o;
        return id == that.id && Objects.equals(name, that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name);
    }
}
