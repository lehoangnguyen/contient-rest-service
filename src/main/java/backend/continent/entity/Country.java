package backend.continent.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.persistence.*;

@Data
@Entity
public class Country {

    @JsonIgnore
    private @Id
    @GeneratedValue
    Long id;
    private String name;
    private String flag;

    @JsonIgnore
    @ManyToOne
    @JoinColumn
    private Continent continent;

    public Country() {
    }

    public Country(String name, String flag, Continent continent) {
        this.name = name;
        this.flag = flag;
        this.continent = continent;
    }

    public String getFlag() {
        return flag;
    }

    public String getName() {
        return name;
    }

    public Continent getContinent() {
        return continent;
    }

    public void setContinent(Continent continent) {
        this.continent = continent;
    }

}
