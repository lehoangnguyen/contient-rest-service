package backend.continent.repositories;

import backend.continent.entity.Continent;
import backend.continent.entity.Country;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import java.util.List;


public interface ContinentRepository extends JpaRepository<Continent, Long> {
    @Query("select c.countries from Continent c where c.continent = ?1")
    public List<Country> findCountriesByContinentName(String continent);

    @Query("from Country c where c.name = ?1")
    public Country findCountryByName(String name);
}
