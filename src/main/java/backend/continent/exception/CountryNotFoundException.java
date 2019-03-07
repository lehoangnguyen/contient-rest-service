package backend.continent.exception;

public class CountryNotFoundException extends NotFoundException{
        public CountryNotFoundException(String name) {
            super("Could not find country '" + name + "'");
        }
}
