package pl.polsl.tab.fleetmanagement.people;

import javax.persistence.*;
import java.util.Collection;
import java.util.Objects;

@Entity
@Table(name = "functions", schema = "public", catalog = "testdb")
public class FunctionsEntity {
    private long id;
    private String name;
    private Collection<PeopleEntity> peopleById;

    @Id
    @Column(name = "id", nullable = false)
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

    @OneToMany(mappedBy = "functionsByFunctionsId")
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
