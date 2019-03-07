package backend.continent.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Data
@Entity
public class Continent {

    @JsonIgnore
    private @Id
    @GeneratedValue
    Long id;

    private String continent;

    @OneToMany(mappedBy = "continent", cascade = {CascadeType.ALL})
    private List<Country> countries;

    public Continent() {
    }

    public Continent(String continent) {
        this.continent = continent;
    }

    public String getContinent() {
        return continent;
    }

    public List<Country> getCountries() {
        return countries;
    }

    public void setCountries(List<Country> countries) {
        this.countries = countries;
    }

}
