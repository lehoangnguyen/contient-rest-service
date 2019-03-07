package backend.continent.controller;

import backend.continent.repositories.ContinentRepository;
import backend.continent.entity.ContinentMetricsEnum;
import backend.continent.entity.Continent;
import backend.continent.entity.Country;
import backend.continent.exception.ContinentNotFoundException;
import backend.continent.exception.CountryNotFoundException;
import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@Slf4j
public class ContinentsController {
    private final ContinentRepository repository;
    private Map<String, Counter> counterMap;

    @Autowired
    private MeterRegistry registry;

    public ContinentsController(ContinentRepository repository) {
        this.repository = repository;
        this.counterMap = new HashMap<>();
    }

    @RequestMapping("/continents")
    public List<Continent> getContinents() {
        return repository.findAll();
    }

    @GetMapping("/continents/{continentName}")
    public List<Country> getCountries(@PathVariable String continentName) {
        captureMetrics(ContinentMetricsEnum.CONTINENT_GET.getValue() + continentName);
        List<Country> countries = repository.findCountriesByContinentName(continentName);
        if (countries.size() == 0) {
            log.error("No countries found for continent '{}'", continentName);
            throw new ContinentNotFoundException(continentName);
        }
        return countries;
    }

    @GetMapping("/country/{countryName}")
    public Country getFlag(@PathVariable String countryName) {
        captureMetrics(ContinentMetricsEnum.COUNTRY_GET.getValue() + countryName);
        Country result = repository.findCountryByName(countryName);
        if (result == null) {
            log.error("No country flag information found for the country name '{}'", countryName);
            throw new CountryNotFoundException(countryName);
        }
        return result;
    }

    private void captureMetrics(String metricName) {
        if (counterMap.containsKey(metricName)) {
            counterMap.get(metricName).increment();
        } else {
            Counter counter = registry.counter(metricName);
            counter.increment();
            counterMap.put(metricName, counter);
        }
    }
}
