package pl.polsl.tab.fleetmanagement.function;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import pl.polsl.tab.fleetmanagement.people.PersonEntity;

import javax.persistence.*;
import java.util.Collection;
import java.util.Objects;

@Entity
@NoArgsConstructor
@Table(name = "functions", schema = "public", catalog = "testdb")
public class FunctionEntity {

    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Getter private Long id;

    @Basic
    @Column(name = "name", nullable = false, length = 50)
    @Getter @Setter private String name;

    @OneToMany(mappedBy = "functionsByFunctionsId")
    @Getter @Setter  private Collection<PersonEntity> peopleById;

    public FunctionEntity(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        FunctionEntity that = (FunctionEntity) o;
        return id.equals(that.id) && Objects.equals(name, that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name);
    }
}
