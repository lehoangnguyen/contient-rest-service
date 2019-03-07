package backend.continent.entity;

public enum ContinentMetricsEnum {
    CONTINENT_GET ("continent.get."), COUNTRY_GET ("country.get.");
    private String value;

    public String getValue() {
        return value;
    }

    ContinentMetricsEnum(String value) {
        this.value = value;
    }

}
